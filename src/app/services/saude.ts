import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SaudeService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  registrarMedicao(body: any) {
    return this.http.post(`${this.apiUrl}/api/saude/registros`, body);
  }

  buscarRelatorio(usuarioId: number) {
    return this.http.get(`${this.apiUrl}/api/saude/relatorios/${usuarioId}`);
  }
}