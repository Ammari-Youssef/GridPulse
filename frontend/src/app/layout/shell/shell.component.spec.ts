import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShellComponent } from './shell.component';
import { ActivatedRoute } from '@angular/router';
import { ActivatedRouteMock } from '@testing/mock/activated-route.mock';
import { LayoutModule } from '@layout/layout.module';
import { AuthService } from '@core/services/auth.service';
import { Apollo } from 'apollo-angular';

describe('ShellComponent', () => {
  let component: ShellComponent;
  let fixture: ComponentFixture<ShellComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LayoutModule],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: ActivatedRouteMock,
        },
        {
          provide: AuthService,
        },
        {
          provide: Apollo,
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(ShellComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
