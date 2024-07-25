/* eslint-disable no-useless-catch */
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable  } from 'rxjs';
import { UserHomeService } from '../user-home/user-home.service';
import { HttpErrorResponse } from '@angular/common/http';
import { LoginSignUpService } from '../login_signup/login_signup.service';

@Injectable({
    providedIn: 'root'
})

export class TicketCountService {

    private userId = 0;
    private inProgressTicketsCountSubject = new BehaviorSubject<number>(0);
    private assignedTicketsCountSubject = new BehaviorSubject<number>(0);
    private approvedTicketsCountSubject = new BehaviorSubject<number>(0);
    private rejectedTicketsCountSubject = new BehaviorSubject<number>(0);

    constructor( private _userHomeService: UserHomeService,
                 private _loginSignUpService: LoginSignUpService ) {
                    this.userId = this._loginSignUpService.getCurrentUser().personid;
                    this.fetchTicketCounts(); 
                }

    public async fetchTicketCounts(): Promise<void> {
        try {
            const inProgressCount = await this.getTicketCount(this.userId, 1);
            const assignedCount = await this.getTicketCount(this.userId, 2);
            const approvedCount = await this.getTicketCount(this.userId, 3);
            const rejectedCount = await this.getTicketCount(this.userId, 4);

            this.inProgressTicketsCountSubject.next(inProgressCount);
            this.assignedTicketsCountSubject.next(assignedCount);
            this.approvedTicketsCountSubject.next(approvedCount);
            this.rejectedTicketsCountSubject.next(rejectedCount);
        } catch (error) {
            console.error('Error fetching ticket counts:', error);
        }
    }

    public async getTicketCount(userId: number, statusId: number): Promise<number> {
        try {
            return await this.fetchTicketCount(userId, statusId);
        } catch (error) {
            throw error;
        }
    }

    private fetchTicketCount(userId: number, statusId: number): Promise<number> {
        return new Promise((resolve, reject) => {
            this._userHomeService.getTicketCount(userId, statusId).subscribe({
                next: (response: any) => {
                    resolve(response.count);
                },
                error: (e: HttpErrorResponse) => {
                    reject(e);
                },
            });
        });
    }

    getInProgressTicketsCount(): Observable<number> {
        return this.inProgressTicketsCountSubject.asObservable();
    }
    
    getAssignedTicketsCount(): Observable<number> {
        return this.assignedTicketsCountSubject.asObservable();
    }

    getApprovedTicketsCount(): Observable<number> {
        return this.approvedTicketsCountSubject.asObservable();
    }

    getRejectedTicketsCount(): Observable<number> {
        return this.rejectedTicketsCountSubject.asObservable();
    }
}
