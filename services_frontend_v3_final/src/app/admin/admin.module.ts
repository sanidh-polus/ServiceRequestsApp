import { NgModule } from '@angular/core';
import { CommonModule, NgClass } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { AdminNavComponent } from './admin-nav/admin-nav.component';
import { PreviousRequestsComponent } from './previous-requests/previous-requests.component';
import { TicketCardComponent } from '../components/ticket-card/ticket-card.component';
import { ManageUsersComponent } from './manage-users/manage-users.component';
import { UserListComponent } from '../components/user-list/user-list.component';
import { ProfilePageComponent } from '../components/profile-page/profile-page.component';
import { AdminProfileComponent } from './admin-profile/admin-profile.component';


@NgModule({
    declarations: [
        AdminHomeComponent,
        AdminNavComponent,
        PreviousRequestsComponent,
        ManageUsersComponent,
        AdminProfileComponent
    ],
    imports: [
        CommonModule,
        AdminRoutingModule,
        FormsModule,
        NgClass,
        TicketCardComponent,
        UserListComponent,
        ProfilePageComponent
    ]
})

export class AdminModule { }
