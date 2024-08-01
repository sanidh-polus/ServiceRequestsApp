import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';

import { AdminProfileComponent } from './admin-profile.component';
import { ProfilePageComponent } from '../../components/profile-page/profile-page.component';
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

describe('AdminProfileComponent', () => {
    let component: AdminProfileComponent;
    let fixture: ComponentFixture<AdminProfileComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [ProfilePageComponent],
            declarations: [AdminProfileComponent],
            providers: [
                provideHttpClient(),
                { provide: LoginSignUpService, useClass: MockLoginSignUpService },
            ],
        })
        .compileComponents();

        fixture = TestBed.createComponent(AdminProfileComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
