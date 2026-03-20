import { Routes } from '@angular/router';
import { Tasks } from './pages/tasks/tasks';
import { authGuard } from '../../core/guards/auth-guard';

export const TASKS_ROUTES: Routes = [
    {
        path: "",
        component: Tasks    }
];