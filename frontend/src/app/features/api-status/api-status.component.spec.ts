import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApiStatusComponent } from './api-status.component';
import { Apollo } from 'apollo-angular';
import { ApolloMock } from '../../core/test/mock/apollo.mock';

describe('ApiStatusComponent', () => {
  let component: ApiStatusComponent;
  let fixture: ComponentFixture<ApiStatusComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ApiStatusComponent],
      providers: [
        {
          provide: Apollo,
          useValue: ApolloMock,
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(ApiStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
