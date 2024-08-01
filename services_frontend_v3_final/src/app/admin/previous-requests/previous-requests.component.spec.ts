import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';

import { PreviousRequestsComponent } from './previous-requests.component';

describe('PreviousRequestsComponent', () => {
    let component: PreviousRequestsComponent;
    let fixture: ComponentFixture<PreviousRequestsComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [PreviousRequestsComponent],
            providers: [provideHttpClient()],
        })
        .compileComponents();

        fixture = TestBed.createComponent(PreviousRequestsComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
