import { Component, OnInit, OnDestroy, AfterViewInit } from '@angular/core';
import { DeviceStatus } from '@core/models/enums/device-status.enum';
import {
  DeviceLocation,
  GetDeviceLocationsResponse,
} from '@core/models/interfaces/device-locations.response';
import { GET_DEVICE_LOCATIONS } from '@graphql/schema/queries/device/get-locations.query';
import { Apollo } from 'apollo-angular';
import * as L from 'leaflet';
import 'leaflet.markercluster';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrl: './map.component.scss',
  standalone: false,
})
export class MapComponent implements OnInit, AfterViewInit, OnDestroy {
  private map!: L.Map;
  private markerClusterGroup!: L.MarkerClusterGroup;
  private isMapInitialized = false;

  // Real time polling
  private pollingInterval?: ReturnType<typeof setInterval>;

  // Loading & Error handling
  loading = false;
  error: string | null = null;

  // Filter properties
  availableStatuses: DeviceStatus[] = [
    DeviceStatus.ONLINE,
    DeviceStatus.STANDBY,
    DeviceStatus.OFFLINE,
    DeviceStatus.ERROR,
    DeviceStatus.MAINTENANCE,
    DeviceStatus.NEW,
    DeviceStatus.COMMISSIONING,
    DeviceStatus.DECOMMISSIONED,
    DeviceStatus.RETIRED,
    DeviceStatus.UNKNOWN,
  ];

  selectedStatuses = new Set<DeviceStatus>();
  private allDevices: DeviceLocation[] = [];

  constructor(private readonly apollo: Apollo) {}

  ngOnInit() {
    this.fixLeafletIconIssue();
    this.startPolling();
  }

  ngAfterViewInit() {
    setTimeout(() => {
      this.initMap();
      this.loadDevices();
    }, 100);
  }

  toggleStatusFilter(status: DeviceStatus | 'ALL'): void {
    if (status === 'ALL') {
      this.selectedStatuses.clear();
    } else if (this.selectedStatuses.has(status)) {
      this.selectedStatuses.delete(status);
    } else {
      this.selectedStatuses.add(status);
    }

    // Re-render markers with filter
    this.addDeviceMarkers(this.allDevices);
  }


  getStatusBgColor(status: DeviceStatus): string {
    switch (status) {
      case DeviceStatus.ONLINE:
        return '#2AAD27';
      case DeviceStatus.STANDBY:
        return '#31882A';
      case DeviceStatus.OFFLINE:
        return '#CB2B3E';
      case DeviceStatus.ERROR:
        return '#CB8427';
      case DeviceStatus.MAINTENANCE:
        return '#CAC428';
      case DeviceStatus.UNKNOWN:
        return '#7B7B7B';
      case DeviceStatus.NEW:
        return '#2A81CB';
      case DeviceStatus.COMMISSIONING:
        return '#9C2BCB';
      case DeviceStatus.DECOMMISSIONED:
        return '#3D3D3D';
      case DeviceStatus.RETIRED:
        return '#7B7B7B';
      default:
        return '#7B7B7B';
    }
  }

  private fixLeafletIconIssue() {
    L.Marker.prototype.options.icon = L.icon({
      iconUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon.png',
      iconRetinaUrl:
        'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon-2x.png',
      shadowUrl:
        'https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png',
      iconSize: [25, 41],
      iconAnchor: [12, 41],
      popupAnchor: [1, -34],
      tooltipAnchor: [16, -28],
      shadowSize: [41, 41],
    });
  }

  private startPolling() {
    // Poll every 60 seconds
    this.pollingInterval = setInterval(() => {
      this.loadDevices();
    }, 60000);
  }

