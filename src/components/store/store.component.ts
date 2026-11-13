import { Component, HostListener, OnInit } from '@angular/core';
import { FooterComponent } from '../footer/footer.component';
import { HeaderComponent } from '../header/header.component';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { CartService } from '../../app/services/cart.service';
import { ApiService } from '../../app/services/api.service';
import { Product } from '../../app/models/product.model';

type SortKey = 'featured' | 'price-asc' | 'price-desc' | 'name';

@Component({
  selector: 'app-store',
  standalone: true,
  imports: [FooterComponent, HeaderComponent, CommonModule, FormsModule, RouterLink],
  templateUrl: './store.component.html',
  styleUrl: './store.component.css'
})
export class StoreComponent implements OnInit {
  products: Product[] = [];
  loading = true;
  error = false;

  searchText = '';
  selectedCategories: string[] = [];
  inStockOnly = false;
  sortBy: SortKey = 'featured';

  currentPage = 0;
  pageSize = 9;
  cartCount = 0;

  selectedProduct: Product | null = null;
  detailQty = 1;

  constructor(
    private apiService: ApiService,
    private cartService: CartService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {
    this.cartService.cart$.pipe(takeUntilDestroyed()).subscribe(items => {
      this.cartCount = items.reduce((sum, item) => sum + item.qty, 0);
    });
  }

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.loading = true;
    this.error = false;

    this.apiService.getProducts().subscribe({
      next: (data) => {
        this.products = data;
        this.loading = false;
      },
      error: () => {
        this.products = [];
        this.loading = false;
        this.error = true;
      }
    });
  }

  get categories(): string[] {
    return Array.from(new Set(this.products.map(product => product.category).filter(Boolean)));
  }

  get filteredProducts(): Product[] {
    const query = this.searchText.trim().toLowerCase();

    return this.products.filter(product => {
      const matchSearch = query === ''
        || product.name.toLowerCase().includes(query)
        || (product.description ?? '').toLowerCase().includes(query)
        || product.category.toLowerCase().includes(query);
      const matchCategory = this.selectedCategories.length === 0
        || this.selectedCategories.includes(product.category);
      const matchStock = !this.inStockOnly || this.inStock(product);
      return matchSearch && matchCategory && matchStock;
    });
  }

  get sortedProducts(): Product[] {
    const list = [...this.filteredProducts];

    switch (this.sortBy) {
      case 'price-asc':
        return list.sort((a, b) => a.price - b.price);
      case 'price-desc':
        return list.sort((a, b) => b.price - a.price);
      case 'name':
        return list.sort((a, b) => a.name.localeCompare(b.name));
      default:
        return list;
    }
  }

  get paginatedProducts(): Product[] {
    const start = this.currentPage * this.pageSize;
    return this.sortedProducts.slice(start, start + this.pageSize);
  }

  get totalResults(): number {
    return this.sortedProducts.length;
  }

  get totalPages(): number {
    return Math.max(1, Math.ceil(this.totalResults / this.pageSize));
  }

  get pageNumbers(): number[] {
    return Array.from({ length: this.totalPages }, (_, index) => index);
  }

  get hasActiveFilters(): boolean {
    return this.searchText.trim() !== ''
      || this.selectedCategories.length > 0
      || this.inStockOnly
      || this.sortBy !== 'featured';
  }

  countForCategory(category: string): number {
    return this.products.filter(product => product.category === category).length;
  }

  inStock(product: Product): boolean {
    return (product.stock ?? 0) > 0;
  }

  stockLabel(product: Product): string {
    const stock = product.stock ?? 0;
    if (stock <= 0) return 'Rupture de stock';
    if (stock <= 5) return `Plus que ${stock} en stock`;
    return 'En stock';
  }

  toggleCategory(category: string): void {
    if (this.selectedCategories.includes(category)) {
      this.selectedCategories = this.selectedCategories.filter(item => item !== category);
    } else {
      this.selectedCategories = [...this.selectedCategories, category];
    }
    this.resetPage();
  }

  clearFilters(): void {
    this.searchText = '';
    this.selectedCategories = [];
    this.inStockOnly = false;
    this.sortBy = 'featured';
    this.resetPage();
  }

  onFilterChange(): void {
    this.resetPage();
  }

  goToPage(page: number): void {
    if (page < 0 || page >= this.totalPages) return;
    this.currentPage = page;
  }

  nextPage(): void {
    this.goToPage(this.currentPage + 1);
  }

  previousPage(): void {
    this.goToPage(this.currentPage - 1);
  }

  addToCart(product: Product, qty = 1): void {
    if (!this.inStock(product)) return;

    this.cartService.addToCart({
      id: product.id,
      name: product.name,
      price: product.price,
      category: product.category,
      image: product.image
    }, qty);

    this.snackBar
      .open(`${qty} × ${product.name} ajouté au panier`, 'Voir le panier', {
        duration: 3000,
        panelClass: ['success-snackbar']
      })
      .onAction()
      .subscribe(() => this.router.navigate(['/cart']));
  }

  openDetails(product: Product): void {
    this.selectedProduct = product;
    this.detailQty = 1;
  }

  closeDetails(): void {
    this.selectedProduct = null;
    this.detailQty = 1;
  }

  changeDetailQty(step: number): void {
    if (!this.selectedProduct) return;
    const max = this.selectedProduct.stock ?? 99;
    const next = this.detailQty + step;
    if (next >= 1 && next <= Math.max(1, max)) {
      this.detailQty = next;
    }
  }

  quantityInCart(product: Product): number {
    return this.cartService.quantityOf(product.id);
  }

  onImageError(event: Event): void {
    const img = event.target as HTMLImageElement;
    if (img && !img.src.endsWith('/assets/images/placeholder.svg')) {
      img.src = '/assets/images/placeholder.svg';
    }
  }

  @HostListener('document:keydown.escape')
  onEscape(): void {
    if (this.selectedProduct) {
      this.closeDetails();
    }
  }

  private resetPage(): void {
    this.currentPage = 0;
  }
}
