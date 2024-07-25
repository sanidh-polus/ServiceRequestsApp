export class Tickets {
    ticketId: number;
    categoryId: number | undefined;
    ticketDescription: string;
    categoryName: string;
    ticketCreatedTime: string;
    assignedTo: {
        designation: string,
        email: string,
        firstName: string, 
        id: number,
        lastName: string
    } | null;
    ticketUpdatedAt: string;

    constructor() {
        this.ticketId = 0;
        this.categoryId = 0;
        this.ticketDescription = '';
        this.categoryName = '';
        this.ticketCreatedTime = '';
        this.assignedTo = null;
        this.ticketUpdatedAt = '';
    }
}