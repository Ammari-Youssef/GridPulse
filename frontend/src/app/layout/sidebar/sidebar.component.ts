import { Component } from '@angular/core';
import { Role } from '@core/models/enums/role.enum';
import { AuthService } from '@core/services/auth.service';
import { Observable } from 'rxjs';


@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss',
  standalone: false,
})
export class SidebarComponent {
  userRole$: Observable<Role | null>;

  constructor(private readonly authService: AuthService) {
    this.userRole$ = this.authService.getUserRole$();
  }

  // Expose Role enum to template
  readonly Role = Role;
}
