import {
  ApplicationConfig,
  provideZoneChangeDetection
} from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { graphqlProvider } from './graphql/config/graphql.provider';
import { jwtInterceptor } from './core/interceptors/jwt.interceptor';
import { globalHttpErrorInterceptor } from './core/interceptors/global-http-error.interceptor';
import { httpErrorInterceptor } from './core/interceptors/http-error.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(
      withInterceptors([
        jwtInterceptor,
        globalHttpErrorInterceptor,
        httpErrorInterceptor,
      ])
    ),
    graphqlProvider,
  ],
};
