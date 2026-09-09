import { Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard';
import { ClienteCreateComponent } from './components/cliente-create/cliente-create.component';
import { ChamadoCreateComponent } from './components/chamado/chamado-create/chamado-create';
import { ChamadoDetalheComponent } from './components/chamado-detalhe/chamado-detalhe';
import { ChamadoListaComponent } from './components/chamado-lista/chamado-lista';
import { ClienteListaComponent } from './components/cliente-lista/cliente-lista';



export const routes: Routes = [
  { path: '', redirectTo: 'chamados', pathMatch: 'full' },
  { path: 'chamados', component: ChamadoListaComponent },
  { path: 'chamados/novo', component: ChamadoCreateComponent },
  { path: 'chamados/:id', component: ChamadoDetalheComponent },
  { path: 'clientes', component: ClienteListaComponent },
  { path: 'clientes/novo', component: ClienteCreateComponent },
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent }
];
