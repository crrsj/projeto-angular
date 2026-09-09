import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ChamadoService } from '../../../services/chamado.service';
import { Cliente } from '../../../models/cliente.model';

@Component({
  selector: 'app-chamado-create',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
 templateUrl: './chamado-create.html'
})
export class ChamadoCreateComponent implements OnInit {
  private fb = inject(FormBuilder);
  private chamadoService = inject(ChamadoService);
  private router = inject(Router);

  clientes: Cliente[] = [];
  mensagemErro: string | null = null;

  form: FormGroup = this.fb.group({
    clienteId: ['', [Validators.required]],
   assunto: ['', [Validators.required, Validators.minLength(5)]],
    descricao: ['', [Validators.required, Validators.minLength(10)]],
    prioridade: ['MEDIA', [Validators.required]],
    setor: ['SUPORTE', [Validators.required]]
  });

  ngOnInit(): void {
    this.carregarClientes();
  }
carregarClientes(): void {
  this.chamadoService.listarClientes().subscribe({
    next: (dados: any) => {
      // Se a resposta do backend for um Page do Spring Data (dados.content)
      console.log('Dados recebidos da API de clientes:', dados);
      if (dados && Array.isArray(dados.content)) {
        this.clientes = dados.content;
      }
      // Se a resposta já for uma lista direta ([])
      else if (Array.isArray(dados)) {
        this.clientes = dados;
      }
      else {
        this.clientes = [];
      }
    },
    error: () => {
      this.mensagemErro = 'Erro ao carregar a lista de clientes para vinculação.';
    }
  });
}

  salvar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    // Extrai o clienteId e agrupa o restante dos dados no chamadoDTO
    const { clienteId, ...chamadoDTO } = this.form.value;
    console.log('Payload enviado ao backend:', chamadoDTO);

    this.chamadoService.abrirChamado(Number(clienteId), chamadoDTO).subscribe({
      next: () => {
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        this.mensagemErro = err?.error?.message || 'Erro ao abrir o chamado. Tente novamente.';
      }
    });
  }

}

