import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { provideRouter } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { UserHomeComponent } from './user-home.component';
import { LoginSignUpService } from '../../service/login-signup/login-signup.service';

class MockLoginSignUpService {
    getCurrentUser() {
        return { personid: 9 }; 
    }
}

describe('UserHomeComponent', () => {
    let component: UserHomeComponent;
    let fixture: ComponentFixture<UserHomeComponent>;
    let loginSignUpService: LoginSignUpService;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [UserHomeComponent],
            imports: [FormsModule],
            providers: [
                provideHttpClient(), 
                provideHttpClientTesting(), 
                provideRouter([]),
                { provide: LoginSignUpService, useClass: MockLoginSignUpService },
            ],
        })
        .compileComponents();

        fixture = TestBed.createComponent(UserHomeComponent);
        loginSignUpService = TestBed.inject(LoginSignUpService);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
