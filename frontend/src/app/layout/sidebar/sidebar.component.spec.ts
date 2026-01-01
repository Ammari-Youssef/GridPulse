import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivatedRoute, RouterModule } from '@angular/router';
import { ActivatedRouteMock } from '@testing/mock/activated-route.mock';
import { CommonModule } from '@angular/common';
import { Apollo } from 'apollo-angular';
import { ApolloMock } from '@testing/mock/apollo.mock';
import { of } from 'rxjs';

import { SidebarComponent } from './sidebar.component';
import { MaterialModule } from '@shared/ui/material/material.module';
import { AuthService } from '@core/services/auth.service';
import { Role } from '@core/models/enums/role.enum';

describe('SidebarComponent', () => {
  let component: SidebarComponent;
  let fixture: ComponentFixture<SidebarComponent>;

  beforeEach(async () => {
    const mockAuthService = {
      getUserRole$: jasmine
        .createSpy('getUserRole$')
        .and.returnValue(of<Role | null>(null)),
    };

    await TestBed.configureTestingModule({
      declarations: [SidebarComponent],
      imports: [CommonModule, RouterModule.forRoot([]), MaterialModule],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: ActivatedRouteMock,
        },
        {
          provide: Apollo,
          useValue: ApolloMock,
        },
        {
          provide: AuthService,
          useValue: mockAuthService,
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(SidebarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
