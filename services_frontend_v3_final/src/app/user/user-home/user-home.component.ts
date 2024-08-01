/* eslint-disable @typescript-eslint/no-unused-expressions */
import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import swal from 'sweetalert';
import { UserHomeService } from '../../service/user-home/user-home.service';
import { NewRequest } from './NewRequest';
import { CategoryDetails } from './CategoryDetails';
import { LoginSignUpService } from '../../service/login-signup/login-signup.service';
import { TicketCountService } from '../../service/ticket-count/ticket-count.service';

declare let bootstrap: any;

@Component({
    selector: 'app-user-home',
    templateUrl: './user-home.component.html',
    styleUrl: './user-home.component.css'
})

export class UserHomeComponent implements OnInit {

    @ViewChild('carousel') carouselElement!: ElementRef; 
    newRequest: NewRequest = new NewRequest();
    firstName = '';
    errorMessage = '';
    categoryNames: string[] = [];
    categories = new Map<string, number>();
    categoryDetails: CategoryDetails[] = [];
    requestFormData = {
        category: '',
        description: ''
    };
    errorsMap = new Map<string, string>();
    showAlert = true; 
    totalTicketCount = 0;
    ticketsPerPage = 4;

    constructor( private _loginSignUpService: LoginSignUpService,
                 private _userHomeService: UserHomeService,
                 private _router: Router,
                 private _ticketCountService: TicketCountService ) {}

    ngOnInit(): void {
        const CURRENT_USER = this._loginSignUpService.getCurrentUser();
        if (CURRENT_USER) {
            this.firstName = CURRENT_USER.firstName;
            this.newRequest.personId = CURRENT_USER.personid;
        }
        this.getAllCategories();

        if (localStorage.getItem('firstTime') === 'false') {
            localStorage.setItem('firstTime', 'true');
        }
        else {
            this.showAlert = false;
        }
    }

    public setCarousel(): void {
        const MY_CAROUSEL = this.carouselElement.nativeElement.querySelector('div');
        const CAROUSEL = new bootstrap.Carousel(MY_CAROUSEL);
        const SLIDE_POSITION = this.categoryDetails.findIndex(category => 
                            category.categoryName === this.requestFormData.category);
        CAROUSEL.to(SLIDE_POSITION);
    }

    private resetForm(): void {
        this.requestFormData.category = '';
        this.requestFormData.description = '';
    }

    private getAllCategories(): void {
        this._userHomeService.getCategories().subscribe({
            next: (response) => {
                this.categoryDetails = response;
                response.forEach((category: any) => {
                    this.categories.set(category.categoryName, category.categoryId);
                    this.categoryNames.push(category.categoryName);
                });
                this.categoryNames.sort((a, b) => b.length - a.length);
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

    private checkNewRequestErrors(): boolean {
        this.requestFormData.category === '' ? this.errorsMap.set('category', '') : null;
        this.requestFormData.description === '' ? this.errorsMap.set('description', '') : null;

        if (this.requestFormData.category === '' && this.requestFormData.description === '') {
            this.errorMessage = 'Please enter all the details';
            return false;
        }
        else if (this.requestFormData.category === '') {
            this.errorMessage = 'Please select a category';
            return false;
        }
        else if (this.requestFormData.description === '') {
            this.errorMessage = 'Please enter a description';
            return false;
        }
        else if (this.requestFormData.description.length > 255) {
            this.errorMessage = 'Please shorten the description, size is too large';
            return false;
        }
        else {
            return true;
        }
    }

    private createRequestService(): void {
        const NEW_REQ_BODY = {
            "personId": this.newRequest.personId,
            "categoryId": this.newRequest.categoryId,
            "ticketDescription": this.newRequest.ticketDescription
        }

        this._userHomeService.createRequest(NEW_REQ_BODY).subscribe({
            next: (response) => {
                console.log('Response: ', response);
                swal({
                    title: 'Successfully Created',
                    text: ' ',
                    icon: 'success',
                    buttons: [false],
                    timer: 2000
                });
                this.resetForm();
                this._ticketCountService.fetchTicketCounts();
                this._ticketCountService.getInProgressTicketsCount()
                    .subscribe(count => this.totalTicketCount = count + 1);
                const TOTAL_PAGES = Math.ceil(this.totalTicketCount / this.ticketsPerPage);
                this._router.navigate(['/user/nav/in-progress'], {
                    queryParams: { page: TOTAL_PAGES }
                });
            },
            error: (e: HttpErrorResponse) => {
                console.log(e);
                console.log('Error: ', e.status, e.statusText);
            }
        });
    }

    public createRequest(): void {
        this.errorMessage = '';
        this.errorsMap = new Map<string, string>();
        
        if (!this.checkNewRequestErrors()) {
            return;
        }
        this.newRequest.ticketDescription = this.requestFormData.description;
        this.newRequest.categoryId = this.categories.get(this.requestFormData.category);
        this.createRequestService();
    }
}