  private initMap() {
    if (this.isMapInitialized) return;

    // Initialize map centered on Morocco
    this.map = L.map('device-map', {
      center: [34.68, -1.91], // Morocco center
      zoom: 6,
      scrollWheelZoom: true,
      zoomControl: true,
    });

    // Add OpenStreetMap tiles
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 19,
      minZoom: 3,
      attribution: 'GridPulse',
    }).addTo(this.map);

    // Initialize marker cluster group
    this.markerClusterGroup = L.markerClusterGroup({
      chunkedLoading: true,
      spiderfyOnMaxZoom: true,
      showCoverageOnHover: false,
      zoomToBoundsOnClick: true,
    });

    this.map.addLayer(this.markerClusterGroup);
    this.isMapInitialized = true;

    // Force map to recalculate size after initialization
    setTimeout(() => {
      this.map.invalidateSize();
    }, 200);

    console.log('✅ Map initialized');
  }

  private loadDevices() {
    this.loading = true;
    this.error = null;

    this.apollo
      .query<GetDeviceLocationsResponse>({
        query: GET_DEVICE_LOCATIONS,
        fetchPolicy: 'network-only',
      })
      .subscribe({
        next: ({ data }) => {
          this.loading = false;

          if (!data?.getAllDevices) {
            console.warn('⚠️ No devices returned from API');
            return;
          }

          console.log('📍 Loaded devices:', data.getAllDevices);
          this.allDevices = data.getAllDevices; // Store all devices
          this.addDeviceMarkers(this.allDevices);
        },
        error: (error) => {
          this.loading = false;
          this.error = 'Failed to load device locations. Please try again.';
          console.error('❌ Error loading devices:', error);
        },
      });
  }

  private addDeviceMarkers(devices: DeviceLocation[]) {
    this.markerClusterGroup.clearLayers();

    // Apply filter
    let filteredDevices = devices.filter(
      (device) => device.gpsLat !== null && device.gpsLong !== null
    );

    // Filter by selected statuses if any are selected
    if (this.selectedStatuses.size > 0) {
      filteredDevices = filteredDevices.filter((device) =>
        this.selectedStatuses.has(device.status)
      );
    }

    console.log(`📊 Adding ${filteredDevices.length} markers to map`);

    if (filteredDevices.length === 0) {
      console.warn('⚠️ No valid devices with GPS coordinates');
      return;
    }

    filteredDevices.forEach((device) => {
      const icon = this.getDeviceIcon(device.status);
      const marker = L.marker([device.gpsLat!, device.gpsLong!], { icon });

      const popupContent = this.createPopupContent(device);
      marker.bindPopup(popupContent);

      this.markerClusterGroup.addLayer(marker);
    });

    // Fit map to show all markers
    const bounds = this.markerClusterGroup.getBounds();
    if (bounds.isValid()) {
      this.map.fitBounds(bounds, {
        padding: [50, 50],
        maxZoom: 15,
      });
    }

    // Force resize after fitting bounds
    setTimeout(() => {
      this.map.invalidateSize();
    }, 100);
  }

  private getDeviceIcon(status: DeviceStatus): L.Icon {
    const color = (() => {
      switch (status) {
        case DeviceStatus.ONLINE:
        case DeviceStatus.STANDBY:
          return 'green';

        case DeviceStatus.OFFLINE:
          return 'red';

        case DeviceStatus.ERROR:
          return 'orange';

        case DeviceStatus.MAINTENANCE:
          return 'gold';

        case DeviceStatus.NEW:
          return 'blue';

        case DeviceStatus.COMMISSIONING:
          return 'violet';

        case DeviceStatus.DECOMMISSIONED:
          return 'black';

        case DeviceStatus.RETIRED:
        case DeviceStatus.UNKNOWN:
        default:
          return 'grey';
      }
    })();

    return L.icon({
      iconUrl: `https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-${color}.png`,
      shadowUrl:
        'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
      iconSize: [25, 41],
      iconAnchor: [12, 41],
      popupAnchor: [1, -34],
      shadowSize: [41, 41],
    });
  }

  // Pop up when hovering on a markup
  private createPopupContent(device: DeviceLocation): string {
    const statusColor = this.getStatusColor(device.status);
    const lastSeenDate = new Date(device.lastSeen).toLocaleString();

    return `
      <div style="padding: 12px; min-width: 200px;">
        <h3 style="font-weight: bold; font-size: 1.125rem; margin-bottom: 8px;">${
          device.name
        }</h3>
        <div style="font-size: 0.875rem;">
          <p style="margin: 4px 0;">
            <span style="font-weight: 600;">Status:</span>
            <span style="padding: 4px 8px; border-radius: 4px; color: white; ${statusColor}">
              ${device.status}
            </span>
          </p>
          <p style="margin: 4px 0;">
            <span style="font-weight: 600;">Location:</span>
            ${device.gpsLat?.toFixed(4)}, ${device.gpsLong?.toFixed(4)}
          </p>
          <p style="margin: 4px 0; color: #6b7280;">
            <span style="font-weight: 600;">Last Seen:</span><br/>
            ${lastSeenDate}
          </p>
        </div>
      </div>
    `;
  }

  private getStatusColor(status: string): string {
    switch (status) {
      // Operational
      case 'ONLINE':
        return 'background-color: #2AAD27;'; // Green
      case 'STANDBY':
        return 'background-color: #31882A;'; // Gold (closest to teal)
      case 'OFFLINE':
        return 'background-color: #CB2B3E;'; // Red
      case 'ERROR':
        return 'background-color: #CB8427;'; // Orange
      case 'MAINTENANCE':
        return 'background-color: #CAC428;'; // Yellow
      case 'UNKNOWN':
        return 'background-color: #7B7B7B;'; // Grey
      case 'NEW':
        return 'background-color: #2A81CB;'; // Blue

      // Lifecycle
      case 'COMMISSIONING':
        return 'background-color: #9C2BCB;'; // Violet
      case 'DECOMMISSIONED':
        return 'background-color: #3D3D3D;'; // Black
      case 'RETIRED':
        return 'background-color: #7B7B7B;'; // Grey (closest to zinc)

      default:
        return 'background-color: #7B7B7B;'; // Safe fallback (Grey)
    }
  }

  ngOnDestroy() {
    if (this.map) {
      this.map.remove();
    }
    if (this.pollingInterval) {
      clearInterval(this.pollingInterval);
    }
  }
}
