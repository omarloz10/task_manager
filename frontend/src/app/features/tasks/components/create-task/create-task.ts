import { Component, computed, effect, inject } from '@angular/core';
import { TaskService } from '../../services/task.service';
import { FormBuilder, FormGroup, Validators, ɵInternalFormsSharedModule, ReactiveFormsModule } from '@angular/forms';
import { ITask } from '../../models/task';

@Component({
  selector: 'app-create-task',
  imports: [ɵInternalFormsSharedModule, ReactiveFormsModule],
  templateUrl: './create-task.html',
  styleUrl: './create-task.css',
})
export class CreateTask {

  taskForm!: FormGroup;

  private readonly taskService: TaskService = inject(TaskService)
  private readonly fb: FormBuilder = inject(FormBuilder);

  buttonText = computed(() =>
    this.taskService.selectedTask() ? 'Actualizar Tarea' : 'Crear Tarea'
  );

  constructor() {
    this.taskForm = this.fb.group({
      title: ["", [Validators.required]],
      description: ["", [Validators.required]]
    })

    effect(() => {
      const task = this.taskService.selectedTask();
      if (task) {
        this.taskForm.patchValue({
          title: task.title,
          description: task.description
        });
      } else {
        this.taskForm.reset();
      }
    });
  }

  onSubmit() {
    const task: ITask = this.taskForm.value as ITask;

    if (this.taskService.selectedTask()) {
      // editar
      this.taskService.updateTask(this.taskService.selectedTask()!.id, task);
    } else {
      // crear nueva
      this.taskService.createTask(task);
    }

    this.taskForm.reset();
  }

}
