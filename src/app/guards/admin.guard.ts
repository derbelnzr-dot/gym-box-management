import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);

  if (typeof window !== 'undefined' && window.localStorage) {
    const token = localStorage.getItem('token');
    const userRole = localStorage.getItem('userRole');

    if (token && userRole === 'ADMIN') {
      return true;
    }
  }

  router.navigate(['/login']);
  return false;
};
