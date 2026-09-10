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
  mensagemSucesso = '';
  mensagemErro = '';
  mensagensSucesso: string = '';
  mensagensErro: string = '';
  loading: boolean = false;
protocoloBusca: string = '';
chamadoEncontrado: any = null;


  formResposta: FormGroup = this.fb.group({
    descricao: ['', [Validators.required, Validators.minLength(5)]]
  });


carregarRespostas(id: number): void {
  this.respostaService.listarRespostas(id).subscribe({
    next: (dados) => {
      this.respostas = Array.isArray(dados) ? dados : [];
    },
    error: (err) => console.error(err)
  });
}




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
        this.carregarRespostas(this.chamadoId);
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


ngOnInit(): void {
  const idParam = this.route.snapshot.queryParamMap.get('chamadoId') || this.route.snapshot.paramMap.get('id');
  if (idParam) {
    this.chamadoId = Number(idParam);
    this.carregarRespostas(this.chamadoId);
  }
}
  enviarResposta(): void {
    if (this.formResposta.invalid) {
      this.formResposta.markAllAsTouched();
      return;
    }

    this.loading = true;
    this.respostaService.adicionarResposta(this.chamadoId, this.formResposta.value).subscribe({
      next: () => {
        this.mensagensSucesso = 'Resposta adicionada com sucesso!';
        this.mensagensErro = '';
        this.formResposta.reset();
        this.loading = false;
        this.carregarRespostas(this.chamadoId);
      },
      error: (err) => {
        if (err.status === 201 || err.status === 200) {
          this.mensagensSucesso = 'Resposta adicionada com sucesso!';
          this.mensagensErro = '';
          this.formResposta.reset();
          this.loading = false;
          this.carregarRespostas(this.chamadoId);
        } else {
          this.mensagensErro = 'Falha ao registrar resposta. Tente novamente.';
          this.loading = false;
        }
      }
    });
  }

}
