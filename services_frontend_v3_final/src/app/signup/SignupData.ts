import { Country } from "./Country";

export class SignupData {
    firstName: string;
    lastName: string;
    designation: string;
    email: string;
    password: string;
    country: Country;
    state: string;
    address: string;
    phoneNumber: string;

    constructor() {
        this.firstName = '';
        this.lastName = '';
        this.designation = '';
        this.email = '';
        this.phoneNumber = '';
        this.country = new Country();
        this.state = '';
        this.address = '';
        this.password = '';
    }
}
