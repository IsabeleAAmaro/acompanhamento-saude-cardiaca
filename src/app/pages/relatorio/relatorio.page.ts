import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { 
  IonContent, IonHeader, IonTitle, IonToolbar, IonCard, 
  IonCardHeader, IonCardTitle, IonCardContent, IonButtons, IonBackButton, IonList, IonItem, IonLabel, IonBadge
} from '@ionic/angular/standalone';
import { SaudeService } from '../../services/saude.service';

@Component({
  selector: 'app-relatorio',
  templateUrl: './relatorio.page.html',
  styleUrls: ['./relatorio.page.scss'],
  standalone: true,
  imports: [
    IonContent, IonHeader, IonTitle, IonToolbar, IonCard, 
    IonCardHeader, IonCardTitle, IonCardContent, IonButtons, IonBackButton, IonList, IonItem, IonLabel, IonBadge,
    CommonModule
  ]
})
export class RelatorioPage {
  relatorio: any = null;

  constructor(private saudeService: SaudeService) {}

  // Este método roda automaticamente sempre que a tela vai ser exibida
  ionViewWillEnter() {
    this.carregarRelatorio();
  }

  carregarRelatorio() {
    // Buscando relatório do usuário ID 1
    this.saudeService.buscarRelatorio(1).subscribe({
      next: (dados) => {
        this.relatorio = dados;
      },
      error: (err) => {
        console.error('Erro ao buscar relatório:', err);
        alert('Não foi possível carregar o relatório. Verifique se o backend está rodando.');
      }
    });
  }
}