import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { catchError, throwError } from 'rxjs';
import { Router } from '@angular/router';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const snackBar = inject(MatSnackBar);
  const router = inject(Router);

  return next(req).pipe(
    catchError((error) => {
      let message = 'Une erreur est survenue';

      if (error.status === 0) {
        message = 'Impossible de se connecter au serveur';
      } else if (error.status === 401) {
        message = 'Session expirée. Veuillez vous reconnecter';
        localStorage.removeItem('token');
        localStorage.removeItem('currentUser');
        router.navigate(['/login']);
      } else if (error.status === 403) {
        message = 'Accès refusé';
      } else if (error.status === 404) {
        message = 'Ressource non trouvée';
      } else if (error.error?.message) {
        message = error.error.message;
      } else if (error.error?.messages) {
        const msgs = Object.values(error.error.messages).join(', ');
        message = msgs || message;
      }

      snackBar.open(message, 'Fermer', {
        panelClass: ['error-snackbar']
      });

      return throwError(() => error);
    })
  );
};
