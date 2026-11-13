import { Component } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FooterComponent } from '../footer/footer.component';

@Component({
  selector: 'app-accueil',
  standalone: true,
  imports: [HeaderComponent, CommonModule, FooterComponent, RouterLink],
  templateUrl: './accueil.component.html',
  styleUrl: './accueil.component.css'
})
export class AccueilComponent {
  heroBg = 'https://images.unsplash.com/photo-1534438327276-14e5300c3a48?ixlib=rb-4.0.3&auto=format&fit=crop&w=1920&q=80';
  features = [
    { icon: 'fitness_center', title: 'Équipements Modernes', desc: 'Machines dernière génération pour des entraînements optimaux' },
    { icon: 'badge', title: 'Coachs Certifiés', desc: 'Accompagnement personnalisé par des professionnels diplômés' },
    { icon: 'schedule', title: 'Horaires Flexibles', desc: 'Ouvert 7j/7 de 6h à 23h pour s\'adapter à votre rythme' },
    { icon: 'group', title: 'Ambiance Motivante', desc: 'Une communauté dynamique pour vous dépasser chaque jour' }
  ];

  programmes = [
    { title: 'Fitness Débutant', duration: '4 semaines', price: '50 DT', level: 'Débutant', tag: 'Populaire' },
    { title: 'Musculation', duration: '8 semaines', price: '120 DT', level: 'Intermédiaire', tag: 'Pro' },
    { title: 'Cardio Intensif', duration: '6 semaines', price: '80 DT', level: 'Avancé', tag: 'Intense' },
    { title: 'Yoga & Stretching', duration: '4 semaines', price: '60 DT', level: 'Tous niveaux', tag: 'Relax' }
  ];

  testimonials = [
    { name: 'Ahmed Ben Ali', text: 'J\'ai transformé mon corps en 3 mois grâce aux coachs exceptionnels de GymBox !', rating: 5, image: '' },
    { name: 'Sarra Mansour', text: 'L\'ambiance est incroyable, les équipements sont top. Je recommande à 100% !', rating: 5, image: '' },
    { name: 'Mehdi Kacem', text: 'Meilleure salle de la région. Les programmes sont adaptés à tous les niveaux.', rating: 5, image: '' }
  ];

  stats = [
    { value: '500+', label: 'Membres actifs' },
    { value: '15+', label: 'Coachs pro' },
    { value: '50+', label: 'Programmes' },
    { value: '4.9', label: 'Note moyenne' }
  ];
}
