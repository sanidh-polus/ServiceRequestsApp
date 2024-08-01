import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { AdminTicket } from './AdminTicket';

@Injectable({
    providedIn: 'root'
})

export class AdminHomeService {

    constructor(private _http: HttpClient) {}

    getAdminsAssignedTickets(pageDetails: any): Observable<AdminTicket[]> {
        return this._http.post<AdminTicket[]>(`/service/admintickets`, pageDetails);
    }

    approveOrRejectTicket(ticketDetails: any): Observable<any> {
        return this._http.post<any>(`/service/updatestatus`, ticketDetails);
    }

    getAdminsOrUsers(roleId: number): Observable<any> {
        return this._http.get<any>(`/service/roles/${roleId}`);
    }

    makeAdmin(addAdminBody: any): Observable<any> {
        return this._http.post<any>(`/service/makeadmin`, addAdminBody);
    }

    removeAdmin(adminId: number, userId: number): Observable<any> {
        return this._http.delete<any>(`/service/removeadmin/${adminId}/${userId}`);
    }
}
