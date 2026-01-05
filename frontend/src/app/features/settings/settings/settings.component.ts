import { Component, OnInit } from '@angular/core';
import { Role } from '@core/models/enums/role.enum';
import { SettingsTab } from '@core/models/interfaces/settings-tabs';
import { AuthService } from '@core/services/auth.service';
import { Observable, map } from 'rxjs';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss'],
  standalone: false,
})
export class SettingsComponent implements OnInit {
  activeTab = 'profile';
  isAdmin$: Observable<boolean>;

  tabs: SettingsTab[] = [
    { id: 'profile', label: 'Profile', icon: 'person', adminOnly: false },
    { id: 'security', label: 'Security', icon: 'lock', adminOnly: false },
    { id: 'users', label: 'User Management', icon: 'people', adminOnly: true },
  ];

  visibleTabs: SettingsTab[] = [];

  constructor(private readonly authService: AuthService) {
    this.isAdmin$ = this.authService.user$.pipe(
      map((user) => user?.role === Role.ADMIN)
    );
  }

  ngOnInit() {
    this.isAdmin$.subscribe((isAdmin) => {
      this.visibleTabs = this.tabs.filter((tab) => !tab.adminOnly || isAdmin);
    });
  }

  selectTab(tabId: string) {
    this.activeTab = tabId;
  }

  isTabActive(tabId: string): boolean {
    return this.activeTab === tabId;
  }
}
