import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink, Router } from '@angular/router';
import { AuthService } from '../../app/services/auth.service';
import { HeaderComponent } from '../header/header.component';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, HeaderComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  user = { email: '', password: '' };
  loading = false;
  showPassword = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  onSubmit(): void {
    if (!this.user.email || !this.user.password) return;

    this.loading = true;
    this.authService.login(this.user).subscribe({
      next: (res) => {
        this.loading = false;
        if (res.role === 'ADMIN') {
          this.router.navigate(['/admin-dashboard']);
        } else {
          this.router.navigate(['/member-home']);
        }
      },
      error: () => {
        this.loading = false;
      }
    });
  }
}
