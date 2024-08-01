import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AdminHomeComponent } from './admin-home.component';

describe('AdminHomeComponent', () => {
    let component: AdminHomeComponent;
    let fixture: ComponentFixture<AdminHomeComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [FormsModule],
            declarations: [AdminHomeComponent],
            providers: [provideHttpClient()],
        })
        .compileComponents();

        fixture = TestBed.createComponent(AdminHomeComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
