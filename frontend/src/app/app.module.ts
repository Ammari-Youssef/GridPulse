import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, provideHttpClient } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

// Interceptors
import { JwtInterceptor } from './core/interceptors/jwt.interceptor';
import { GlobalHttpErrorInterceptor } from './core/interceptors/global-http-error.interceptor';
import { HttpErrorInterceptor } from './core/interceptors/http-error.interceptor';

// Custom modules
import { SharedModule } from './shared/shared.module';
import { CoreModule } from './core/core.module';
import { LayoutModule } from './layout/layout.module';
import { DashboardModule } from './features/dashboard/dashboard.module';

import { GraphQLModule } from './graphql/graphql.module';
import { ApiStatusModule } from './features/api-status/api-status.module';

@NgModule({
  declarations: [AppComponent],
  imports: [
    // API Status
    ApiStatusModule,

    // Angular Modules
    BrowserModule,
    AppRoutingModule,

    // Apollo GraphQL
    GraphQLModule,
    

    // Custom modules
    SharedModule,
    CoreModule,
    LayoutModule,

    // Feature modules
    DashboardModule,

  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: GlobalHttpErrorInterceptor,
      multi: true,
    },
    { provide: HTTP_INTERCEPTORS, useClass: HttpErrorInterceptor, multi: true },
    provideHttpClient(),
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
