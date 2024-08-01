import { Component, OnInit  } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { LoginSignUpService } from '../../service/login-signup/login-signup.service';
import { AdminHomeService } from '../../service/admin-home/admin-home.service';
import { AdminTickets } from './AdminTickets';

@Component({
    selector: 'app-previous-requests',
    templateUrl: './previous-requests.component.html',
    styleUrl: './previous-requests.component.css'
})

export class PreviousRequestsComponent implements OnInit {

    userId = 0;
    pageNo = 0;
    size = 20;
    tickets: AdminTickets[] = [];
    activeTab: 'approved' | 'rejected' = 'approved';
    
    constructor(private _loginSignUpService: LoginSignUpService,
                private _adminHomeService: AdminHomeService) {}


    ngOnInit(): void {
        const CURRENT_USER = this._loginSignUpService.getCurrentUser();
        if (CURRENT_USER) {
            this.userId = CURRENT_USER.personid;
        }
        this.getAdminsApprovedRejectedRequests(this.activeTab);
    }

    public setActiveTab(tab: 'approved' | 'rejected'): void {
        this.activeTab = tab;
        this.getAdminsApprovedRejectedRequests(tab);
    }

    public getAdminsApprovedRejectedRequests( status: 'approved' | 'rejected' ): void {
        const STATUS_ID = status === 'approved' ? 3 : 4;
        const TICKETS_PAYLOAD = {
            "page": this.pageNo,
            "size": this.size,
            "assignedTo": this.userId,
            "statusId": STATUS_ID
        };
        this._adminHomeService.getAdminsAssignedTickets(TICKETS_PAYLOAD).subscribe({
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
}
