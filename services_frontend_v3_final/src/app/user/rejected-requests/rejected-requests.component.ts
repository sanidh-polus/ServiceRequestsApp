import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Tickets } from './Tickets';
import { UserHomeService } from '../../service/user-home/user-home.service';
import { LoginSignUpService } from '../../service/login_signup/login_signup.service';
import { TicketCountService } from '../../service/ticket-count/ticket-count.service';

@Component({
    selector: 'app-rejected-requests',
    templateUrl: './rejected-requests.component.html',
    styleUrl: './rejected-requests.component.css'
})

export class RejectedRequestsComponent implements OnInit {
    
    userId = 0;
    currentPage = 1;
    ticketsPerPage = 4;
    totalTicketCount = 0;
    pages: number[] = [];
    tickets: Tickets[] = [];

    constructor( private _loginSignUpService: LoginSignUpService,
                 private _userHomeService: UserHomeService,
                 private _ticketCountService: TicketCountService,
                 private _route: ActivatedRoute ) {}
    
    ngOnInit(): void {
        const CURRENT_USER = this._loginSignUpService.getCurrentUser();
        this.userId = CURRENT_USER.personid;
        this.pagination();
        this.fetchCurrentPageTickets();
    }

    public getRejectedRequests( pageNumber: number ): void {
        this._userHomeService.getTickets( this.userId, 4, pageNumber, this.ticketsPerPage ).subscribe({
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

    private pagination(): void {
        this._ticketCountService.getRejectedTicketsCount().subscribe(count => this.totalTicketCount = count);
        const TOTAL_PAGES = Math.ceil(this.totalTicketCount / this.ticketsPerPage);
        this.pages = Array.from({ length: TOTAL_PAGES }, (_, index) => index);
    }

    private fetchCurrentPageTickets(): void {
        this._route.params.subscribe(params => {
            this.currentPage = +params['page'] || 1;
            this.getRejectedRequests(this.currentPage);
        });
    }
}
