import { Component, HostListener, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';
import { LoginSignUpService } from '../../service/login_signup/login_signup.service';
import { TicketCountService } from '../../service/ticket-count/ticket-count.service';

@Component({
    selector: 'app-user-nav',
    templateUrl: './user-nav.component.html',
    styleUrl: './user-nav.component.css'
})

export class UserNavComponent implements OnInit, OnDestroy {       

    firstName = '';
    userId = 0;
    lastScrollTop = 0;
    isNavbarHidden = false;
    inProgressTicketsCount = 0;
    assignedTicketsCount = 0;
    approvedTicketsCount = 0;
    rejectedTicketsCount = 0;
    private subscription: Subscription = new Subscription();

    constructor( private _router: Router,
                 private _loginSignUpService: LoginSignUpService,
                 private _ticketCountService: TicketCountService ) {}

    ngOnInit(): void {
        const CURRENT_USER = this._loginSignUpService.getCurrentUser();
        if (CURRENT_USER !== null) {
            this.firstName = CURRENT_USER.firstName;
            this.userId = CURRENT_USER.personid;
        }
        this.fetchTicketCount();
    }

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }

    @HostListener('window:scroll', ['$event'])
    public onWindowScroll(): void {
        const SCROLL_TOP = window.scrollY || document.documentElement.scrollTop;
        if (SCROLL_TOP > this.lastScrollTop) {
            this.isNavbarHidden = true;
        } else {
            this.isNavbarHidden = false;
        }
        this.lastScrollTop = SCROLL_TOP <= 0 ? 0 : SCROLL_TOP;
    }

    public logout(): void {
        localStorage.removeItem('currentUser');
        localStorage.setItem('firstTime', 'false');
        this._router.navigate(['login']);
    };

    private fetchTicketCount(): void {
        this.subscription = this._ticketCountService.getInProgressTicketsCount()
            .subscribe(count => this.inProgressTicketsCount = count);

        this.subscription.add(this._ticketCountService.getAssignedTicketsCount()
            .subscribe(count => this.assignedTicketsCount = count));

        this.subscription.add(this._ticketCountService.getApprovedTicketsCount()
            .subscribe(count => this.approvedTicketsCount = count));

        this.subscription.add(this._ticketCountService.getRejectedTicketsCount()
            .subscribe(count => this.rejectedTicketsCount = count));
    }
}
