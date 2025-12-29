import { Component, OnInit } from '@angular/core';
import { AuthService } from '@core/services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
  standalone: false,
})
export class AppComponent implements OnInit {
  title = 'gridpulse';

  constructor(private readonly authService: AuthService) {}

  ngOnInit(): void {
    this.authService.initAuth().subscribe();
  }
}
