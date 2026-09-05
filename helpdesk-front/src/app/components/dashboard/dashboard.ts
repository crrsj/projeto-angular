import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardService } from '../../services/dashboard';
import { Dashboard } from '../../models/dashboard.model';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class DashboardComponent implements OnInit {
  private readonly dashboardService = inject(DashboardService);

  metrics: Dashboard | null = null;
  loading = true;
  error = '';

  ngOnInit(): void {
    this.carregarMetricas();
  }

  carregarMetricas(): void {
    this.loading = true;
    this.dashboardService.getDashboardMetrics().subscribe({
      next: (data) => {
        this.metrics = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Erro ao carregar dados da Dashboard', err);
        this.error = 'Não foi possível carregar as métricas. Verifique se o servidor backend está em execução.';
        this.loading = false;
      }
    });
  }
}


