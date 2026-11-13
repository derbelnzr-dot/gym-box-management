import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-imc',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './imc.component.html',
  styleUrl: './imc.component.css'
})
export class ImcComponent {
  poids!: number;
  taille!: number;
  age!: number;
  sexe = 'homme';
  imc?: number;
  resultat = '';
  category = '';

  calculerIMC(): void {
    if (!this.poids || !this.taille) return;

    const tailleM = this.taille / 100;
    this.imc = this.poids / (tailleM * tailleM);

    if (this.imc < 16) {
      this.resultat = 'Maigreur sévère';
      this.category = 'danger';
    } else if (this.imc < 18.5) {
      this.resultat = 'Insuffisance pondérale';
      this.category = 'warning';
    } else if (this.imc < 25) {
      this.resultat = 'Poids normal';
      this.category = 'success';
    } else if (this.imc < 30) {
      this.resultat = 'Surpoids';
      this.category = 'warning';
    } else if (this.imc < 35) {
      this.resultat = 'Obésité modérée';
      this.category = 'danger';
    } else {
      this.resultat = 'Obésité sévère';
      this.category = 'danger';
    }
  }

  getColorClass(): string {
    return {
      success: 'text-green-400',
      warning: 'text-yellow-400',
      danger: 'text-red-400'
    }[this.category] || 'text-gray-400';
  }
}
