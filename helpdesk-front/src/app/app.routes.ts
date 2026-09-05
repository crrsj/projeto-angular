import { Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard';
import { ClienteCreateComponent } from './components/cliente-create/cliente-create.component';
import { ChamadoCreateComponent } from './components/chamado/chamado-create/chamado-create';
import { ChamadoDetalheComponent } from './components/chamado-detalhe/chamado-detalhe'; // <--- Adicione esta linha

export const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'clientes/novo', component: ClienteCreateComponent },
  { path: 'chamados/novo', component: ChamadoCreateComponent },
  { path: 'chamados/:id',component: ChamadoDetalheComponent },
  { path: '**', redirectTo: 'dashboard' }
];
