import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserHomeComponent } from './user-home/user-home.component';
import { UserNavComponent } from './user-nav/user-nav.component';
import { InProgressRequestsComponent } from './in-progress-requests/in-progress-requests.component';
import { AssignedRequestsComponent } from './assigned-requests/assigned-requests.component';
import { ApprovedRequestsComponent } from './approved-requests/approved-requests.component';
import { RejectedRequestsComponent } from './rejected-requests/rejected-requests.component';
import { UserProfileComponent } from './user-profile/user-profile.component';

const routes: Routes = [
    { path: '', redirectTo: 'nav', pathMatch: 'full' },
    { path: 'nav', component: UserNavComponent, children: [
        { path: '', redirectTo: 'home', pathMatch: 'full' },
        { path: 'home', component: UserHomeComponent },
        { path: 'in-progress', component: InProgressRequestsComponent },
        { path: 'assigned', component: AssignedRequestsComponent },
        { path: 'approved', component: ApprovedRequestsComponent},
        { path: 'rejected', component: RejectedRequestsComponent },
        { path: 'profile', component: UserProfileComponent }
    ]}
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})

export class UserRoutingModule { }
