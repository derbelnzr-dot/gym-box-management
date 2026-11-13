import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-contact-coachs',
  standalone: true,
  imports: [FormsModule, HeaderComponent, FooterComponent, CommonModule],
  templateUrl: './contact-coachs.component.html',
  styleUrl: './contact-coachs.component.css'
})
export class ContactCoachsComponent {
  coachName = '';
  coachEmail = '';
  contact = { name: '', message: '' };
  submitted = false;

  constructor(private route: ActivatedRoute) {
    this.route.queryParams.subscribe(params => {
      this.coachName = params['coach'] || '';
      this.coachEmail = params['email'] || '';
    });
  }

  onSubmit(): void {
    if (this.contact.name && this.contact.message) {
      this.submitted = true;
      setTimeout(() => {
        this.submitted = false;
        this.contact = { name: '', message: '' };
      }, 3000);
    }
  }
}
