import { Cliente } from './../models/cliente.model';
import { Injectable, inject } from '@angular/core';
import { HttpClient,HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ChamadoDTO } from '../models/chamado.model';
import { AtualizarChamadoDTO } from '../models/chamado.model'; // Ajuste o caminho relativo se necessário

@Injectable({
  providedIn: 'root'
})
export class ChamadoService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/chamados';
  private clientesUrl = 'http://localhost:8080/api/clientes';

  // Busca todos os clientes para preencher o <select>
  listarClientes(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(this.clientesUrl);
  }

  // Envia o clienteId no caminho da URL e os dados do chamado no JSON
  abrirChamado(clienteId: number, chamadoDTO: ChamadoDTO): Observable<ChamadoDTO> {
    return this.http.post<ChamadoDTO>(`${this.apiUrl}/${clienteId}`, chamadoDTO);
  }

 buscarPorProtocolo(protocolo: string): Observable<any> {
  const params = new HttpParams().set('protocolo', protocolo);
  return this.http.get<any>(`${this.apiUrl}/protocolo`, { params });
}

  listarTodos(): Observable<any> {
    return this.http.get<any>(this.apiUrl);
  }


 atualizar(id: number, chamado: AtualizarChamadoDTO): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, chamado);
  }

  deletar(id: number): Observable<any> {
  return this.http.delete<any>(`${this.apiUrl}/${id}`);
}

}
