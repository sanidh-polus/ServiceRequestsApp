import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketCardComponent } from './ticket-card.component';

describe('TicketCardComponent', () => {
    let component: TicketCardComponent;
    let fixture: ComponentFixture<TicketCardComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [TicketCardComponent],
            providers: [],
        })
        .compileComponents();

        fixture = TestBed.createComponent(TicketCardComponent);
        component = fixture.componentInstance;
        component.ticket = { 
            ticketId: 1,
            categoryId: 1,
            fullName: '',
            ticketDescription: '',
            categoryName: '',
            ticketCreatedTime: '',
            assignedTo: null,
            ticketUpdatedAt: '',
        };
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
