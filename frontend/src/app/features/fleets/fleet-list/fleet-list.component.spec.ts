import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FleetListComponent } from './fleet-list.component';
import { Apollo } from 'apollo-angular';
import { of } from 'rxjs';

describe('FleetListComponent', () => {
  let component: FleetListComponent;
  let fixture: ComponentFixture<FleetListComponent>;

  const apolloMock = {
    query: jasmine.createSpy('query').and.returnValue(
      of({
        data: {
          getAllFleetPaged: {
            content: [],
            totalElements: 0,
            totalPages: 0,
            pageNumber: 0,
            pageSize: 10,
          },
        },
      })
    ),
    mutate: jasmine.createSpy('mutate').and.returnValue(of({})),
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FleetListComponent],
      providers: [{ provide: Apollo, useValue: apolloMock }],
    }).compileComponents();

    fixture = TestBed.createComponent(FleetListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges(); 
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
