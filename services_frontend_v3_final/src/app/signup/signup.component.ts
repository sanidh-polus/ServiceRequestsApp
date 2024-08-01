/* eslint-disable @typescript-eslint/no-unused-expressions */
import { Component, OnInit } from '@angular/core';
import { Router, RouterOutlet, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { NgClass, NgFor, NgIf } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import swal from 'sweetalert';
import { SignupData } from './SignupData';
import { Country } from './Country';
import { LoginSignUpService } from '../service/login-signup/login-signup.service';

@Component({
	selector: 'app-signup',
	standalone: true,
    imports: [RouterOutlet, RouterLink, FormsModule, NgFor, NgIf, NgClass],
    templateUrl: './signup.component.html',
    styleUrl: './signup.component.css',
})

export class SignUpComponent implements OnInit {

    constructor(private _loginSignUpService: LoginSignUpService, 
                private _router: Router) {}

    signUpData: SignupData = new SignupData();
    countries: Country[] = [];
    errorMessage = '';
    confirmPassword = '';
    passwordType = 'password';
    togglePasswordClass = 'bi-eye-slash';
    confirmPasswordType = 'password';
    toggleConfirmPasswordClass = 'bi-eye-slash';
    countryNames: string[] = [];
    searchText = '';
    errorsMap = new Map<string, string>();

    ngOnInit(): void {
        this.getAllCountries();
    }

    private getAllCountries(): void {
        this._loginSignUpService.getCountries().subscribe({
            next: (response) => {
                response.forEach((country: any) => {
                    // console.log(country.name["common"]);         // For public countries REST API
                    this.countryNames.push(country.countryName);
                });
                this.countries = response;
            },
            error: (e: HttpErrorResponse) => {
                console.log(e);
            },
        });
    }

    public getErrorClasses(field: string): Record<string, boolean> {
        return {
                'error-border': this.errorsMap.has(field),
                'mb-0': this.errorsMap.has(field),
                'mb-2': !this.errorsMap.has(field)
            }
    }
    /**
    * Description:
    * Filtering the countries.
    */
    public getFilteredCountryNames(): string[] {
        const FILTER_VALUE = this.searchText.toLowerCase();
        return this.countryNames.filter((country) =>
            country.toLowerCase().startsWith(FILTER_VALUE)).sort();
    }

    private isValidEmailFormat(email: string): boolean {
        const EMAIL_REGEX = /^[^@\s]+@[^@\s]+\.[^@\s]+$/;
        return EMAIL_REGEX.test(email);
    }

    private countDigitsWithSpaces(inputString: string): boolean {
        const DIGITS_ONLY = inputString.replace(/\D/g, '');
        const DIGIT_COUNT = DIGITS_ONLY.length;
        return DIGIT_COUNT === 10;
    }

    private checkSignUpErrors(): boolean {
        this.signUpData.email === '' ? this.errorsMap.set("email", "Please enter email") : null;
        this.signUpData.password === '' ? this.errorsMap.set('password', 'Please enter password') : null;
        this.signUpData.phoneNumber === '' ? this.errorsMap.set('phoneNumber', 'Please enter phone number') : null;
        this.signUpData.firstName === '' ? this.errorsMap.set('firstName', 'Please enter first name') : null;
        this.signUpData.lastName === '' ? this.errorsMap.set('lastName', 'Please enter last name') : null;
        this.signUpData.designation === '' ? this.errorsMap.set('designation', 'Please enter designation') : null;
        this.signUpData.country.countryName === '' ? this.errorsMap.set('country', 'Please enter country') : null;
        this.signUpData.state === '' ? this.errorsMap.set('state', 'Please enter state') : null;
        this.signUpData.address === '' ? this.errorsMap.set('address', 'Please enter address') : null;
        this.confirmPassword === '' ? this.errorsMap.set('confirmPassword', 'Please enter password confirmation') : null;
    
        if (!this.isValidEmailFormat(this.signUpData.email) && this.signUpData.email !== '') {
            this.errorsMap.set('email', 'Please enter a valid email (example@domain.com)');
        }
        if (this.signUpData.password.length < 8  && this.signUpData.password !== '') {
            this.errorsMap.set('password', 'Password should contain at least 8 characters');
        }
        if (!this.countDigitsWithSpaces(this.signUpData.phoneNumber)  && this.signUpData.phoneNumber !== '') {
            this.errorsMap.set('phoneNumber', 'Enter a valid phone number (10 digits)');
        }
        // const PASSWORD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;
        if (this.signUpData.password !== '' && this.confirmPassword !== '' && 
                this.signUpData.password != this.confirmPassword) {
            this.errorMessage = 'Passwords do not match';
        }
        if (this.errorsMap.size === 0 && this.errorMessage === '') {
            return true;
        }
        else {
            return false;
        }
    }

    private signUpService(): void {
        const SIGNUP_BODY = {
            "firstName": this.signUpData.firstName,
            "lastName": this.signUpData.lastName,
            "designation": this.signUpData.designation,
            "emailAddress": this.signUpData.email,
            "password": this.signUpData.password,
            "countryCode": this.signUpData.country.countryCode,
            "state": this.signUpData.state,
            "address": this.signUpData.address,
            "phoneNo": this.signUpData.phoneNumber,
        };

        this._loginSignUpService.enterSignUpDetails(SIGNUP_BODY).subscribe({
            next: (response) => {
                console.log('Response: ', response);
                swal('Successfully Signed Up', ' ', 'success');
                this._router.navigate(['login']);
            },
            error: (e: HttpErrorResponse) => {
                console.log(e);
                console.log('Error: ', e.status, e.statusText);
                this.errorMessage = e.status === 400 ? 'Check email, duplicate entry' :
                                    e.status === 500 ? 'Cannot check data, server error' :
                                                        '';
                e.status === 404 ? this._router.navigate(['error404']) : null;
            },
        });
    }

    public signUp(): void {
        this.errorMessage = '';
        this.errorsMap = new Map<string, string>();
        this.signUpData.country.countryName = this.searchText;

        if (!this.checkSignUpErrors()) {
            return;
        }
        if (this.countries.length !== 0) {
            this.signUpData.country = this.countries.find(i => i.countryName === this.signUpData.country.countryName)!;
        }
        this.signUpService();
    }

    public togglePasswordVisibility(field: string): void {
        if (field === 'password') {
            this.passwordType =
                this.passwordType === 'password' ? 'text' : 'password';
            this.togglePasswordClass =
                this.passwordType === 'password' ? 'bi-eye-slash' : 'bi-eye';
            } 
        else if (field === 'confirmPassword') {
            this.confirmPasswordType =
                this.confirmPasswordType === 'password' ? 'text' : 'password';
            this.toggleConfirmPasswordClass =
                this.confirmPasswordType === 'password' ? 'bi-eye-slash' : 'bi-eye';
        }
    }
}
