import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminTickets } from './AdminTickets';

@Component({
    selector: 'app-ticket-card',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './ticket-card.component.html',
    styleUrl: './ticket-card.component.css'
})

export class TicketCardComponent {
    
    @Input() ticket: AdminTickets = new AdminTickets();
    @Input() status: string = '';
}
