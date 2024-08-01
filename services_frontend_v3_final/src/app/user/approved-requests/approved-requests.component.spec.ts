import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideRouter } from '@angular/router';

import { ApprovedRequestsComponent } from './approved-requests.component';

describe('ApprovedRequestsComponent', () => {
    let component: ApprovedRequestsComponent;
    let fixture: ComponentFixture<ApprovedRequestsComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [ApprovedRequestsComponent],
            providers: [provideHttpClient(), provideRouter([])],
        })
        .compileComponents();

        fixture = TestBed.createComponent(ApprovedRequestsComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
