import { TestBed } from '@angular/core/testing';
import { RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { Apollo } from 'apollo-angular';
import { of } from 'rxjs';
import { AuthService } from '@core/services/auth.service';

describe('AppComponent', () => {
  beforeEach(async () => {
    const mockAuthService = {
      user$: of(null),
      loadCurrentUser: jasmine
        .createSpy('loadCurrentUser')
        .and.returnValue(of(null)),
      isAuthenticated: jasmine
        .createSpy('isAuthenticated')
        .and.returnValue(false),
      getUserRole$: jasmine.createSpy('getUserRole$').and.returnValue(of(null)),
    };

    await TestBed.configureTestingModule({
      imports: [RouterModule.forRoot([])],
      declarations: [AppComponent],
      providers: [
        { provide: Apollo, useValue: {} },
        { provide: AuthService, useValue: mockAuthService },
      ],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'gridpulse'`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('gridpulse');
  });
});
