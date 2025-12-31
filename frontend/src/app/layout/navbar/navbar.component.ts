import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '@core/models/classes/User.model';
import { AuthService } from '@core/services/auth.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
  standalone: false,
})
export class NavbarComponent {
  user$: Observable<User | null>;

  constructor(
    private readonly authService: AuthService,
    private readonly router: Router
  ) {
    this.user$ = this.authService.user$;
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  getAvatarUrl(): string {
    return 'https://avatar.iran.liara.run/public/boy';
  }

  isDarkMode = false;

  toggleTheme() {
    this.isDarkMode = !this.isDarkMode;
    // Implement your theme switching logic here in future issues
    // For example: document.body.classList.toggle('dark-theme');
  }
}