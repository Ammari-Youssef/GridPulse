import {Component, OnInit, OnDestroy, Output, EventEmitter} from '@angular/core';
import {Router} from '@angular/router';
import {Message} from '@core/models/classes/message.model';
import {User} from '@core/models/classes/User.model';
import {MessageStatus} from '@core/models/enums/message/MessageStatus.enum';
import {MessageType} from '@core/models/enums/message/MessageType.enum';
import {Severity} from '@core/models/enums/message/Severity.enum';
import {GetAllMessagesPagedResponse} from '@core/models/interfaces/get-all-message-paged.response';
import {AuthService} from '@core/services/auth.service';
import {PageRequest} from '@graphql/pagination/page.request';
import {GET_ALL_MESSAGES_PAGED} from '@graphql/schema/queries/message/get-all-paged.query';
import {SnackbarService} from '@shared/services/snackbar.service';
import {Apollo} from 'apollo-angular';
import {Observable, Subject, interval} from 'rxjs';
import {takeUntil} from 'rxjs/operators';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
  standalone: false,
})
export class NavbarComponent implements OnInit, OnDestroy {
  @Output() sidebarToggle = new EventEmitter<void>();

  user$: Observable<User | null>;
  recentMessages: Message[] = [];
  unreadCount = 0;
  loadingNotifications = false;
  isDarkMode = false;

  isLargeScreen = false;
  toggleSidebar(): void {
    this.sidebarToggle.emit();
  }
  private checkScreenSize(): void {
    this.isLargeScreen = window.innerWidth >= 1024;
  }

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

  private destroy$ = new Subject<void>();

  constructor(
    private readonly authService: AuthService,
    private readonly snackbar: SnackbarService,
    private readonly router: Router,
    private readonly apollo: Apollo
  ) {
    this.user$ = this.authService.user$;
  }

  ngOnInit(): void {
    this.loadRecentNotifications();

    // Poll for new notifications every 30 seconds
    interval(30000)
      .pipe(takeUntil(this.destroy$))
      .subscribe(() => {
        this.loadRecentNotifications();
      });

    // Initial screen size check
    this.checkScreenSize();
    window.addEventListener('resize', () => this.checkScreenSize());
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  loadRecentNotifications(): void {
    this.loadingNotifications = true;

    const pageRequest: PageRequest = {
      page: this.currentPage,
      size: this.pageSize,
      sortBy: this.sortBy,
      desc: this.sortDesc,
    };

    this.apollo
      .query<GetAllMessagesPagedResponse>({
        query: GET_ALL_MESSAGES_PAGED,
        variables: {
          pageRequestInput: pageRequest,
        },
        fetchPolicy: 'network-only',
      })
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: ({ data }) => {
          this.loadingNotifications = false;

          if (data?.getAllMessagePaged) {
            const allMessages = data.getAllMessagePaged.content;

            // Filter for NEW status and take only 5 most recent
            this.recentMessages = allMessages
              .filter((m) => m.status === MessageStatus.NEW)
              .slice(0, 5);

            // Count all NEW messages (unread)
            this.unreadCount = allMessages.filter(
              (m) => m.status === MessageStatus.NEW
            ).length;
          }
        },
        error: () => {
          this.loadingNotifications = false;
          this.snackbar.showError('Failed to load notifications');
        },
      });
  }

  // navigateToMessage(messageId: string): void {
  //   this.router.navigate(['/messages', messageId]);
  // }

  navigateToAllMessages(): void {
    this.router.navigate(['/messages']);
  }

  getSeverityClass(severity: Severity): string {
    return `severity-${severity.toLowerCase()}`;
  }

  getMessageIcon(messageType: MessageType): string {
    const iconMap: Record<MessageType, string> = {
      [MessageType.IDS]: 'security',
      [MessageType.HEARTBEAT]: 'favorite',
      [MessageType.SYSTEM]: 'settings',
      [MessageType.SOFTWARE]: 'code',
      [MessageType.BMS]: 'battery_charging_full',
      [MessageType.METER]: 'speed',
      [MessageType.INVERTER]: 'power',
    };
    return iconMap[messageType] || 'notifications';
  }

  getTimeAgo(dateString: string): string {
    const date = new Date(dateString);
    const now = new Date();
    const seconds = Math.floor((now.getTime() - date.getTime()) / 1000);

    if (seconds < 60) return 'Just now';
    if (seconds < 3600) return `${Math.floor(seconds / 60)}m ago`;
    if (seconds < 86400) return `${Math.floor(seconds / 3600)}h ago`;
    if (seconds < 604800) return `${Math.floor(seconds / 86400)}d ago`;
    return date.toLocaleDateString();
  }

  logout(): void {
    this.authService.logout().subscribe({
      next: () => {
        this.router.navigate(['/login']);
      },
      error: () => {
        this.router.navigate(['/login']);
      },
    });
  }

  getAvatarUrl(): string {
    return 'https://avatar.iran.liara.run/public/boy';
  }

  toggleTheme(): void {
    this.isDarkMode = !this.isDarkMode;
    // Implement your theme switching logic here in future issues
  }
}
