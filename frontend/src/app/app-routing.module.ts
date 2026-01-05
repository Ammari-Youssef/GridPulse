import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

// Layout
import { ShellComponent } from '@layout/shell/shell.component';

// Shared Pages 
import { NotFoundComponent } from '@shared/pages/not-found/not-found.component';
import { ForbiddenComponent } from '@shared/pages/forbidden/forbidden.component';

// Guards
import { AuthGuard } from '@guards/auth.guard';

const routes: Routes = [
  {
    path: 'login',
    loadChildren: () =>
      import('./features/auth/auth.module').then((m) => m.AuthModule),
  },
  {
    path: 'api-status',
    loadChildren: () =>
      import('./features/api-status/api-status.module').then(
        (m) => m.ApiStatusModule
      ),
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
    children: [
      {
        path: '',
        loadChildren: () =>
          import('./features/dashboard/dashboard.module').then(
            (m) => m.DashboardModule
          ),
      },
      {
        path: 'devices',
        loadChildren: () =>
          import('./features/devices/devices.module').then(
            (m) => m.DevicesModule
          ),
      },
      {
        path: 'fleets',
        loadChildren: () =>
          import('./features/fleets/fleets.module').then((m) => m.FleetsModule),
      },
      {
        path: 'messages',
        loadChildren: () =>
          import('./features/messages/messages.module').then(
            (m) => m.MessagesModule
          ),
      },
      {
        path: 'analytics',
        loadChildren: () =>
          import('./features/analytics/analytics.module').then(
            (m) => m.AnalyticsModule
          ),
      },
      {
        path: 'settings',
        loadChildren: () =>
          import('./features/settings/settings.module').then(
            (m) => m.SettingsModule
          ),
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
