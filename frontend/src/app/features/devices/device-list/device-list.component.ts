import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Device } from '@core/models/classes/device.model';
import { DeviceStatus } from '@core/models/enums/device-status.enum';
import { GetAllDevicePagedResponse } from '@core/models/interfaces/get-all-device-paged.response';
import { PageRequest } from '@graphql/pagination/page.request';
import { DELETE_DEVICE_MUTATION } from '@graphql/schema/mutations/device/delete.mutation';
import { GET_ALL_DEVICE_PAGED } from '@graphql/schema/queries/device/get-all-paged.query';
import { Apollo } from 'apollo-angular';

@Component({
  selector: 'app-device-list',
  templateUrl: './device-list.component.html',
  styleUrl: './device-list.component.scss',
  standalone: false,
})
export class DeviceListComponent implements OnInit {
  devices: Device[] = [];
  loading = false;
  error: string | null = null;

  // Pagination
  currentPage = 0;
  pageSize = 10;
  totalElements = 0;
  totalPages = 0;
  pageSizeOptions = [10, 20, 50];
  Math = Math;

  // Sorting
  sortBy: string | null = null;
  sortDesc = true;

  // View modal
  selectedDevice: Device | null = null;
  showViewModal = false;

  // Delete modal
  deviceToDelete: Device | null = null;
  showDeleteModal = false;
  deleting = false;

  // Status filter
  DeviceStatus = DeviceStatus;
  availableStatuses: DeviceStatus[] = Object.values(DeviceStatus);

  selectedStatuses = new Set<DeviceStatus>();

  constructor(
    private readonly apollo: Apollo,
    private readonly snackBar: MatSnackBar
  ) {}

  ngOnInit() {
    this.loadDevices();
  }

  loadDevices() {
    this.loading = true;
    this.error = null;

    const pageRequest: PageRequest = {
      page: this.currentPage,
      size: this.pageSize,
      sortBy: this.sortBy,
      desc: this.sortDesc,
    };

    this.apollo
      .query<GetAllDevicePagedResponse>({
        query: GET_ALL_DEVICE_PAGED,
        variables: { pageRequestInput: pageRequest },
        fetchPolicy: 'network-only',
      })
      .subscribe({
        next: ({ data }) => {
          this.loading = false;
          if (data?.getAllDevicePaged) {
            const response = data.getAllDevicePaged;
            this.devices = response.content;
            this.totalElements = response.totalElements;
            this.totalPages = response.totalPages;
            this.currentPage = response.pageNumber;
            this.pageSize = response.pageSize;
          }
        },
        error: (err) => {
          this.loading = false;
          this.error = 'Failed to load devices.' + err + ' Please try again.';
          this.snackBar.open(this.error, 'Close', {
            duration: 5000,
            horizontalPosition: 'right',
            verticalPosition: 'top',
            panelClass: ['snackbar-error'],
          });
        },
      });
  }

  // Pagination
  goToPage(page: number) {
    if (page >= 0 && page < this.totalPages) {
      this.currentPage = page;
      this.loadDevices();
    }
  }

  nextPage() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.loadDevices();
    }
  }

  previousPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadDevices();
    }
  }

  changePageSize(size: number) {
    this.pageSize = size;
    this.currentPage = 0;
    this.loadDevices();
  }

  // Sorting
  sort(column: string) {
    if (this.sortBy === column) {
      this.sortDesc = !this.sortDesc;
    } else {
      this.sortBy = column;
      this.sortDesc = true;
    }
    this.currentPage = 0;
    this.loadDevices();
  }

  getPageNumbers(): number[] {
    const pages: number[] = [];
    const maxVisible = 5;
    let start = Math.max(0, this.currentPage - Math.floor(maxVisible / 2));
    const end = Math.min(this.totalPages, start + maxVisible);

    if (end - start < maxVisible) {
      start = Math.max(0, end - maxVisible);
    }

    for (let i = start; i < end; i++) {
      pages.push(i);
    }

    return pages;
  }

  // View device
  viewDevice(device: Device) {
    this.selectedDevice = device;
    this.showViewModal = true;
  }

  closeViewModal() {
    this.showViewModal = false;
    this.selectedDevice = null;
  }

  // Delete device
  confirmDelete(device: Device) {
    this.deviceToDelete = device;
    this.showDeleteModal = true;
  }

  cancelDelete() {
    this.showDeleteModal = false;
    this.deviceToDelete = null;
  }

  deleteDevice() {
    if (!this.deviceToDelete) return;

    this.deleting = true;

    this.apollo
      .mutate({
        mutation: DELETE_DEVICE_MUTATION,
        variables: { id: this.deviceToDelete.id },
      })
      .subscribe({
        next: () => {
          this.deleting = false;
          this.showDeleteModal = false;
          this.deviceToDelete = null;

          if (this.devices.length === 1 && this.currentPage > 0) {
            this.currentPage--;
          }
          this.loadDevices();
        },
        error: (err) => {
          this.deleting = false;
          this.error = 'Failed to load devices.' + err + ' Please try again.';
          this.snackBar.open(this.error, 'Close', {
            duration: 5000,
            horizontalPosition: 'right',
            verticalPosition: 'top',
            panelClass: ['snackbar-error'],
          });
        },
      });
  }

  // Helpers
  formatDate(dateString: string): string {
    return new Date(dateString).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
    });
  }

  formatDateTime(dateString: string): string {
    return new Date(dateString).toLocaleString('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    });
  }

  getStatusColor(status: DeviceStatus): string {
    switch (status) {
      case this.DeviceStatus.ONLINE:
        return 'bg-green-100 text-green-800';
      case DeviceStatus.STANDBY:
        return 'bg-teal-100 text-teal-800';
      case DeviceStatus.OFFLINE:
        return 'bg-red-100 text-red-800';
      case DeviceStatus.ERROR:
        return 'bg-orange-100 text-orange-800';
      case DeviceStatus.MAINTENANCE:
        return 'bg-yellow-100 text-yellow-800';
      case DeviceStatus.UNKNOWN:
        return 'bg-gray-100 text-gray-800';
      case DeviceStatus.NEW:
        return 'bg-blue-100 text-blue-800';
      case DeviceStatus.COMMISSIONING:
        return 'bg-indigo-100 text-indigo-800';
      case DeviceStatus.DECOMMISSIONED:
        return 'bg-slate-100 text-slate-800';
      case DeviceStatus.RETIRED:
        return 'bg-zinc-100 text-zinc-800';
      default:
        return 'bg-gray-100 text-gray-800';
    }
  }

  get endIndex(): number {
    return Math.min((this.currentPage + 1) * this.pageSize, this.totalElements);
  }
}
