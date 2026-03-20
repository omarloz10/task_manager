import { HttpInterceptorFn } from '@angular/common/http';
import { AuthService } from '../../features/auth/services/auth.service';
import { inject } from '@angular/core';

export const authInterceptor: HttpInterceptorFn = (req, next) => {

  const authService: AuthService = inject(AuthService)

  if (req.url.includes("/api/v1/auth/")
    || req.url.includes("/api/v1/users/register"))
    return next(req);


  return next(req.clone({
    setHeaders: { Authorization: `Bearer ${localStorage.getItem("access_token")}` }
  }))
};
