import { Component, HostListener, OnInit } from '@angular/core';
import { LoginSignUpService } from '../../service/login-signup/login-signup.service';
import { TicketCountService } from '../../service/ticket-count/ticket-count.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-admin-nav',
    templateUrl: './admin-nav.component.html',
    styleUrl: './admin-nav.component.css'
})

export class AdminNavComponent implements OnInit {

    userId = 0;
    firstName = '';
    lastScrollTop = 0;
    isNavbarHidden = false;
    
    constructor(private _loginSignUpService: LoginSignUpService,
                private _ticketCountService: TicketCountService,
                private _router: Router) {}

    ngOnInit(): void {
        const CURRENT_USER = this._loginSignUpService.getCurrentUser();
        if (CURRENT_USER) {
            this.userId = CURRENT_USER.personid;
            this.firstName = CURRENT_USER.firstName;
        }
    }

    public async switchToUser(): Promise<void> {
        console.log('Switching to user');
        await this._ticketCountService.fetchTicketCounts();
        this._router.navigate(['user']);
    }
    
    public logout(): void {
        localStorage.removeItem('currentUser');
        localStorage.setItem('firstTime', 'false');
        this._router.navigate(['login']);
    };

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
}
