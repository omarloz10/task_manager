import { Component, computed, inject, OnInit, signal, Signal } from '@angular/core';
import { LogOut, LucideAngularModule } from 'lucide-angular';
import { ListTask } from '../../components/list-task/list-task';
import { CreateTask } from '../../components/create-task/create-task';
import { TaskService } from '../../services/task.service';
import { ITask } from '../../models/task';
import { AuthService } from '../../../auth/services/auth.service';

@Component({
  selector: 'app-tasks',
  imports: [LucideAngularModule, ListTask, CreateTask],
  templateUrl: './tasks.html',
  styleUrl: './tasks.css',
})
export class Tasks implements OnInit {
  private readonly taskService: TaskService = inject(TaskService)
  private readonly authService: AuthService = inject(AuthService)
  total = this.taskService.total;
  actives = this.taskService.actives;
  completed = this.taskService.completed;

  LogOut = LogOut;

  ngOnInit(): void {
    this.taskService.loadTask()
  }

  logOut() {
    this.authService.logout();
  }

}
