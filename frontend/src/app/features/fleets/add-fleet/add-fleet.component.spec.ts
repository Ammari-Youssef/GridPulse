import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddFleetComponent } from './add-fleet.component';

describe('AddFleetComponent', () => {
  let component: AddFleetComponent;
  let fixture: ComponentFixture<AddFleetComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddFleetComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddFleetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
