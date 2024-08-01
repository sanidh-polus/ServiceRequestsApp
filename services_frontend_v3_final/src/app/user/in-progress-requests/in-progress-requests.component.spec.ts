import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideRouter } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { InProgressRequestsComponent } from './in-progress-requests.component';
import { LoginSignUpService } from '../../service/login-signup/login-signup.service';

class MockLoginSignUpService {
    getCurrentUser() {
        return { 
            roles: [
                {
                    "roleId": 1,
                    "roleName": "APPLICATION_ADMINISTRATOR",
                    "roleDescription": `The administrator can approve or reject a service request. 
                                        In addition to that he can add an admin or remove an existing admin`
                },
                {
                    "roleId": 2,
                    "roleName": "PRINCIPAL INVESTIGATOR",
                    "roleDescription": `The principal investigator can create a service ticket and assign it to an 
                                        administrator`
                }
            ] 
        }; 
    }
}

describe('InProgressRequestsComponent', () => {
    let component: InProgressRequestsComponent;
    let fixture: ComponentFixture<InProgressRequestsComponent>;
    let loginSignUpService: LoginSignUpService;
    
    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [FormsModule],
            declarations: [InProgressRequestsComponent],
            providers: [
                provideHttpClient(), 
                provideRouter([]),
                { provide: LoginSignUpService, useClass: MockLoginSignUpService },
            ],
        })
        .compileComponents();

        fixture = TestBed.createComponent(InProgressRequestsComponent);
        loginSignUpService = TestBed.inject(LoginSignUpService);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
