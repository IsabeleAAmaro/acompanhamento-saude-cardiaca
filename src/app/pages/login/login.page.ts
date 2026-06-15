import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import {
  IonContent,
  IonHeader,
  IonTitle,
  IonToolbar,
  IonItem,
  IonLabel,
  IonInput,
  IonButton,
  IonCard,
  IonCardHeader,
  IonCardTitle,
  IonCardContent,
  IonText
} from '@ionic/angular/standalone';
import { AuthService } from '../../services/auth';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    IonContent,
    IonHeader,
    IonTitle,
    IonToolbar,
    IonItem,
    IonLabel,
    IonInput,
    IonButton,
    IonCard,
    IonCardHeader,
    IonCardTitle,
    IonCardContent,
    IonText
  ]
})
export class LoginPage {
  email = '';
  senha = '';
  mensagem = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  fazerLogin() {
    const body = {
      email: this.email,
      senha: this.senha
    };

    this.authService.login(body).subscribe({
      next: (res) => {
        console.log('Login realizado com sucesso', res);
        this.mensagem = 'Login realizado com sucesso!';
        this.router.navigate(['/home']);
      },
      error: (err) => {
        console.error('Erro no login', err);
        this.mensagem = 'E-mail ou senha inválidos.';
      }
    });
  }

  irParaCadastro() {
    this.router.navigate(['/cadastro']);
  }
}