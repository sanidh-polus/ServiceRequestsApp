import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

import { LoginSignUpService } from './login-signup.service';

describe('LoginSignupService', () => {
    let service: LoginSignUpService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [provideHttpClient(), provideHttpClientTesting()],
        });
        service = TestBed.inject(LoginSignUpService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
