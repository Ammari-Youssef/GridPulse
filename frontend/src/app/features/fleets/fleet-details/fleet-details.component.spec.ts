import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FleetDetailsComponent } from './fleet-details.component';

describe('FleetDetailsComponent', () => {
  let component: FleetDetailsComponent;
  let fixture: ComponentFixture<FleetDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FleetDetailsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FleetDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
