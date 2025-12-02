import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeviceManagerComponent } from './device-manager.component';

describe('DeviceManagerComponent', () => {
  let component: DeviceManagerComponent;
  let fixture: ComponentFixture<DeviceManagerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeviceManagerComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeviceManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
