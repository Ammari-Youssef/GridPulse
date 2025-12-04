import { ResolveFn, Routes } from '@angular/router';

// Layout
import { ShellComponent } from './layout/shell/shell.component';

// Features
import { DashboardComponent } from './features/dashboard/dashboard/dashboard.component';
import { FleetListComponent } from './features/fleets/fleet-list/fleet-list.component';
import { FleetDetailsComponent } from './features/fleets/fleet-details/fleet-details.component';
import { DeviceListComponent } from './features/devices/device-list/device-list.component';
import { DeviceDetailsComponent } from './features/devices/device-details/device-details.component';
import { AnalyticsComponent } from './features/dashboard/analytics/analytics.component';
import { MessagesComponent } from './features/messages/messages/messages.component';
import { SettingsComponent } from './features/settings/settings/settings.component';
import { DeviceManagerComponent } from './features/device-manager/device-manager/device-manager.component';
import { NotFoundComponent } from './shared/pages/not-found/not-found.component';
import { HelloComponent } from './features/hello/hello.component';
import { ForbiddenComponent } from './shared/pages/forbidden/forbidden.component';

// Guards
import { authGuard } from './core/guards/auth.guard';
import { roleGuard } from './core/guards/role.guard';

// --- Resolver ---
const idTitleResolver: ResolveFn<string> = (route) => {
  const id = route.paramMap.get('id');
  return id ? `Details – ${id}` : 'Details';
};

export const routes: Routes = [
  // Public route
  { path: 'hello', component: HelloComponent, title: 'API GridPulse' },

  // Forbidden (before wildcard)
  {
    path: 'forbidden',
    component: ForbiddenComponent,
    title: '403 – Forbidden',
  },

  // Main authenticated area
  {
    path: '',
    component: ShellComponent,
    canActivate: [authGuard],
    canActivateChild: [authGuard],
    children: [
      // Dashboard
      {
        path: '',
        component: DashboardComponent,
        title: 'GridPulse ⚡ Dashboard',
      },

      // Fleets
      { path: 'fleets', component: FleetListComponent, title: 'Fleet List' },
      {
        path: 'fleets/:id',
        component: FleetDetailsComponent,
        title: idTitleResolver,
      },

      // Devices
      { path: 'devices', component: DeviceListComponent, title: 'Device List' },
      {
        path: 'devices/:id',
        component: DeviceDetailsComponent,
        title: idTitleResolver,
      },

      // Alerts + Analytics
      { path: 'messages', component: MessagesComponent, title: 'Alerts' },
      { path: 'analytics', component: AnalyticsComponent, title: 'Analytics' },

      // --- Admin-only routes ---
      {
        path: 'settings',
        component: SettingsComponent,
        canActivate: [roleGuard],
        data: { role: 'ADMIN' },
        title: 'Admin – Settings',
      },
      {
        path: 'device-manager',
        component: DeviceManagerComponent,
        canActivate: [roleGuard],
        data: { role: 'ADMIN' },
        title: 'Admin – Device Manager',
      },
    ],
  },

  // Wildcard
  { path: '**', component: NotFoundComponent, title: 'Not Found – 404' },
];
