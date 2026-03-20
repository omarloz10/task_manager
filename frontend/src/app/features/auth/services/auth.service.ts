import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, EMPTY, Observable, tap } from 'rxjs';
import { LoginResponse } from '../models/login-response';
import { LoginData } from '../models/login-data';
import { User } from '../models/user';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly baseUrl: string = "http://localhost:8080/api/v1";

  constructor(private readonly http: HttpClient, private readonly router: Router) { }

  login(loginData: LoginData): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.baseUrl}/auth/login`, loginData)
      .pipe(
        tap((res: LoginResponse) => {
          localStorage.setItem("refresh_token", res.access_token);
          localStorage.setItem("access_token", res.refresh_token);
        }));
  }

  register(registerData: User): Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/users/register`, registerData)
      .pipe(
        tap((res: User) => {
          return res
        })
      );
  }

  refreshToken() {
    const refreshToken = localStorage.getItem("refresh_token")
    return this.http.post(`${this.baseUrl}/refresh-token`, refreshToken, {
      headers: { 'Content-Type': 'text/plain' },
      responseType: 'json'
    })
      .pipe(
        tap((res) => {
          console.log(res);
        }),
        catchError((err) => {
          console.log(err)
          return EMPTY
        })
      );
  }

  logout() {
    localStorage.removeItem('access_token');
    localStorage.removeItem('refresh_token');
    this.router.navigate(['']);
  }

}
