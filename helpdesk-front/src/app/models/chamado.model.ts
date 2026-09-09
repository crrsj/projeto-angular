export interface ChamadoDTO {
  id?: number;
  protocolo: string;
  assunto?: string; // Adicione esta linha
  descricao: string;
  prioridade: string;
  setor: string;
  dataInicial?: string;
  dataFinal?: string;
  status: string;
  cliente?: {
    id: number;
    nome: string;
    cpf: string;
    telefone: string;
    email: string;
    endereco?: any;
  };
}

export interface AtualizarChamadoDTO {
  id: number;
  dataFinal?: string;
  assunto?: string;
  setor: string;
  descricao: string;
}
