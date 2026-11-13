import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';

@Component({
  selector: 'app-planning',
  standalone: true,
  imports: [CommonModule, HeaderComponent, FooterComponent],
  templateUrl: './planning.component.html',
  styleUrl: './planning.component.css'
})
export class PlanningComponent {
  planning = [
    { day: 'Lundi', courses: [
      { time: '8h - 9h', name: 'Fitness Débutant', coach: 'Anis', level: 'Débutant', room: 'Salle A' },
      { time: '17h - 18h', name: 'Musculation', coach: 'Ahmed', level: 'Intermédiaire', room: 'Salle B' }
    ]},
    { day: 'Mardi', courses: [
      { time: '7h - 8h', name: 'Cardio Intensif', coach: 'Karim', level: 'Avancé', room: 'Salle A' },
      { time: '18h - 19h', name: 'Yoga', coach: 'Sara', level: 'Tous niveaux', room: 'Yoga Studio' }
    ]},
    { day: 'Mercredi', courses: [
      { time: '8h - 9h', name: 'Cross Training', coach: 'Karim', level: 'Intermédiaire', room: 'Salle A' },
      { time: '17h - 18h', name: 'Boxe Fitness', coach: 'Anis', level: 'Intermédiaire', room: 'Ring' }
    ]},
    { day: 'Jeudi', courses: [
      { time: '7h - 8h', name: 'Yoga Flow', coach: 'Sara', level: 'Tous niveaux', room: 'Yoga Studio' },
      { time: '18h - 19h', name: 'Musculation Avancée', coach: 'Ahmed', level: 'Avancé', room: 'Salle B' }
    ]},
    { day: 'Vendredi', courses: [
      { time: '8h - 9h', name: 'Fitness Débutant', coach: 'Anis', level: 'Débutant', room: 'Salle A' },
      { time: '17h - 18h', name: 'HIIT Training', coach: 'Karim', level: 'Avancé', room: 'Salle A' }
    ]},
    { day: 'Samedi', courses: [
      { time: '9h - 10h', name: 'Cardio Dance', coach: 'Sara', level: 'Tous niveaux', room: 'Salle A' },
      { time: '10h - 11h', name: 'Body Pump', coach: 'Ahmed', level: 'Intermédiaire', room: 'Salle B' }
    ]},
    { day: 'Dimanche', courses: [
      { time: '9h - 10h', name: 'Yoga Relax', coach: 'Sara', level: 'Tous niveaux', room: 'Yoga Studio' }
    ]}
  ];
}
