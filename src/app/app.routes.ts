import { Routes } from '@angular/router';
import { authGuard } from './guards/admin.guard';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('../components/accueil/accueil.component').then(m => m.AccueilComponent),
    pathMatch: 'full'
  },
  {
    path: 'login',
    loadComponent: () => import('../components/login/login.component').then(m => m.LoginComponent)
  },
  {
    path: 'signup',
    loadComponent: () => import('../components/sign-up/sign-up.component').then(m => m.SignUpComponent)
  },
  {
    path: 'programmes',
    loadComponent: () => import('../components/programmes/programmes.component').then(m => m.ProgrammesComponent)
  },
  {
    path: 'coachs',
    loadComponent: () => import('../components/coachs/coach.component').then(m => m.CoachComponent)
  },
  {
    path: 'planning',
    loadComponent: () => import('../components/planning/planning.component').then(m => m.PlanningComponent)
  },
  {
    path: 'contact',
    loadComponent: () => import('../components/contact/contact.component').then(m => m.ContactComponent)
  },
  {
    path: 'contactCoach',
    loadComponent: () => import('../components/contact-coachs/contact-coachs.component').then(m => m.ContactCoachsComponent)
  },
  {
    path: 'store',
    loadComponent: () => import('../components/store/store.component').then(m => m.StoreComponent)
  },
  {
    path: 'cart',
    loadComponent: () => import('../cart/cart.component').then(m => m.CartComponent)
  },
  {
    path: 'admin-dashboard',
    loadComponent: () => import('../components/admin-dashboard/admin-dashboard.component').then(m => m.AdminDashboardComponent),
    canActivate: [authGuard]
  },
  {
    path: 'member-home',
    loadComponent: () => import('./member-home/member-home.component').then(m => m.MemberHomeComponent)
  },
  {
    path: 'imc',
    loadComponent: () => import('../components/imc/imc.component').then(m => m.ImcComponent)
  },
  {
    path: '**',
    loadComponent: () => import('../components/not-found/not-found.component').then(m => m.NotFoundComponent)
  }
];
