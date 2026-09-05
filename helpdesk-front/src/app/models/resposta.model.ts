export interface RespostaDTO {
  descricao: string;
}

export interface RespostaRequisicaoDTO {
  id: number;
  descricao: string;
  dataCriacao: string;
  chamadoId?: number;
}
