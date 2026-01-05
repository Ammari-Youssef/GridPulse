import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, provideHttpClient } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

// Interceptors
import { JwtInterceptor } from '@interceptors/jwt.interceptor';
import { GlobalHttpErrorInterceptor } from '@interceptors/global-http-error.interceptor';
import { HttpErrorInterceptor } from '@interceptors/http-error.interceptor';

// Custom modules
import { SharedModule } from '@shared/shared.module';
import { CoreModule } from '@core/core.module';
import { LayoutModule } from '@layout/layout.module';

import { AuthModule } from '@features/auth/auth.module';
import { ApiStatusModule } from '@features/api-status/api-status.module';
import { GraphQLModule } from '@graphql/graphql.module';

// Initializers
import { initializeApp } from '@core/init/app-initializer';
// Services
import { AuthService } from '@core/services/auth.service';
import { TokenStorageService } from '@core/services/token-storage.service';

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
    AuthModule,
    SharedModule,
    CoreModule,
    LayoutModule,
    ApiStatusModule,
    
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializeApp,
      deps: [AuthService, TokenStorageService],
      multi: true,
    },
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: HttpErrorInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: GlobalHttpErrorInterceptor, multi: true },
    provideHttpClient(),
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
