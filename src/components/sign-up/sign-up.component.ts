import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink, Router } from '@angular/router';
import { HeaderComponent } from '../header/header.component';
import { AuthService } from '../../app/services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-sign-up',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, HeaderComponent],
  templateUrl: './sign-up.component.html',
  styleUrl: './sign-up.component.css'
})
export class SignUpComponent {
  user = { username: '', email: '', password: '' };
  loading = false;
  showPassword = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  onSubmit(): void {
    if (!this.user.username || !this.user.email || !this.user.password) return;

    this.loading = true;
    this.authService.register(this.user).subscribe({
      next: () => {
        this.loading = false;
        this.router.navigate(['/member-home']);
      },
      error: () => {
        this.loading = false;
      }
    });
  }
}
