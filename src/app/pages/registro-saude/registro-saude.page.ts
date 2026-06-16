import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import {
  IonContent, IonHeader, IonTitle, IonToolbar, IonItem,
  IonInput, IonButton, IonButtons, IonBackButton
} from '@ionic/angular/standalone';
import { SaudeService } from '../../services/saude.service';

@Component({
  selector: 'app-registro-saude',
  templateUrl: './registro-saude.page.html',
  styleUrls: ['./registro-saude.page.scss'],
  standalone: true,
  imports: [
    IonContent, IonHeader, IonTitle, IonToolbar, IonItem,
    IonInput, IonButton, IonButtons, IonBackButton,
    CommonModule, FormsModule
  ]
})
export class RegistroSaudePage {
  // O back-end exige o usuarioId. Para o escopo do trabalho, estamos usando o ID 1 (do usuário de teste gerado no DataLoader).
  registro = {
    usuarioId: 1,
    pressaoArterial: '',
    frequenciaCardiaca: null,
    oxigenacaoSangue: null,
    pesoCorporal: null,
    sintomas: ''
  };

  constructor(
    private saudeService: SaudeService,
    private router: Router
  ) {}

  salvarRegistro() {
    this.saudeService.registrarMedicao(this.registro).subscribe({
      next: () => {
        alert('Medição registrada com sucesso!');
        this.router.navigate(['/home']);
      },
      error: (err) => {
        console.error('Erro ao salvar medição:', err);
        alert('Erro ao registrar os dados.');
      }
    });
  }
}