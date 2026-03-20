import { Component, EventEmitter, inject, Input, OnInit, Output, signal } from '@angular/core';
import { LucideAngularModule, PencilIcon, TrashIcon } from 'lucide-angular';
import { TaskService } from '../../services/task.service';

@Component({
  selector: 'app-list-task',
  imports: [LucideAngularModule],
  templateUrl: './list-task.html',
  styleUrl: './list-task.css',
})
export class ListTask {

  private readonly taskService: TaskService = inject(TaskService)
  tasks = this.taskService.tasks;

  PencilIcon = PencilIcon
  TrashIcon = TrashIcon

  onChange(taskId: string) {
    this.taskService.changeStatus(taskId);
  }

  deleteTask(taskId: string) {
    this.taskService.deleteTask(taskId)
  }

  selectTask(taskId: string) {
    this.taskService.selectTaskById(taskId);
  }

}
