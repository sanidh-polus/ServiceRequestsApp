export interface AdminTickets {
    ticketId: number;
    categoryId: number;
    fullName: string;
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
