export class NewRequest {
    personId: number;
    categoryId: number | undefined;
    ticketDescription: string;

    constructor() {
        this.personId = 0;
        this.categoryId = 0;
        this.ticketDescription = '';
    }
}
