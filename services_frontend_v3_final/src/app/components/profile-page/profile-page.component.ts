import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgFor, NgIf } from '@angular/common';
import { LoginSignUpService } from '../../service/login-signup/login-signup.service';
import { UserDetails } from './UserDetails';
import { Role } from './UserDetails';

@Component({
    selector: 'app-profile-page',
    standalone: true,
    imports: [NgFor, NgIf, CommonModule],
    templateUrl: './profile-page.component.html',
    styleUrl: './profile-page.component.css'
})

export class ProfilePageComponent implements OnInit {

    currentUser: UserDetails | null = null;
 
    constructor( private _loginSignUpService: LoginSignUpService ) {}

    ngOnInit(): void {
        const CURRENT_USER = this._loginSignUpService.getCurrentUser();
        if (CURRENT_USER) {
            this.currentUser = CURRENT_USER;
        }
        const UNIQUE_ROLES = Array.from(new Map(CURRENT_USER.roles.map((role: Role) => [role.roleId, role])).values());
        CURRENT_USER.roles = UNIQUE_ROLES;
    }
}
