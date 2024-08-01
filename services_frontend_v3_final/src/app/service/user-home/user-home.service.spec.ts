import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

import { UserHomeService } from './user-home.service';

describe('UserHomeService', () => {
    let service: UserHomeService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [
                provideHttpClient(), 
                provideHttpClientTesting(),
            ],
        });
        service = TestBed.inject(UserHomeService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
