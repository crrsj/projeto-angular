import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cliente } from '../models/cliente.model';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  private http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/api/clientes';

  listar(): Observable<any> {
    return this.http.get<any>(this.apiUrl);
  }

  cadastrar(cliente: Cliente): Observable<Cliente> {
    return this.http.post<Cliente>(this.apiUrl, cliente);
  }

  deletar(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }

  buscarPorId(id: number): Observable<any> {
  return this.http.get<any>(`${this.apiUrl}/${id}`);
}

atualizar(id: number, cliente: any): Observable<any> {
  return this.http.patch<any>(`http://localhost:8080/api/clientes/${id}`, cliente);
}

}
