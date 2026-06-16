import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private apiUrl = environment.apiUrl;
  constructor(private http: HttpClient) {}

  registrar(body: any) {
    return this.http.post(`${this.apiUrl}/api/usuarios/registrar`, body);
  }
}