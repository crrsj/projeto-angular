export interface Endereco {
  cep: string;
  logradouro?: string;
  numero: string;
  complemento?: string;
  bairro?: string;
  cidade?: string;
  estado?: string;
  uf?: string;
}

export interface Cliente {
  id?: number;
  nome: string;
  email: string;
  cpfOuCnpj: string;
  telefone: string;
  endereco: Endereco;
}
