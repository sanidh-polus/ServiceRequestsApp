import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ServiceCategory } from './ServiceCategory';
import { NewRequest } from './NewRequest';
import { Ticket } from './Ticket';
import { Admin } from './Admin';

@Injectable({
    providedIn: 'root'
})

export class UserHomeService {

    constructor(private _http: HttpClient) {}

    getCategories(): Observable<ServiceCategory[]> {
        return this._http.get<ServiceCategory[]>('/service/categories');
    }

    createRequest(requestDetails: NewRequest): Observable<any> {
        return this._http.post<NewRequest>('/service/create', requestDetails);
    }

    getTickets(ticketsData: any): Observable<Ticket[]> {
        return this._http.post<Ticket[]>(`/service/tickets`, ticketsData);
    }

    deleteTicket(id: number): Observable<any> {
        return this._http.delete<any>(`/service/delete/${id}`);
    }

    getAdmins(): Observable<Admin[]> {
        return this._http.get<Admin[]>('/service/roles/1');
    }

    assignAdmin(adminDetails: any): Observable<any> {
        return this._http.put<any>('/service/assign', adminDetails);
    }

    getTicketCount(id: number): Observable<any> {
        return this._http.get<any>(`/service/count/${id}`);
    }
}
