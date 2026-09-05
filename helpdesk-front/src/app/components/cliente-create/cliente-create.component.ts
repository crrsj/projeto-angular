import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { ClienteService } from '../../services/cliente.service';
import { Cliente } from '../../models/cliente.model';

@Component({
  selector: 'app-cliente-create',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './cliente-create.component.html'
})
export class ClienteCreateComponent {
  private fb: FormBuilder = inject(FormBuilder);
  private clienteService: ClienteService = inject(ClienteService);
  private router: Router = inject(Router);
  private http: HttpClient = inject(HttpClient);

  mensagemErro: string | null = null;

  form: FormGroup = this.fb.group({
    nome: ['', [Validators.required, Validators.minLength(3)]],
    email: ['', [Validators.required, Validators.email]],
    cpfOuCnpj: ['', [Validators.required]],
    telefone: ['', [Validators.required]],
    endereco: this.fb.group({
      cep: ['', [Validators.required]],
      logradouro: ['', [Validators.required]],
      numero: ['', [Validators.required]],
      complemento: [''],
      bairro: ['', [Validators.required]],
      cidade: ['', [Validators.required]],
      estado: ['', [Validators.required]],
      uf: ['', [Validators.required, Validators.maxLength(2)]]
    })
  });

  buscarCep(): void {
    const cep = this.form.get('endereco.cep')?.value?.replace(/\D/g, '');

    if (cep && cep.length === 8) {
      this.http.get<any>(`https://viacep.com.br/ws/${cep}/json/`).subscribe({
        next: (dados) => {
          if (!dados.erro) {
            this.form.get('endereco')?.patchValue({
              logradouro: dados.logradouro,
              bairro: dados.bairro,
              cidade: dados.localidade,
              estado: dados.estado || dados.uf,
              uf: dados.uf
            });
            this.mensagemErro = null;
          } else {
            this.mensagemErro = 'CEP não encontrado.';
          }
        },
        error: () => {
          this.mensagemErro = 'Erro ao buscar o CEP.';
        }
      });
    }
  }

  salvar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const payload = this.form.value as Cliente;

    this.clienteService.cadastrar(payload).subscribe({
      next: () => {
        this.router.navigate(['/dashboard']);
      },
      error: (err: any) => {
        this.mensagemErro = err?.error?.message || 'Erro ao cadastrar o cliente. Verifique os dados.';
      }
    });
  }
}
