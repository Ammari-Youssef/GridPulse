import { Component, OnInit } from '@angular/core';
import { Apollo } from 'apollo-angular';
import { SnackbarService } from '@shared/services/snackbar.service';
import { Message } from '@core/models/classes/message.model';
import { Severity } from '@core/models/enums/message/Severity.enum';
import { MessageType } from '@core/models/enums/message/MessageType.enum';
import { GET_ALL_MESSAGES_PAGED } from '@graphql/schema/queries/message/get-all-paged.query';
import { GetAllMessagesPagedResponse } from '@core/models/interfaces/get-all-message-paged.response';
import { PageRequest } from '@graphql/pagination/page.request';

@Component({
  selector: 'app-messages',
  templateUrl: './messages.component.html',
  styleUrls: ['./messages.component.scss'],
  standalone: false,
})
export class MessagesComponent implements OnInit {
  messages: Message[] = [];
  loading = false;
  error: string | null = null;

  // Pagination
  currentPage = 0;
  pageSize = 20;
  totalElements = 0;
  totalPages = 0;
  pageSizeOptions = [10, 20, 50, 100];
  Math = Math;

  // Sorting
  sortBy: string | null = 'receivedAt';
  sortDesc = true;

  // Enums for template
  Severity = Severity;
  MessageType = MessageType;

  // View modal
  selectedMessage: Message | null = null;
  showViewModal = false;

  constructor(
    private readonly apollo: Apollo,
    private readonly snackbar: SnackbarService
  ) {}

  ngOnInit() {
    this.loadMessages();
  }

  loadMessages() {
    this.loading = true;
    this.error = null;

    const pageRequest: PageRequest = {
      page: this.currentPage,
      size: this.pageSize,
      sortBy: this.sortBy,
      desc: this.sortDesc,
    };

    this.apollo
      .query<GetAllMessagesPagedResponse>({
        query: GET_ALL_MESSAGES_PAGED,
        variables: { pageRequestInput: pageRequest },
        fetchPolicy: 'network-only',
      })
      .subscribe({
        next: ({ data }) => {
          this.loading = false;

          if (data?.getAllMessagePaged) {
            const response = data.getAllMessagePaged;
            this.messages = response.content;
            this.totalElements = response.totalElements;
            this.totalPages = response.totalPages;
            this.currentPage = response.pageNumber;
            this.pageSize = response.pageSize;
          }
        },
        error: (err) => {
          this.loading = false;
          this.error = 'Failed to load messages. ' + err.message;
          this.snackbar.showError(this.error);
        },
      });
  }

  // Pagination
  goToPage(page: number) {
    if (page >= 0 && page < this.totalPages) {
      this.currentPage = page;
      this.loadMessages();
    }
  }

  nextPage() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.loadMessages();
    }
  }

  previousPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadMessages();
    }
  }

  changePageSize(size: number) {
    this.pageSize = size;
    this.currentPage = 0;
    this.loadMessages();
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

  // Sorting
  sort(column: string) {
    if (this.sortBy === column) {
      this.sortDesc = !this.sortDesc;
    } else {
      this.sortBy = column;
      this.sortDesc = true;
    }
    this.currentPage = 0;
    this.loadMessages();
  }

  // View modal
  viewMessage(message: Message) {
    this.selectedMessage = message;
    this.showViewModal = true;
  }

  closeViewModal() {
    this.showViewModal = false;
    this.selectedMessage = null;
  }

  // Helpers
  formatDateTime(dateString: string): string {
    if (!dateString) return 'N/A';
    return new Date(dateString).toLocaleString('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    });
  }

  getSeverityColor(severity: Severity): string {
    switch (severity) {
      case Severity.CRITICAL:
        return 'bg-red-100 text-red-800';
      case Severity.MEDIUM:
        return 'bg-orange-100 text-orange-800';
      case Severity.LOW:
        return 'bg-yellow-100 text-yellow-800';
      case Severity.INFO:
        return 'bg-blue-100 text-blue-800';
      default:
        return 'bg-gray-100 text-gray-800';
    }
  }

  getTypeColor(type: MessageType): string {
    switch (type) {
      case MessageType.IDS:
        return 'bg-red-100 text-red-800';
      case MessageType.SYSTEM:
        return 'bg-purple-100 text-purple-800';
      case MessageType.SOFTWARE:
        return 'bg-indigo-100 text-indigo-800';
      case MessageType.BMS:
        return 'bg-green-100 text-green-800';
      case MessageType.METER:
        return 'bg-cyan-100 text-cyan-800';
      case MessageType.INVERTER:
        return 'bg-amber-100 text-amber-800';
      case MessageType.HEARTBEAT:
        return 'bg-teal-100 text-teal-800';
      default:
        return 'bg-gray-100 text-gray-800';
    }
  }

  formatPayload(messageText: any): string {
    if (!messageText) return 'N/A';
    try {
      return JSON.stringify(messageText, null, 2);
    } catch {
      return String(messageText);
    }
  }

  get endIndex(): number {
    return Math.min((this.currentPage + 1) * this.pageSize, this.totalElements);
  }
}