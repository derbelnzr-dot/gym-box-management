import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { FooterComponent } from '../footer/footer.component';
import { ApiService } from '../../app/services/api.service';
import { Coach } from '../../app/models/coach.model';

@Component({
  selector: 'app-coach',
  standalone: true,
  imports: [HeaderComponent, CommonModule, RouterModule, FormsModule, FooterComponent],
  templateUrl: './coach.component.html',
  styleUrl: './coach.component.css'
})
export class CoachComponent implements OnInit {
  coaches: Coach[] = [];
  loading = true;
  error = false;
  searchText = '';
  selectedSpecialty = '';

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.loadCoaches();
  }

  loadCoaches(): void {
    this.loading = true;
    this.error = false;

    this.apiService.getCoaches().subscribe({
      next: (data) => {
        this.coaches = data;
        this.loading = false;
      },
      error: () => {
        this.coaches = [];
        this.loading = false;
        this.error = true;
      }
    });
  }

  get specialties(): string[] {
    return Array.from(
      new Set(
        this.coaches
          .map(coach => coach.specialty)
          .filter((specialty): specialty is string => !!specialty)
      )
    );
  }

  get filteredCoaches(): Coach[] {
    const query = this.searchText.trim().toLowerCase();

    return this.coaches.filter(coach => {
      const matchSearch = query === ''
        || coach.username.toLowerCase().includes(query)
        || (coach.specialty ?? '').toLowerCase().includes(query)
        || (coach.bio ?? '').toLowerCase().includes(query);
      const matchSpecialty = this.selectedSpecialty === '' || coach.specialty === this.selectedSpecialty;
      return matchSearch && matchSpecialty;
    });
  }

  get availableCount(): number {
    return this.coaches.filter(coach => coach.isAvailable).length;
  }

  get totalExperience(): number {
    return this.coaches.reduce((sum, coach) => sum + (coach.yearsExperience ?? 0), 0);
  }

  selectSpecialty(specialty: string): void {
    this.selectedSpecialty = this.selectedSpecialty === specialty ? '' : specialty;
  }

  onImageError(event: Event): void {
    const img = event.target as HTMLImageElement;
    if (img && !img.src.endsWith('/assets/images/placeholder.svg')) {
      img.src = '/assets/images/placeholder.svg';
    }
  }

  initials(name: string): string {
    return name
      .split(' ')
      .filter(part => part.length > 0)
      .slice(0, 2)
      .map(part => part.charAt(0).toUpperCase())
      .join('');
  }
}
