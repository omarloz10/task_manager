import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const loginGuard: CanActivateFn = (route, state) => {
  const router = inject(Router)

  if (localStorage.getItem("access_token")) {
    router.navigate(['/task'])
    return false
  }

  return true;
};
