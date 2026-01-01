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
      console.log('🔄 Loading user from token...');
      return authService
        .loadCurrentUser()
        .toPromise()
        .then(() => {
          console.log('✅ User loaded successfully');
        })
        .catch((err) => {
          console.error('❌ Failed to load user, clearing tokens', err);
          tokenStorage.clear();
        });
    }

    // No token, just resolve immediately
    return Promise.resolve();
  };
}
