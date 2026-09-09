import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ClienteService } from '../../services/cliente.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-cliente-lista',
  standalone: true,
  imports: [CommonModule, RouterLink,FormsModule],
  templateUrl: './cliente-lista.html',
  styleUrl: './cliente-lista.css'
})
export class ClienteListaComponent implements OnInit {
  clientes: any[] = [];
  clienteSelecionado: any = null;
  modalAberto: boolean = false;
  clienteEdicao: any = {};
  modalEdicaoAberto: boolean = false;

  constructor(private clienteService: ClienteService) {}

  ngOnInit(): void {
    this.carregarClientes();
  }

  carregarClientes() {
    this.clienteService.listar().subscribe({
      next: (resposta) => {
        this.clientes = resposta.content || resposta;
      },
      error: (erro) => {
        console.error('Erro ao carregar clientes:', erro);
      }
    });
  }

  abrirDetalhes(id: number) {
    this.clienteService.buscarPorId(id).subscribe({
      next: (dados) => {
        this.clienteSelecionado = dados;
        this.modalAberto = true;
      },
      error: (erro) => {
        console.error('Erro ao carregar detalhes do cliente:', erro);
      }
    });
  }

  fecharModal() {
    this.modalAberto = false;
    this.clienteSelecionado = null;
  }

  excluir(id: number) {
    if (confirm('Tem certeza que deseja excluir este cliente?')) {
      this.clienteService.deletar(id).subscribe({
        next: () => {
          this.clientes = this.clientes.filter(c => c.id !== id);
        },
        error: (erro) => {
          console.error('Erro ao excluir cliente:', erro);
        }
      });
    }
  }


  abrirEdicao(id: number) {
    this.clienteService.buscarPorId(id).subscribe({
      next: (dados) => {
        this.clienteEdicao = { ...dados }; // Clona o objeto para edição
        this.modalEdicaoAberto = true;
      },
      error: (erro) => {
        console.error('Erro ao carregar cliente para edição:', erro);
      }
    });
  }

fecharModalEdicao() {
    this.modalEdicaoAberto = false;
    this.clienteEdicao = {};
  }

  salvarEdicao() {
    this.clienteService.atualizar(this.clienteEdicao.id, this.clienteEdicao).subscribe({
      next: () => {
        this.fecharModalEdicao();
        this.carregarClientes();
      },
      error: (erro) => {
        console.error('Erro ao atualizar cliente:', erro);
      }
    });
  }


}
