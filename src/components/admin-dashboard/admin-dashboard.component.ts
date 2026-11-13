import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink, Router } from '@angular/router';
import { AuthService } from '../../app/services/auth.service';
import { ApiService } from '../../app/services/api.service';
import { DashboardStats } from '../../app/models/stats.model';
import { Member } from '../../app/models/member.model';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent implements OnInit {
  stats: DashboardStats | null = null;
  members: Member[] = [];
  filteredMembers: Member[] = [];
  searchTerm = '';
  loading = true;
  currentUser: any = null;

  constructor(
    private authService: AuthService,
    private apiService: ApiService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    this.loadData();
  }

  private loadData(): void {
    this.loading = true;
    this.apiService.getDashboardStats().subscribe(s => {
      this.stats = s;
      this.loading = false;
    });
    this.apiService.getMembers().subscribe(m => {
      this.members = m;
      this.filteredMembers = m;
    });
  }

  filterMembers(): void {
    const term = this.searchTerm.toLowerCase();
    this.filteredMembers = this.members.filter(m =>
      (m.firstName || m.username).toLowerCase().includes(term) ||
      (m.lastName || '').toLowerCase().includes(term) ||
      m.email.toLowerCase().includes(term)
    );
  }

  deleteMember(id: number): void {
    if (confirm('Confirmer la suppression de ce membre ?')) {
      this.apiService.deleteMember(id).subscribe(() => {
        this.members = this.members.filter(m => m.id !== id);
        this.filterMembers();
      });
    }
  }

  logout(): void {
    this.authService.logout();
  }

  getStatIcon(index: number): string {
    const icons = ['group', 'fitness_center', 'calendar_month', 'payments', 'trending_up'];
    return icons[index] || 'analytics';
  }

  getStatColor(index: number): string {
    const colors = ['#dc2626', '#2563eb', '#16a34a', '#f59e0b', '#8b5cf6'];
    return colors[index] || '#dc2626';
  }
}
