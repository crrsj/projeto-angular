import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RespostaDTO, RespostaRequisicaoDTO } from '../models/resposta.model';



@Injectable({
  providedIn: 'root'
})
export class RespostaService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/respostas';

  adicionarResposta(chamadoId: number, dados: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/${chamadoId}/respostas`, dados, { responseType: 'text' as 'json' });
  }

  listarRespostas(chamadoId: number): Observable<RespostaRequisicaoDTO[]> {
    return this.http.get<RespostaRequisicaoDTO[]>(`${this.apiUrl}/${chamadoId}/respostas`);
  }
}


