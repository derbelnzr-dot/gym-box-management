import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class StorageService {

  private isBrowser(): boolean {
    return typeof window !== 'undefined' && typeof window.localStorage !== 'undefined';
  }

  setItem(key: string, value: any) {
    if (this.isBrowser()) localStorage.setItem(key, JSON.stringify(value));
  }

  getItem(key: string) {
    if (this.isBrowser()) {
      const value = localStorage.getItem(key);
      return value ? JSON.parse(value) : null;
    }
    return null;
  }

  removeItem(key: string) {
    if (this.isBrowser()) localStorage.removeItem(key);
  }

  clear() {
    if (this.isBrowser()) localStorage.clear();
  }
}