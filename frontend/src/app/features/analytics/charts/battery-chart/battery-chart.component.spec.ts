import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BatteryChartComponent } from './battery-chart.component';

describe('BatteryChartComponent', () => {
  let component: BatteryChartComponent;
  let fixture: ComponentFixture<BatteryChartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BatteryChartComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BatteryChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
