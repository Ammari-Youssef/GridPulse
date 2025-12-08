import { Injectable } from '@angular/core';

const ACCESS_TOKEN_KEY = 'token';

@Injectable({
  providedIn: 'root',
})
export class TokenStorageService {
  // Store token
  setToken(token: string): void {
    localStorage.setItem(ACCESS_TOKEN_KEY, token);
  }

  // Retrieve token
  getToken(): string | null {
    return localStorage.getItem(ACCESS_TOKEN_KEY);
  }

  // Remove token
  clearToken(): void {
    localStorage.removeItem(ACCESS_TOKEN_KEY);
  }

  // Check if authenticated
  isAuthenticated(): boolean {
    return true //!!this.getToken();
  }

  // 🔥 Decode JWT payload
  getUserRole(): 'ADMIN' | 'USER' | null {
    const token = this.getToken();
    if (!token) return null;

    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.role || null;
    } catch {
      return null
    }
  }
}
