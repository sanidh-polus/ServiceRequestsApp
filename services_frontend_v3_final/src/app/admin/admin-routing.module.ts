import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { AdminNavComponent } from './admin-nav/admin-nav.component';
import { PreviousRequestsComponent } from './previous-requests/previous-requests.component';
import { ManageUsersComponent } from './manage-users/manage-users.component';
import { AdminProfileComponent } from './admin-profile/admin-profile.component';

const routes: Routes = [
    { path: '', redirectTo: 'nav', pathMatch: 'full' },
    { path: 'nav', component: AdminNavComponent, children: [
        { path: '', redirectTo: 'home', pathMatch: 'full' },
        { path: 'home', component: AdminHomeComponent },
        { path: 'previous-reqs', component: PreviousRequestsComponent },
        { path: 'manage-users', component: ManageUsersComponent },
        { path: 'profile', component: AdminProfileComponent }
    ]}
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})

export class AdminRoutingModule { }
