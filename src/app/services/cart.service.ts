import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export interface CartItem {
  id?: number;
  name: string;
  price: number;
  category: string;
  image: string;
  qty: number;
}

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cart: CartItem[] = [];
  private cartSubject = new BehaviorSubject<CartItem[]>([]);
  cart$ = this.cartSubject.asObservable();

  constructor() {
    // Vérifier si on est dans le navigateur
    if (typeof window !== 'undefined' && window.localStorage) {
      const storedCart = localStorage.getItem('cart');
      if (storedCart) {
        this.cart = JSON.parse(storedCart);
        this.cartSubject.next(this.cart);
      }
    }
  }

  addToCart(item: Omit<CartItem, 'qty'>, qty = 1) {
    const existing = this.cart.find(ci =>
      ci.id != null && item.id != null ? ci.id === item.id : ci.name === item.name
    );
    if (existing) {
      existing.qty += qty;
    } else {
      this.cart.push({ ...item, qty });
    }
    this.updateCart();
  }

  quantityOf(id?: number): number {
    if (id == null) return 0;
    return this.cart
      .filter(ci => ci.id === id)
      .reduce((sum, ci) => sum + ci.qty, 0);
  }

  removeItem(index: number) {
    this.cart.splice(index, 1);
    this.updateCart();
  }

  clearCart() {
    this.cart = [];
    this.updateCart();
  }

  getCart(): CartItem[] {
    return this.cart;
  }

  getTotal(): number {
    return this.cart.reduce((sum, item) => sum + item.price * item.qty, 0);
  }

  getCartCount(): number {
    return this.cart.reduce((sum, item) => sum + item.qty, 0);
  }

  private updateCart() {
    if (typeof window !== 'undefined' && window.localStorage) {
      localStorage.setItem('cart', JSON.stringify(this.cart));
    }
    this.cartSubject.next(this.cart);
  }
}