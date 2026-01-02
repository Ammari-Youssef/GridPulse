import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-stats-card',
  templateUrl: './stats-card.component.html',
  styleUrl: './stats-card.component.scss',
  standalone: false,
})
export class StatsCardComponent {
  @Input() title = '';
  @Input() value: number | string = 0;
  @Input() icon = 'info';
  @Input() iconColor = 'text-gray-500';
  @Input() bgColor = 'bg-gray-100';
  @Input() trend?: string; // e.g., "+12%" or "-5%"
  @Input() subtitle?: string;

  getTrendClass(): string {
    if (!this.trend) return '';
    const isPositive = this.trend.startsWith('+');
    return isPositive
      ? 'bg-green-100 text-green-700'
      : 'bg-red-100 text-red-700';
  }
}
