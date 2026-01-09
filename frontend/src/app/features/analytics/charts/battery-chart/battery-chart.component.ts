import {
  AfterViewInit,
  Component,
  ElementRef,
  Input,
  OnChanges,
  OnDestroy,
  SimpleChanges,
  ViewChild,
} from '@angular/core';
import { ChartConfig } from '@core/models/interfaces/analytics/device-analytics.interface';
import { Chart, ChartConfiguration, registerables } from 'chart.js';

Chart.register(...registerables);

@Component({
  selector: 'app-battery-chart',
  standalone: false,
  templateUrl: './battery-chart.component.html',
  styleUrl: './battery-chart.component.scss',
})
export class BatteryChartComponent
  implements OnChanges, AfterViewInit, OnDestroy
{
  @Input() chartData!: ChartConfig;
  @Input() title = 'Battery Metrics';
  @ViewChild('chartCanvas') chartCanvas!: ElementRef<HTMLCanvasElement>;

  private chart: Chart | null = null;

  ngAfterViewInit(): void {
    if (this.chartData) {
      this.createChart();
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['chartData'] && !changes['chartData'].firstChange) {
      this.updateChart();
    }
  }

  ngOnDestroy(): void {
    if (this.chart) {
      this.chart.destroy();
    }
  }

  private getChartType(): 'line' | 'bar' {
    return 'line'; // 'line' for battery, 'bar' for energy
  }

  private createChart(): void {
    if (!this.chartCanvas) return;

    const config: ChartConfiguration = {
      type: this.getChartType(), // 'line' for battery, 'bar' for energy
      data: {
        labels: this.chartData.labels,
        datasets: this.chartData.datasets,
      },
      options: {
        responsive: true,
        maintainAspectRatio: false, // Important for responsive height
        plugins: {
          legend: {
            display: true,
            position: 'top',
          },
          title: {
            display: true,
            text: this.title,
            font: {
              size: 16,
              weight: 'bold',
            },
          },
        },
        scales: {
          y: {
            beginAtZero: true,
          },
        },
      },
    };

    this.chart = new Chart(this.chartCanvas.nativeElement, config);
  }

  private updateChart(): void {
    if (this.chart) {
      this.chart.data.labels = this.chartData.labels;
      this.chart.data.datasets = this.chartData.datasets;
      this.chart.update();
    } else {
      this.createChart();
    }
  }
}
