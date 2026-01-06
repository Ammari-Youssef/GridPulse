import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarComponent } from './navbar.component';
import { CommonModule } from '@angular/common';
import { Apollo } from 'apollo-angular';
import { ApolloMock } from '@testing/mock/apollo.mock';
import { MaterialModule } from '@shared/ui/material/material.module';
import { of } from 'rxjs';

describe('NavbarComponent', () => {
  let component: NavbarComponent;
  let fixture: ComponentFixture<NavbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CommonModule, MaterialModule],
      declarations: [NavbarComponent],
      providers: [{ provide: Apollo, useValue: ApolloMock }],
    }).compileComponents();

    // ✅ Override ApolloMock.query for this spec
    ApolloMock.query = jasmine
      .createSpy('query')
      .and.returnValue(
        of({
          data: {
            getAllMessagePaged: {
              content: [
                {
                  id: '1',
                  severity: 'HIGH',
                  messageType: 'SYSTEM',
                  device: { name: 'Device A', fleet: { name: 'Fleet X' } },
                  explanation: 'Test alert',
                  receivedAt: new Date().toISOString(),
                  status: 'NEW',
                },
              ],
              totalElements: 1,
              totalPages: 1,
              pageNumber: 0,
              pageSize: 20,
            },
          },
        })
      );

    fixture = TestBed.createComponent(NavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
