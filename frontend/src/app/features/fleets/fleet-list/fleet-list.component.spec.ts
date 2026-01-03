import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FleetListComponent } from './fleet-list.component';

describe('FleetListComponent', () => {
  let component: FleetListComponent;
  let fixture: ComponentFixture<FleetListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FleetListComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(FleetListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
