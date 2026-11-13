import { Component, OnInit } from '@angular/core';
import { FooterComponent } from '../components/footer/footer.component';
import { HeaderComponent } from '../components/header/header.component';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { CartService, CartItem } from '../app/services/cart.service';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [FooterComponent, HeaderComponent, CommonModule, RouterLink],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent implements OnInit {
  cartItems: CartItem[] = [];
  total = 0;

  constructor(private cartService: CartService) {}

  ngOnInit(): void {
    this.cartService.cart$.subscribe(items => {
      this.cartItems = items;
      this.calculateTotal();
    });
  }

  increaseQty(i: number): void {
    this.cartItems[i].qty++;
    this.calculateTotal();
  }

  decreaseQty(i: number): void {
    if (this.cartItems[i].qty > 1) {
      this.cartItems[i].qty--;
      this.calculateTotal();
    }
  }

  removeItem(i: number): void {
    this.cartService.removeItem(i);
  }

  calculateTotal(): void {
    this.total = this.cartItems.reduce((sum, item) => sum + item.price * item.qty, 0);
  }
}
