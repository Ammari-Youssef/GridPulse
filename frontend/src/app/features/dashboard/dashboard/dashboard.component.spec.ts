import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DashboardComponent } from './dashboard.component';
import { AuthService } from '@services/auth.service';
import { of } from 'rxjs';
import { DashboardModule } from '../dashboard.module';
import { CommonModule } from '@angular/common';
import { Apollo } from 'apollo-angular';
import { ApolloMock } from '@testing/mock/apollo.mock';

describe('DashboardComponent', () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;

  beforeEach(async () => {
    const mockAuthService = {
      user$: of({
        id: 1,
        email: 'test@example.com',
        firstname: 'John',
        lastname: 'Doe',
        role: 'USER',
      }) as any,
      getUserRole$: jasmine
        .createSpy('getUserRole$')
        .and.returnValue(of('USER')),
    };

    await TestBed.configureTestingModule({
      imports: [CommonModule, DashboardModule],
      providers: [
        { provide: AuthService, useValue: mockAuthService },
        { provide: Apollo, useValue: ApolloMock },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(DashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
