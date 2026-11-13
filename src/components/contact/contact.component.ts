import { Component } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { FormsModule } from '@angular/forms';
import { FooterComponent } from '../footer/footer.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-contact',
  standalone: true,
  imports: [HeaderComponent, FormsModule, FooterComponent, CommonModule],
  templateUrl: './contact.component.html',
  styleUrl: './contact.component.css'
})
export class ContactComponent {
  contact = { name: '', email: '', message: '' };
  submitted = false;

  onSubmit(): void {
    if (this.contact.name && this.contact.email && this.contact.message) {
      this.submitted = true;
      setTimeout(() => {
        this.submitted = false;
        this.contact = { name: '', email: '', message: '' };
      }, 3000);
    }
  }
}
