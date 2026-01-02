import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MapComponent } from './map.component';
import { CommonModule } from '@angular/common';
import { Apollo } from 'apollo-angular';
import { ApolloMock } from '@testing/mock/apollo.mock';

describe('MapComponent', () => {
  let component: MapComponent;
  let fixture: ComponentFixture<MapComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CommonModule],
      providers: [{ provide: Apollo, useValue: ApolloMock }],
      declarations: [MapComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(MapComponent);
    component = fixture.componentInstance;

    // Prevent actual map initialization in tests
    spyOn(component as unknown as Record<string, () => void>, 'initMap');
    spyOn(component as unknown as Record<string, () => void>, 'loadDevices');
    spyOn(component as unknown as Record<string, () => void>, 'startPolling');

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have loading and error properties', () => {
    expect(component.loading).toBe(false);
    expect(component.error).toBeNull();
  });

  it('should clean up interval on destroy', () => {
    component['pollingInterval'] = setInterval(() => 0, 1000);
    spyOn(globalThis, 'clearInterval');

    component.ngOnDestroy();

    expect(clearInterval).toHaveBeenCalled();
  });
});
