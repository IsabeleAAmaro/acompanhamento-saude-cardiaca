import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { UsuarioService } from '../../services/usuario.service';
import {
  IonContent, IonHeader, IonTitle, IonToolbar, IonItem,
  IonInput, IonButton, IonButtons, IonBackButton
} from '@ionic/angular/standalone';

@Component({
  selector: 'app-cadastro',
  templateUrl: './cadastro.page.html',
  styleUrls: ['./cadastro.page.scss'],
  standalone: true,
  imports: [
    IonContent, IonHeader, IonTitle, IonToolbar, IonItem,
    IonInput, IonButton, IonButtons, IonBackButton,
    CommonModule, FormsModule
  ]
})
export class CadastroPage {
  usuario = {
    nome: '',
    sobrenome: '',
    email: '',
    telefone: '',
    senha: '',
    confirmarSenha: '',
    dataNascimento: '',
    sexo: '',
    paisResidencia: ''
  };

  constructor(
    private usuarioService: UsuarioService,
    private router: Router
  ) {}

  fazerCadastro() {
    this.usuarioService.registrar(this.usuario).subscribe({
      next: () => {
        alert('Conta criada com sucesso!');
        this.router.navigate(['/login']);
      },
      error: (err) => {
        console.error(err);
        alert('Erro ao criar conta. Verifique os dados.');
      }
    });
  }
}