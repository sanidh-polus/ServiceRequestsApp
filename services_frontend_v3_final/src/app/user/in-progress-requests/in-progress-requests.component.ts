import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import swal from 'sweetalert';
import Swal from 'sweetalert2';
import { LoginSignUpService } from '../../service/login-signup/login-signup.service';
import { UserHomeService } from '../../service/user-home/user-home.service';
import { Tickets } from './Tickets';
import { TicketCountService } from '../../service/ticket-count/ticket-count.service';

@Component({
    selector: 'app-in-progress-requests',
    templateUrl: './in-progress-requests.component.html',
    styleUrl: './in-progress-requests.component.css'
})

export class InProgressRequestsComponent implements OnInit {

    firstName = '';
    userId = 0;
    currentPage = 1;
    ticketsPerPage = 4;
    totalTicketCount = 0;
    pages: number[] = [];
    tickets: Tickets[] = [];
    categoryNames: string[] = [];
    categoryMap = new Map<string, number>();
    editTicketDetails: Tickets = new Tickets();
    editError = '';
    selectedAdmin = '';
    adminNames: string[] = [];
    adminMap = new Map<string, number>();
    assignedTicketId = 0;
    isAssigned = false;
    private subscription: Subscription = new Subscription();
    isAdmin = false;
    userFullName = ''

    constructor( private _loginSignUpService: LoginSignUpService,
                 private _userHomeService: UserHomeService,
                 private _ticketCountService: TicketCountService,
                 private _route: ActivatedRoute ) {}

    ngOnInit(): void {
        const CURRENT_USER = this._loginSignUpService.getCurrentUser();
        if (CURRENT_USER) {
            this.userId = CURRENT_USER.personid;
        }
        if (this.hasRole(CURRENT_USER.roles, 'APPLICATION_ADMINISTRATOR')) {
            this.isAdmin = true;
            this.userFullName = CURRENT_USER.firstName + ' ' + CURRENT_USER.lastName;
        }
        this.pagination();
        this.getAllCategories();
        this.getAllAdmins();
        this.fetchCurrentPageTickets();
    }

    private hasRole(roles: { roleId: number; roleName: string; roleDescription: string }[], roleName: string): boolean {
        return roles.some(role => role.roleName === roleName);
    }

    private pagination(): void {
        this.subscription = this._ticketCountService.getInProgressTicketsCount()
            .subscribe(count => this.totalTicketCount = count);
        const TOTAL_PAGES = Math.ceil(this.totalTicketCount / this.ticketsPerPage);
        this.pages = Array.from({ length: TOTAL_PAGES }, (_, index) => index);
    }
      
    // private async getPendingTicketsCount(): Promise<number> {
    //     try {
    //         return await this._ticketCountService.getTicketCount(this.userId, 1);
    //     } catch (error) {
    //         console.error('Error fetching pending tickets count:', error);
    //         throw error; 
    //     }
    // }

    private fetchCurrentPageTickets(): void {
        this._route.queryParams.subscribe(params => {
            this.currentPage = +params['page'] || 1;
            this.getPendingRequests(this.currentPage);
        });
    }

    public getPendingRequests( pageNumber: number ): void {
        const TICKETS_PAYLOAD = {
            "personId": this.userId,
            "statusId": 1,
            "page": pageNumber - 1,
            "size": this.ticketsPerPage
        };

        this._userHomeService.getTickets(TICKETS_PAYLOAD).subscribe({
            next: (response) => {
                console.log(response);
                this.tickets = response;
            },
            error: (e: HttpErrorResponse) => {
                console.log(e);
                // if (e.status == 500) {
                //     swal({
                //         title: "Server down",
                //         text: "Please try again later",
                //         icon: "error"
                //     });
                // }
            },
        });
    }

    private getAllCategories(): void {
        this._userHomeService.getCategories().subscribe({
            next: (response) => {
                response.forEach((category: any) => {
                    this.categoryMap.set(category.categoryName, category.categoryId);
                    this.categoryNames.push(category.categoryName);
                });
                this.categoryNames.sort((a, b) => b.length - a.length);
            },
            error: (e: HttpErrorResponse) => {
                console.log(e);
            },
        });
    }

