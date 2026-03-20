import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth-guard';
import { loginGuard } from './core/guards/login-guard';

export const routes: Routes = [
    {
        path: "",
        loadChildren: () =>
            import('./features/auth/auth.routes').then(m => m.AUTH_ROUTES),
        canActivate: [loginGuard]
    },
    {
        path: "task",
        loadChildren: () =>
            import('./features/tasks/tasks.routes').then(m => m.TASKS_ROUTES),
        canActivate: [authGuard]

    }
];
