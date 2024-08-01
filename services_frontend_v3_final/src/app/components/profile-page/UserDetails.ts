export interface UserDetails {
    personid: number;
    firstName: string;
    lastName: string;
    designation: string;
    email: string;
    createdAt: string;
    country: {
        countryCode: string;
        countryName: string;
        currencyCode: string;
        updatedAt: string;
        updatedBy: string;
        countryCodeIso: string;
    };
    state: string;
    address: string;
    phone_no: string;
    roles: Role[];
}

export interface Role {
    roleId: number;
    roleName: string;
    roleDescription: string;
}
