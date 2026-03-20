import { HttpClient } from '@angular/common/http';
import { computed, Injectable, signal } from '@angular/core';
import { ITask } from '../models/task';

@Injectable({
  providedIn: 'root',
})
export class TaskService {
  private readonly baseUrl: string = "http://localhost:8080/api/v1/tasks"

  private _task = signal<ITask[]>([]);
  private _selectedTask = signal<ITask | null>(null);

  tasks = this._task.asReadonly();
  total = computed(() => this._task().length);
  completed = computed(() => this._task().filter(x => x.isCompleted).length);
  actives = computed(() => this._task().filter(x => !x.isCompleted).length);

  selectedTask = this._selectedTask.asReadonly();

  constructor(private readonly http: HttpClient) { }

  loadTask() {
    this.http.get<ITask[]>(this.baseUrl)
      .subscribe({
        next: (res: ITask[]) => { this._task.set(res) },
        error: (err) => console.log(err)
      });
  }

  changeStatus(taskId: string | undefined) {
    this.http.patch<ITask>(`${this.baseUrl}/${taskId}`, {})
      .subscribe({
        next: (updatedTask) => {
          this._task.update(tasks =>
            tasks.map(task =>
              task.id === taskId ? updatedTask : task
            )
          );
        },
        error: (err) => console.log(err)

      });
  }

  createTask(task: ITask) {
    this.http.post<ITask>(`${this.baseUrl}`, task)
      .subscribe({
        next: (res: ITask) => {
          this._task.update(tasks => [...tasks, res])
        },
        error: (err) => console.log(err)
      });
  }

  deleteTask(id: string) {
    this.http.delete(`${this.baseUrl}/${id}`)
      .subscribe({
        next: () => {
          this._task.update(tasks => tasks.filter(t => t.id !== id));
        },
        error: (err) => console.log(err)
      });
  }

  updateTask(taskId: string, task: ITask) {
    this.http.put<ITask>(`${this.baseUrl}/${taskId}`, task)
      .subscribe({
        next: (updatedTask) => {
          this._selectedTask.set(null);
          this._task.update(tasks =>
            tasks.map(task =>
              task.id === taskId ? updatedTask : task
            )
          );
        },
        error: (err) => console.log(err)

      });
  }

  // Método para actualizar la tarea seleccionada
  selectTaskById(id: string) {
    const task = this._task().find(t => t.id === id) || null;
    this._selectedTask.set(task);
  }

}
