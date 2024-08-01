export interface Tickets {
    ticketId: number;
    categoryId: number;
    ticketDescription: string;
    categoryName: string;
    ticketCreatedTime: string;
    assignedTo: {
        designation: string,
        email: string,
        firstName: string, 
        id: number,
        lastName: string
    } | null,
    ticketUpdatedAt: string;
}
