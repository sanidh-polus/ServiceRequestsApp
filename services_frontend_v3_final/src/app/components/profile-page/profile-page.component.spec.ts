import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';

import { ProfilePageComponent } from './profile-page.component';
import { LoginSignUpService } from '../../service/login-signup/login-signup.service';

class MockLoginSignUpService {
    getCurrentUser() {
        return {
            "personid": 9,
            "firstName": "Abin",
            "lastName": "Raj",
            "designation": "Intern",
            "email": "abin@polus.com",
            "createdAt": "2024-07-16T11:49:23.000+05:30",
            "country": {
                "countryCode": "ISL",
                "countryName": "Iceland",
                "currencyCode": "ISK",
                "updatedAt": "2024-07-11T14:56:38.000+05:30",
                "updatedBy": "APPLICATION_ADMINISTRATOR",
                "countryCodeIso": "IS"
            },
            "state": "Kerala",
            "address": "1234 Trivandrum",
            "phone_no": "9090909090",
            "roles": [
                {
                    "roleId": 2,
                    "roleName": "PRINCIPAL INVESTIGATOR",
                    "roleDescription": "The principal investigator can create a service ticket and assign it to an administrator"
                }
            ]
        }; 
    }
}

describe('ProfilePageComponent', () => {
    let component: ProfilePageComponent;
    let fixture: ComponentFixture<ProfilePageComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [ProfilePageComponent],
            providers: [
                provideHttpClient(),
                { provide: LoginSignUpService, useClass: MockLoginSignUpService },
            ],
        })
        .compileComponents();

        fixture = TestBed.createComponent(ProfilePageComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
