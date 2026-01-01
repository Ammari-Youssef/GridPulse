import { Injectable } from '@angular/core';
@Injectable({
  providedIn: 'root',
})
export class TokenStorageService {
  private readonly ACCESS_TOKEN_KEY = 'access_token';
  private readonly REFRESH_TOKEN_KEY = 'refresh_token';

  save(accessToken: string, refreshToken: string): void {
    console.log('💾 Saving tokens');
    localStorage.setItem(this.ACCESS_TOKEN_KEY, accessToken);
    localStorage.setItem(this.REFRESH_TOKEN_KEY, refreshToken);
  }

  get access(): string | null {
    const token = localStorage.getItem(this.ACCESS_TOKEN_KEY);
    console.log('🔍 Getting token:', token ? 'EXISTS' : 'NULL');
    return token;
  }

  get refresh(): string | null {
    return localStorage.getItem(this.REFRESH_TOKEN_KEY);
  }

  clear(): void {
    console.log('🗑️ Clearing tokens');
    localStorage.removeItem(this.ACCESS_TOKEN_KEY);
    localStorage.removeItem(this.REFRESH_TOKEN_KEY);
  }
}