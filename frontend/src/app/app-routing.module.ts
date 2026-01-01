import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

// Layout
import { ShellComponent } from '@layout/shell/shell.component';

// Features
import { DashboardComponent } from '@features/dashboard/dashboard/dashboard.component';
import { FleetListComponent } from '@features/fleets/fleet-list/fleet-list.component';
import { FleetDetailsComponent } from '@features/fleets/fleet-details/fleet-details.component';
import { DeviceListComponent } from '@features/devices/device-list/device-list.component';
import { DeviceDetailsComponent } from '@features/devices/device-details/device-details.component';
import { AnalyticsComponent } from '@features/dashboard/analytics/analytics.component';
import { MessagesComponent } from '@features/messages/messages/messages.component';
import { SettingsComponent } from '@features/settings/settings/settings.component';
import { DeviceManagerComponent } from '@features/device-manager/device-manager/device-manager.component';

// Shared Pages
import { ApiStatusComponent } from '@features/api-status/api-status.component';
import { NotFoundComponent } from '@shared/pages/not-found/not-found.component';
import { ForbiddenComponent } from '@shared/pages/forbidden/forbidden.component';

// Guards (CLASS-BASED)
import { AuthGuard } from '@guards/auth.guard';
import { RoleGuard } from '@guards/role.guard';

// Resolver (CLASS-BASED)
import { IdTitleResolver } from '@resolvers/id-title.resolver';

const routes: Routes = [
  {
    path: 'api-status',
    component: ApiStatusComponent,
    title: 'API Status GridPulse',
  },
  {
    path: 'login',
    loadChildren: () =>
      import('./features/auth/auth.module').then((m) => m.AuthModule),
  },
  {
    path: 'forbidden',
    component: ForbiddenComponent,
    title: '403 – Forbidden',
  },

  {
    path: '',
    component: ShellComponent,
    canActivate: [AuthGuard],
    // canActivateChild: [AuthGuard],
    children: [
      {
        path: '',
        component: DashboardComponent,
        title: 'GridPulse ⚡ Dashboard',
      },

      { path: 'fleets', component: FleetListComponent, title: 'Fleet List' },
      {
        path: 'fleets/:id',
        component: FleetDetailsComponent,
        resolve: { title: IdTitleResolver },
      },

      { path: 'devices', component: DeviceListComponent, title: 'Device List' },
      {
        path: 'devices/:id',
        component: DeviceDetailsComponent,
        resolve: { title: IdTitleResolver },
      },

      { path: 'messages', component: MessagesComponent, title: 'Alerts' },
      { path: 'analytics', component: AnalyticsComponent, title: 'Analytics' },

      {
        path: 'settings',
        component: SettingsComponent,
        canActivate: [RoleGuard],
        data: { role: 'ADMIN' },
        title: 'Admin – Settings',
      },
      {
        path: 'device-manager',
        component: DeviceManagerComponent,
        canActivate: [RoleGuard],
        data: { role: 'ADMIN' },
        title: 'Admin – Device Manager',
      },
    ],
  },

  { path: '**', component: NotFoundComponent, title: 'Not Found – 404' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
