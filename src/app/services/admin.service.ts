import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';
import { Member } from '../models/member.model';
import { DashboardStats } from '../models/stats.model';

@Injectable({ providedIn: 'root' })
export class AdminService {
  private api = `${environment.apiUrl}/admin`;

  constructor(private http: HttpClient) {}

  getMembers(): Observable<Member[]> {
    return this.http.get<Member[]>(`${this.api}/members`);
  }

  getStats(): Observable<DashboardStats> {
    return this.http.get<DashboardStats>(`${this.api}/stats`);
  }

  deleteMember(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/members/${id}`);
  }

  addMember(memberData: any): Observable<Member> {
    return this.http.post<Member>(`${this.api}/members`, memberData);
  }
}
