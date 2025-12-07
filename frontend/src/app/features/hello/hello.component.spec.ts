import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HelloComponent } from './hello.component';
import { Apollo } from 'apollo-angular';
import { ApolloMock } from '../../core/test/mock/apollo.mock';

describe('HelloComponent', () => {
  let component: HelloComponent;
  let fixture: ComponentFixture<HelloComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HelloComponent],
      providers: [
        {
          provide: Apollo,
          useValue: ApolloMock,
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(HelloComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
