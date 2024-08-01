import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import swal from 'sweetalert';
import { AdminHomeService } from '../../service/admin-home/admin-home.service';
import { LoginSignUpService } from '../../service/login-signup/login-signup.service';
import { User } from './User';

@Component({
    selector: 'app-manage-users',
    templateUrl: './manage-users.component.html',
    styleUrl: './manage-users.component.css'
})

export class ManageUsersComponent implements OnInit {

    userId = 0;
    users: User[] = [];
    activeTab: 'users' | 'admins' = 'users';

    constructor(private _adminHomeService: AdminHomeService,
                private _loginSignUpService: LoginSignUpService) {}

    ngOnInit(): void {
        const CURRENT_USER = this._loginSignUpService.getCurrentUser();
        if (CURRENT_USER) {
            this.userId = CURRENT_USER.personid;
        }
        this.getUsers(this.activeTab);
    }

    public setActiveTab(tab: 'users' | 'admins'): void {
        this.activeTab = tab;
        this.getUsers(tab);
    }

    public getUsers( role: 'users' | 'admins' ): void {
        const ROLE = role === 'admins' ? 1 : 2;
        this._adminHomeService.getAdminsOrUsers(ROLE).subscribe({
            next: (response) => {
                console.log(response);
                this.users = response;
                this.users.sort((a, b) => a.id - b.id)
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
    
    public makeAdmin(selectedUser: number): void {
        console.log('Selected User:', selectedUser);
        const ADD_ADMIN_BODY = {
            "adminId": this.userId,
            "personId": selectedUser
        }
        this._adminHomeService.makeAdmin(ADD_ADMIN_BODY).subscribe({
            next: (response) => {
                console.log(response);
                const POSITION = this.users.findIndex(person => person.id === selectedUser);
                this.users.splice(POSITION, 1);
                swal('Successfully added', '', 'success');
            },
            error: (e: HttpErrorResponse) => {
                console.log(e);
            },
        });
    }

    public removeAdmin(selectedUser: number): void {
        console.log('Selected Admin:', selectedUser);
        this._adminHomeService.removeAdmin(this.userId, selectedUser).subscribe({
            next: (response) => {
                console.log(response);
                const POSITION = this.users.findIndex(person => person.id === selectedUser);
                this.users.splice(POSITION, 1);
                swal('Successfully removed', '', 'success');
            },
            error: (e: HttpErrorResponse) => {
                console.log(e);
            },
        });
    }
}
