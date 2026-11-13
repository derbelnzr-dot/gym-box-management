import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';

@Component({
  selector: 'app-programmes',
  standalone: true,
  imports: [CommonModule, HeaderComponent, FooterComponent],
  templateUrl: './programmes.component.html',
  styleUrl: './programmes.component.css'
})
export class ProgrammesComponent {
  programmes = [
    {
      title: 'Fitness Débutant',
      duration: '4 semaines',
      price: '50 DT',
      level: 'Débutant',
      description: 'Programme idéal pour commencer le fitness en douceur avec un accompagnement personnalisé.',
      icon: 'directions_run',
      features: ['Séances guidées', 'Suivi personnalisé', 'Planning flexible', 'Coach dédié']
    },
    {
      title: 'Musculation',
      duration: '8 semaines',
      price: '120 DT',
      level: 'Intermédiaire',
      description: 'Programme intensif de musculation pour développer votre masse musculaire et votre force.',
      icon: 'fitness_center',
      features: ['Matériel pro', 'Programme sur mesure', 'Nutrition incluse', 'Suivi mensuel']
    },
    {
      title: 'Cardio Intensif',
      duration: '6 semaines',
      price: '80 DT',
      level: 'Avancé',
      description: 'Brûlez les graisses et améliorez votre endurance avec notre programme cardio intense.',
      icon: 'directions_bike',
      features: ['HIIT training', 'Circuit training', 'Cardio fractionné', 'Body Combat']
    },
    {
      title: 'Yoga & Stretching',
      duration: '4 semaines',
      price: '60 DT',
      level: 'Tous niveaux',
      description: 'Retrouvez souplesse et sérénité avec nos séances de yoga adaptées à tous.',
      icon: 'self_improvement',
      features: ['Hatha Yoga', 'Vinyasa Flow', 'Méditation', 'Respirations']
    },
    {
      title: 'Cross Training',
      duration: '8 semaines',
      price: '150 DT',
      level: 'Avancé',
      description: 'Programme complet mixant force, cardio et agilité pour des résultats maximaux.',
      icon: 'bolt',
      features: ['WOD quotidiens', 'Functional training', 'Sprints & lifts', 'Challenges']
    },
    {
      title: 'Boxe Fitness',
      duration: '6 semaines',
      price: '100 DT',
      level: 'Intermédiaire',
      description: 'Découvrez la boxe comme sport fitness pour un défoulement complet et efficace.',
      icon: 'sports_kabaddi',
      features: ['Shadow boxing', 'Punching bag', 'Cardio combat', 'Self-défense']
    }
  ];
}
