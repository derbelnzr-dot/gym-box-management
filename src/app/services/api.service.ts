import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';
import { Member } from '../models/member.model';
import { Coach } from '../models/coach.model';
import { Subscription } from '../models/subscription.model';
import { Session } from '../models/session.model';
import { Payment } from '../models/payment.model';
import { Invoice } from '../models/invoice.model';
import { DashboardStats } from '../models/stats.model';
import { Product } from '../models/product.model';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private api = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getMembers(): Observable<Member[]> {
    return this.http.get<Member[]>(`${this.api}/members`);
  }

  getMember(id: number): Observable<Member> {
    return this.http.get<Member>(`${this.api}/members/${id}`);
  }

  createMember(data: Member): Observable<Member> {
    return this.http.post<Member>(`${this.api}/members`, data);
  }

  updateMember(id: number, data: Member): Observable<Member> {
    return this.http.put<Member>(`${this.api}/members/${id}`, data);
  }

  deleteMember(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/members/${id}`);
  }

  getCoaches(): Observable<Coach[]> {
    return this.http.get<Coach[]>(`${this.api}/coaches`);
  }

  getCoach(id: number): Observable<Coach> {
    return this.http.get<Coach>(`${this.api}/coaches/${id}`);
  }

  createCoach(data: Coach): Observable<Coach> {
    return this.http.post<Coach>(`${this.api}/coaches`, data);
  }

  updateCoach(id: number, data: Coach): Observable<Coach> {
    return this.http.put<Coach>(`${this.api}/coaches/${id}`, data);
  }

  deleteCoach(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/coaches/${id}`);
  }

  getSubscriptions(): Observable<Subscription[]> {
    return this.http.get<Subscription[]>(`${this.api}/subscriptions`);
  }

  createSubscription(data: Subscription): Observable<Subscription> {
    return this.http.post<Subscription>(`${this.api}/subscriptions`, data);
  }

  deleteSubscription(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/subscriptions/${id}`);
  }

  getSessions(): Observable<Session[]> {
    return this.http.get<Session[]>(`${this.api}/sessions`);
  }

  createSession(data: Session): Observable<Session> {
    return this.http.post<Session>(`${this.api}/sessions`, data);
  }

  updateSession(id: number, data: Session): Observable<Session> {
    return this.http.put<Session>(`${this.api}/sessions/${id}`, data);
  }

  deleteSession(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/sessions/${id}`);
  }

  getPlanning(): Observable<{ [key: string]: Session[] }> {
    return this.http.get<{ [key: string]: Session[] }>(`${this.api}/planning`);
  }

  getPayments(): Observable<Payment[]> {
    return this.http.get<Payment[]>(`${this.api}/payments`);
  }

  createPayment(data: Payment): Observable<Payment> {
    return this.http.post<Payment>(`${this.api}/payments`, data);
  }

  getInvoices(): Observable<Invoice[]> {
    return this.http.get<Invoice[]>(`${this.api}/invoices`);
  }

  createInvoice(data: Invoice): Observable<Invoice> {
    return this.http.post<Invoice>(`${this.api}/invoices`, data);
  }

  getDashboardStats(): Observable<DashboardStats> {
    return this.http.get<DashboardStats>(`${this.api}/stats/dashboard`);
  }

  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.api}/products`);
  }
}
