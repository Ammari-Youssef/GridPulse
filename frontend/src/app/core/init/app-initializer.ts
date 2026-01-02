import { AuthService } from '@services/auth.service';
import { TokenStorageService } from '@services/token-storage.service';

export function initializeApp(
  authService: AuthService,
  tokenStorage: TokenStorageService
) {
  return (): Promise<unknown> => {
    // Check if token exists
    const token = tokenStorage.access;

    if (token) {
      // Load user data before app starts

      return authService
        .loadCurrentUser()
        .toPromise()
        .catch(() => {
          tokenStorage.clear();
        });
    }

    // No token, just resolve immediately
    return Promise.resolve();
  };
}