    private getAllAdmins(): void {
        this._userHomeService.getAdmins().subscribe({
            next: (response) => {
                console.log(response);
                response.forEach((admin: any) => {
                    let ADMIN_FULL_NAME = admin.firstName + ' ' + admin.lastName;
                    ADMIN_FULL_NAME = (ADMIN_FULL_NAME === this.userFullName) ? 'to me' : ADMIN_FULL_NAME;
                    this.adminMap.set(ADMIN_FULL_NAME, admin.id);
                    this.adminNames.push(ADMIN_FULL_NAME);
                });
                this.adminNames.sort((a, b) => a.length - b.length);
            },
            error: (e: HttpErrorResponse) => {
                console.log(e);
            },
        });
    }

    public openEditTicket( ticketDetails: Tickets ): void {
        this.editTicketDetails = { ...ticketDetails };
    }

    private editTicketService(): void {
        const EDIT_REQ_BODY = {
            "personId": this.userId,
            "ticketId": this.editTicketDetails.ticketId,
            "categoryId": this.editTicketDetails.categoryId,
            "ticketDescription": this.editTicketDetails.ticketDescription
        }
        this._userHomeService.createRequest(EDIT_REQ_BODY).subscribe({
            next: (response) => {
                console.log('Response: ', response);
                const EDITED_TICKET = this.tickets.find(ticket => ticket.ticketId === this.editTicketDetails.ticketId);
                if (EDITED_TICKET) {
                    EDITED_TICKET.categoryName = this.editTicketDetails.categoryName;
                    EDITED_TICKET.ticketDescription = this.editTicketDetails.ticketDescription;
                    EDITED_TICKET.ticketUpdatedAt = response.message;
                }
                swal({
                    title: 'Successfully Edited',
                    text: ' ',
                    icon: 'success',
                    buttons: [false],
                    timer: 2000
                });
            },
            error: (e: HttpErrorResponse) => {
                console.log(e);
            }
        });
    }

    public editTicket(): void {
        this.editError = '';
        if (this.editTicketDetails.ticketDescription === '') {
            this.editError = 'Please enter a description';
        }
        if (this.editTicketDetails.ticketDescription.length > 255) {
            this.editError = 'Please shorten the description, size is too large';
        }
        if (this.editError !== '') {
            return;
        }
        this.editTicketDetails.categoryId = this.categoryMap.get(this.editTicketDetails.categoryName);
        this.editTicketService();
    }

    private deleteTicketService( ticketId: number ): void {
        this._userHomeService.deleteTicket(ticketId).subscribe({
            next: (response) => {
                console.log('Response: ', response);
                this.tickets = this.tickets.filter(ticket => ticket.ticketId !== ticketId);
                swal({
                    title: 'Successfully Deleted',
                    text: ' ',
                    icon: 'success',
                    buttons: [false],
                    timer: 2000
                });
                this._ticketCountService.fetchTicketCounts();
                this.pagination();
            },
            error: (e: HttpErrorResponse) => {
                console.log(e);
            }
        });
    }

    public deleteTicket(ticketId: number): void {
        Swal.fire({
            title: 'Delete Confirmation',
            html: `
                <div id="delete-dialog">
                    <p id="delete-message" tabindex="0">Are you sure you want to delete this item?</p>
                </div>
                `,
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Delete',
            cancelButtonText: 'Cancel',
            cancelButtonColor: '#aaa',
            confirmButtonColor: '#dc3545',
            reverseButtons: true,
            focusConfirm: false
        }).then((result) => {
            if (result.isConfirmed) {
                this.deleteTicketService(ticketId);
            } else {
                console.log('Deletion canceled by user.');
            }
        });
    }

    private assignAdminService( adminId: number | undefined ): void {
        const ASSIGN_REQ_BODY = {
            "ticketId": this.assignedTicketId,
            "assignedTo": adminId,
        }
        this._userHomeService.assignAdmin(ASSIGN_REQ_BODY).subscribe({
            next: (response) => {
                console.log('Response: ', response);
                this.tickets = this.tickets.filter(ticket => ticket.ticketId !== this.assignedTicketId);
                swal({
                    title: 'Successfully Assigned',
                    text: ' ',
                    icon: 'success',
                    buttons: [false],
                    timer: 2000
                });
                this._ticketCountService.fetchTicketCounts();
                this.pagination();
            },
            error: (e: HttpErrorResponse) => {
                console.log(e);
            }
        });
    }

    public assignAdmin(): void {
        const ADMIN_ID = this.adminMap.get(this.selectedAdmin);
        this.assignAdminService(ADMIN_ID);
        this.selectedAdmin = '';
    }
}
