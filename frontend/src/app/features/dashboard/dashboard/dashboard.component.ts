import { Component } from '@angular/core';
import { AuthService } from '@core/services/auth.service';
import { map } from 'rxjs';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
  standalone: false,
})
export class DashboardComponent {
  readonly firstname$;

  constructor(private readonly authService: AuthService) {
    this.firstname$ = this.authService.user$.pipe(
      map((user) => user?.firstname)
    );
  }
}
