import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ChamadoService } from '../../services/chamado.service';
import { ChamadoDTO, AtualizarChamadoDTO } from '../../models/chamado.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-chamado-lista',
  standalone: true,
  imports: [CommonModule,FormsModule,RouterLink],
  templateUrl: './chamado-lista.component.html',
  styleUrl: './chamado-lista.css'

})

export class ChamadoListaComponent implements OnInit {

  chamados: ChamadoDTO[] = [];
  chamadoSelecionado: ChamadoDTO | null = null;
  isModalOpen: boolean = false;

  constructor(private chamadoService: ChamadoService, private router: Router) {
    console.log('ChamadoListaComponent foi instanciado com sucesso!');
  }

  ngOnInit(): void {
    console.log('-> SUCESSO: Entrou no ChamadoListaComponent (ngOnInit)');
    this.carregarChamados();
  }

  carregarChamados() {
    this.chamadoService.listarTodos().subscribe({
      next: (resposta) => {
        this.chamados = resposta.content; // O Spring Boot retorna um objeto Page contendo a lista em 'content'
      },
      error: (erro) => {
        console.error('Erro ao carregar chamados:', erro);
      }
    });
  }

  abrirDetalhes(chamado: ChamadoDTO) {
  this.chamadoSelecionado = chamado;
  this.isModalOpen = true;
}

  fecharModal() {
    this.isModalOpen = false;
    this.chamadoSelecionado = null;
  }

  getStatusBadgeClass(status: string): string {
    switch (status) {
      case 'ABERTO':
        return 'bg-blue-100 text-blue-800';
      case 'EM_ANDAMENTO':
        return 'bg-amber-100 text-amber-800';
      case 'FECHADO':
        return 'bg-green-100 text-green-800';
      default:
        return 'bg-gray-100 text-gray-800';
    }
  }

  getPrioridadeBadgeClass(prioridade: string): string {
    switch (prioridade) {
      case 'ALTA':
        return 'bg-red-100 text-red-800';
      case 'MEDIA':
        return 'bg-yellow-100 text-yellow-800';
      case 'BAIXA':
        return 'bg-emerald-100 text-emerald-800';
      default:
        return 'bg-gray-100 text-gray-800';
    }
  }

  isEditModalOpen: boolean = false;
  chamadoEmEdicao: AtualizarChamadoDTO = {
    id: 0,
    dataFinal: '',
    assunto: '',
    setor: '',
    descricao: ''
  };

  abrirEdicao(chamado: ChamadoDTO) {
    this.chamadoEmEdicao = {
      id: chamado.id!,
      dataFinal: chamado.dataFinal || '',
      assunto: chamado.assunto || '',
      setor: chamado.setor || '',
      descricao: chamado.descricao || ''
    };
    this.isEditModalOpen = true;
  }

  fecharModalEdicao() {
    this.isEditModalOpen = false;
  }

  salvarEdicao() {
    this.chamadoService.atualizar(this.chamadoEmEdicao.id, this.chamadoEmEdicao).subscribe({
      next: () => {
        this.isEditModalOpen = false;
        this.carregarChamados(); // Atualiza a tabela com os novos dados
      },
      error: (erro: any) => {
        console.error('Erro ao atualizar chamado:', erro);
      }
    });
  }


  excluir(id: number) {
  if (confirm('Tem certeza que deseja excluir este chamado?')) {
    this.chamadoService.deletar(id).subscribe({
      next: () => {
        // Remove o item da lista atual sem precisar recarregar tudo do servidor
        this.chamados = this.chamados.filter(c => c.id !== id);
      },
      error: (erro) => {
        console.error('Erro ao excluir chamado:', erro);
      }
    });
  }
}


protocoloBusca: string = '';


pesquisarPorProtocolo(): void {
  if (!this.protocoloBusca.trim()) {
    // Se o campo estiver vazio, recarrega todos os chamados da tabela
    this.carregarChamados();
    return;
  }

  this.chamadoService.buscarPorProtocolo(this.protocoloBusca).subscribe({
    next: (chamado) => {
      // Como a busca por protocolo geralmente retorna um único objeto,
      // colocamos ele dentro de um array para exibir na tabela
      this.chamados = chamado ? [chamado] : [];
    },
    error: (err) => {
      console.error('Chamado não encontrado', err);
      this.chamados = [];
      alert('Nenhum chamado encontrado para este protocolo.');
    }
  });
}

}
