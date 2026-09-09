import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { RespostaService } from '../../services/resposta.service';
import { ChamadoService } from '../../services/chamado.service';
import { RespostaRequisicaoDTO } from '../../models/resposta.model';

@Component({
  selector: 'app-chamado-detalhe',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './chamado-detalhe.html',
  styleUrls: ['./chamado-detalhe.css']
})
export class ChamadoDetalheComponent implements OnInit {
  private fb = inject(FormBuilder);
  private route = inject(ActivatedRoute);
  private respostaService = inject(RespostaService);
  private chamadoService = inject(ChamadoService);
  private router = inject(Router);

  chamadoId!: number;
  respostas: RespostaRequisicaoDTO[] = [];
  loading = false;
  mensagemSucesso = '';
  mensagemErro = '';

  protocoloBusca: string = '';
  chamadoEncontrado: any = null;

  formResposta: FormGroup = this.fb.group({
    descricao: ['', [Validators.required, Validators.minLength(5)]]
  });

  ngOnInit(): void {
    this.chamadoId = Number(this.route.snapshot.paramMap.get('id'));
    if (this.chamadoId) {
      this.carregarRespostas();
    }
  }

  carregarRespostas(): void {
    this.respostaService.listarRespostas(this.chamadoId).subscribe({
      next: (dados: any) => {
        this.respostas = Array.isArray(dados) ? dados : [];
      },
      error: (err) => {
        console.error('Erro ao buscar respostas:', err);
      }
    });
  }

  enviarResposta(): void {
    if (this.formResposta.invalid) {
      this.formResposta.markAllAsTouched();
      return;
    }

    this.loading = true;
    this.respostaService.adicionarResposta(this.chamadoId, this.formResposta.value).subscribe({
      next: () => {
        this.mensagemSucesso = 'Resposta adicionada com sucesso!';
        this.mensagemErro = '';
        this.formResposta.reset();
        this.loading = false;
        this.carregarRespostas();
      },
      error: (err) => {
        if (err.status === 201 || err.status === 200) {
          this.mensagemSucesso = 'Resposta adicionada com sucesso!';
          this.mensagemErro = '';
          this.formResposta.reset();
          this.loading = false;
          this.carregarRespostas();
        } else {
          this.mensagemErro = 'Falha ao registrar resposta. Tente novamente.';
          this.loading = false;
        }
      }
    });
  }

  /*

  pesquisarPorProtocolo(): void {
    if (!this.protocoloBusca) return;

    this.chamadoService.buscarPorProtocolo(this.protocoloBusca).subscribe({
      next: (resposta) => {
        this.chamadoEncontrado = resposta;
        this.mensagemErro = '';
      },
      error: (err: any) => {
        this.chamadoEncontrado = null;
        this.mensagemErro = 'Chamado não encontrado para o protocolo informado.';
      }
    });
  }

  */

  pesquisarPorProtocolo() {
  if (!this.protocoloBusca.trim()) {
    this.mensagemErro = 'Por favor, digite um protocolo válido.';
    return;
  }

  this.mensagemErro = '';
  this.mensagemSucesso = '';

  this.chamadoService.buscarPorProtocolo(this.protocoloBusca).subscribe({
    next: (dados) => {
      this.chamadoEncontrado = dados;

      // Vincula o ID retornado ao contexto e carrega o histórico automaticamente
      if (dados && dados.id) {
        this.chamadoId = dados.id;
        this.carregarRespostas();
      }
    },
    error: (erro) => {
      this.chamadoEncontrado = null;
      this.respostas = [];
      this.mensagemErro = 'Chamado não encontrado para o protocolo informado.';
    }
  });
}

  getPrioridadeClass(prioridade: string): string {
    switch (prioridade) {
      case 'ALTA': return 'badge-prioridade-alta';
      case 'MEDIA': return 'badge-prioridade-media';
      case 'BAIXA': return 'badge-prioridade-baixa';
      default: return 'badge-secondary';
    }
  }



  getStatusClass(status: string): string {
    switch (status) {
      case 'ABERTO': return 'badge-status-aberto';
      case 'EM_ANDAMENTO': return 'badge-status-andamento';
      case 'FECHADO': return 'badge-status-fechado';
      default: return 'badge-secondary';
    }
  }
}
