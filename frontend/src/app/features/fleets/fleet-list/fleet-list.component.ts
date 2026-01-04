import { Component, OnInit } from '@angular/core';
import { Apollo } from 'apollo-angular';
import { GET_ALL_FLEET_PAGED } from '@graphql/schema/queries/fleet/get-all-paged.query';
import { Fleet } from '@core/models/classes/fleet.model';
import { PageRequest } from '@graphql/pagination/page.request';
import { GetAllFleetPagedResponse } from '@core/models/interfaces/get-all-fleet-paged.response';
import { DELETE_FLEET_MUTATION } from '@graphql/schema/mutations/fleet/delete.mutation';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-fleet-list',
  templateUrl: './fleet-list.component.html',
  styleUrls: ['./fleet-list.component.scss'],
  standalone: false,
})
export class FleetListComponent implements OnInit {
  fleets: Fleet[] = [];
  loading = false;
  error: string | null = null;

  // Pagination
  currentPage = 0;
  pageSize = 10;
  totalElements = 0;
  totalPages = 0;
  pageSizeOptions = [10, 20, 50];

  // Sorting
  sortBy: string | null = null;
  sortDesc = true;

  // View modal state
  selectedFleet: Fleet | null = null;
  showViewModal = false;

  // Delete confirmation
  fleetToDelete: Fleet | null = null;
  showDeleteModal = false;
  deleting = false;


  constructor(
    private readonly apollo: Apollo,
    private readonly snackBar: MatSnackBar
  ) {}

  ngOnInit() {
    this.loadFleets();
  }

  loadFleets() {
    this.loading = true;
    this.error = null;

    const pageRequest: PageRequest = {
      page: this.currentPage,
      size: this.pageSize,
      sortBy: this.sortBy,
      desc: this.sortDesc,
    };

    this.apollo
      .query<GetAllFleetPagedResponse>({
        query: GET_ALL_FLEET_PAGED,
        variables: {
          pageRequestInput: pageRequest,
        },
        fetchPolicy: 'network-only',
      })
      .subscribe({
        next: ({ data }) => {
          this.loading = false;
          if (data?.getAllFleetPaged) {
            const response = data.getAllFleetPaged;
            this.fleets = response.content;
            this.totalElements = response.totalElements;
            this.totalPages = response.totalPages;
            this.currentPage = response.pageNumber;
            this.pageSize = response.pageSize;
          }
        },
        error: (err) => {
          this.loading = false;
          const error = this.parseError(err);
          this.error = error;

          this.snackBar.open(error, 'Close', {
            duration: 5000,
            horizontalPosition: 'right',
            verticalPosition: 'top',
            panelClass: ['snackbar-error'],
          });
        },
      });
  }

  // Pagination controls
  goToPage(page: number) {
    if (page >= 0 && page < this.totalPages) {
      this.currentPage = page;
      this.loadFleets();
    }
  }

  nextPage() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.loadFleets();
    }
  }

  previousPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadFleets();
    }
  }

  changePageSize(size: number) {
    this.pageSize = size;
    this.currentPage = 0; // Reset to first page
    this.loadFleets();
  }

  // Sorting
  sort(column: string) {
    if (this.sortBy === column) {
      this.sortDesc = !this.sortDesc;
    } else {
      this.sortBy = column;
      this.sortDesc = true;
    }
    this.currentPage = 0; // Reset to first page
    this.loadFleets();
  }

  // Helper for pagination range
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

  // Format date
  formatDate(dateString: string): string {
    return new Date(dateString).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
    });
  }

  get endIndex(): number {
    return Math.min((this.currentPage + 1) * this.pageSize, this.totalElements);
  }

  // View fleet details
  viewFleet(fleet: Fleet) {
    this.selectedFleet = fleet;
    this.showViewModal = true;
  }

  closeViewModal() {
    this.showViewModal = false;
    this.selectedFleet = null;
  }

  // Delete fleet
  confirmDelete(fleet: Fleet) {
    this.fleetToDelete = fleet;
    this.showDeleteModal = true;
  }

  cancelDelete() {
    this.showDeleteModal = false;
    this.fleetToDelete = null;
  }

  deleteFleet() {
    if (!this.fleetToDelete) return;

    this.deleting = true;

    this.apollo
      .mutate({
        mutation: DELETE_FLEET_MUTATION,
        variables: {
          id: this.fleetToDelete.id,
        },
      })
      .subscribe({
        next: () => {
          this.deleting = false;
          this.showDeleteModal = false;
          this.fleetToDelete = null;

          // Reload current page or adjust if last item on page
          if (this.fleets.length === 1 && this.currentPage > 0) {
            this.currentPage--;
          }
          this.loadFleets();
        },
        error: (err) => {
          this.deleting = false;
          this.error = 'Failed to delete fleet. Please try again.';
          
            this.snackBar.open(this.error, 'Close', {
            duration: 5000,
            horizontalPosition: 'right',
            verticalPosition: 'top',
            panelClass: ['snackbar-error'],
          });
          console.error('❌ Error deleting fleet:', err);
        },
      });
  }

  // Helpers
  private parseError(err: unknown): string {
    if (err && typeof err === 'object') {
      const e = err as {
        graphQLErrors?: { message: string }[];
        networkError?: { message: string };
        message?: string;
      };

      if (e.graphQLErrors?.length) {
        return e.graphQLErrors.map((err) => err.message).join(', ');
      }

      if (e.networkError) {
        return 'Network error: ' + e.networkError.message;
      }

      if (e.message) {
        return e.message;
      }
    }

    return 'Failed to load fleets. Please try again.';
  }
}
