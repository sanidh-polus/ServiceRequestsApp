import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideRouter, RouterLink, RouterOutlet } from '@angular/router';

import { AdminNavComponent } from './admin-nav.component';

describe('AdminNavComponent', () => {
    let component: AdminNavComponent;
    let fixture: ComponentFixture<AdminNavComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [RouterOutlet, RouterLink],
            declarations: [AdminNavComponent],
            providers: [provideHttpClient(), provideRouter([])],
        })
        .compileComponents();

        fixture = TestBed.createComponent(AdminNavComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
