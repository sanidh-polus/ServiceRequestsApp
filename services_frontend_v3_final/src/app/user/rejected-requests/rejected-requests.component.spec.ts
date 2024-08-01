import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideRouter } from '@angular/router';

import { RejectedRequestsComponent } from './rejected-requests.component';

describe('RejectedRequestsComponent', () => {
    let component: RejectedRequestsComponent;
    let fixture: ComponentFixture<RejectedRequestsComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [RejectedRequestsComponent],
            providers: [provideHttpClient(), provideRouter([])],
        })
        .compileComponents();

        fixture = TestBed.createComponent(RejectedRequestsComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
