/* eslint-disable @typescript-eslint/no-unused-expressions */
import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterOutlet, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import swal from 'sweetalert';
import { LoginData } from './LoginData';
import { LoginSignUpService } from '../service/login-signup/login-signup.service';

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [CommonModule, RouterOutlet, RouterLink, FormsModule],
    templateUrl: './login.component.html',
    styleUrl: './login.component.css',
})

export class LoginComponent {

    constructor( private _loginSignUpService: LoginSignUpService, 
                private _router: Router) {}

    errorMessage = '';
    passwordType = 'password';
    togglePasswordClass = 'bi-eye-slash';
    errorsMap = new Map<string, string>();
    loginData: LoginData = new LoginData();
    
    private isValidEmailFormat(email: string): boolean {
        const EMAIL_REGEX = /^[^@\s]+@[^@\s]+\.[^@\s]+$/;
        return EMAIL_REGEX.test(email);
    }

    private checkLoginErrors(): boolean {
        if (this.loginData.email === '' || this.loginData.password === '') {
            this.errorMessage = 'Please enter all details';
            this.loginData.email === '' ? this.errorsMap.set('email', 'true') : null;
            this.loginData.password === '' ? this.errorsMap.set('password', 'true') : null;
        }
        else if (!this.isValidEmailFormat(this.loginData.email)) {
            this.errorMessage = 'Enter a valid email (example@domain.com)';
            this.errorsMap.set('email', 'true');
        }
        // else if (this.loginData.password.length < 8) {
        //     this.errorMessage  = 'Enter a valid password (>= 8 characters)';
        //     this.errorsMap.set('password', 'true');
        // }
        return this.errorMessage !== '' ? false : true;
    }

    private hasRole(roles: { roleId: number; roleName: string; roleDescription: string }[], roleName: string): boolean {
        return roles.some(role => role.roleName === roleName);
    }

    private loginService(): void {
        const LOGIN_BODY = {
            "emailAddress": this.loginData.email,
            "password": this.loginData.password
        }

        this._loginSignUpService.checkLoginDetails(LOGIN_BODY).subscribe({
            next: (response) => {
                console.log('Response: ', response);
                if (this.hasRole(response.roles, 'APPLICATION_ADMINISTRATOR')) 
                    this._router.navigate(['/admin']);
                else if (this.hasRole(response.roles, 'PRINCIPAL INVESTIGATOR')) {
                    this._router.navigate(['/user']);
                }
                else { 
                    swal('Error', 'Not a valid user, please contact an admin', 'error'); 
                    return;
                }
                swal({
                    title: 'Successfully Logged In',
                    text: ' ',
                    icon: 'success',
                    buttons: [false],
                    timer: 2000
                });
            },
            error: (e: HttpErrorResponse) => {
                console.log(e);
                console.log('Error: ', e.status, e.error);
                this.errorMessage = e.status === 401 ? 'Incorrect, please check email/password' : 
                                    e.status === 500 ? 'Sorry, server is currently down' :
                                                        '';
                e.status === 404 ? this._router.navigate(['error404']) : null
            },
        });
    }

    public login(): void {
        this.errorMessage = '';
        this.errorsMap = new Map<string, string>();

        if (!this.checkLoginErrors()) {
            return;
        }
        this.loginService();
    }

    public togglePasswordVisibility(): void {
        this.passwordType = this.passwordType === 'password' ? 'text' : 'password';
        this.togglePasswordClass = this.passwordType === 'text' ? 'bi-eye' : 'bi-eye-slash';
    }
}
