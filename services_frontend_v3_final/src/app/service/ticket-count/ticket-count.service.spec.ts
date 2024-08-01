import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

import { TicketCountService } from './ticket-count.service'
import { LoginSignUpService } from '../login-signup/login-signup.service';

class MockLoginSignUpService {
    getCurrentUser() {
        return { personid: 9 }; 
    }
}

describe('TicketCountService', () => {
    let service: TicketCountService;
    let loginSignUpService: LoginSignUpService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [
                provideHttpClient(), 
                provideHttpClientTesting(),
                { provide: LoginSignUpService, useClass: MockLoginSignUpService },
            ],
        });
        service = TestBed.inject(TicketCountService);
        loginSignUpService = TestBed.inject(LoginSignUpService);
    });
  
    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
  