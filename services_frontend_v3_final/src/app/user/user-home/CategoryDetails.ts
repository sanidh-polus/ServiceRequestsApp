export class CategoryDetails {
    categoryId: number;
    categoryName: string;
    categoryDescription: string;
    categoryCreatedBy: string;
    categoryCreatedAt: string;
    tickets: any[];

    constructor() {
        this.categoryId = 0;
        this.categoryName = '';
        this.categoryDescription = '';
        this.categoryCreatedBy = '';
        this.categoryCreatedAt = '';
        this.tickets = [];
    }
}
