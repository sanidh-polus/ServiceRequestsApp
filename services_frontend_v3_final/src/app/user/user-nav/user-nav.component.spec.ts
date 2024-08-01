import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideRouter, RouterLink, RouterOutlet } from '@angular/router';

import { UserNavComponent } from './user-nav.component';

describe('UserNavComponent', () => {
    let component: UserNavComponent;
    let fixture: ComponentFixture<UserNavComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [RouterOutlet, RouterLink],
            declarations: [UserNavComponent],
            providers: [provideHttpClient(), provideRouter([])],
        })
        .compileComponents();

        fixture = TestBed.createComponent(UserNavComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
