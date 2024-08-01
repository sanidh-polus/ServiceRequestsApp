import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';

import { ManageUsersComponent } from './manage-users.component';
import { UserListComponent } from '../../components/user-list/user-list.component';

describe('ManageUsersComponent', () => {
    let component: ManageUsersComponent;
    let fixture: ComponentFixture<ManageUsersComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [UserListComponent],
            declarations: [ManageUsersComponent],
            providers: [provideHttpClient()],
        })
        .compileComponents();

        fixture = TestBed.createComponent(ManageUsersComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
