import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';

import { LoginData } from './LoginData';
import { SignUpData } from './SignupData';
import { Country } from './Country';

@Injectable({
    providedIn: 'root',
})

export class LoginSignUpService {
    // apiUrl = 'http://10.199.100.140:8080/service/login';
    // proxyUrl = '/api'+'/service/login';
    // private countriesUrl = 'https://restcountries.com/v3.1/all';

    private currentUser: BehaviorSubject<any>;

    constructor(private _http: HttpClient) {
        this.currentUser = new BehaviorSubject<any>(JSON.parse(localStorage.getItem('currentUser')!));
    }

    public checkLoginDetails(loginDetails: LoginData): Observable<any> {
        return this._http.post<any>('/service/login', loginDetails)
            .pipe(
                tap(user => {
                    localStorage.setItem('currentUser', JSON.stringify(user));
                    this.currentUser.next(user);
                })
            );
    }

    public getCurrentUser(): any {
        return this.currentUser.value;
    }

    public enterSignUpDetails(signUpDetails: SignUpData): Observable<SignUpData[]> {
        return this._http.post<SignUpData[]>('/service/signup', signUpDetails);
    }

    public getCountries(): Observable<Country[]> {
        return this._http.get<Country[]>('/service/countries');
    }
}
