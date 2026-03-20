import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LucideAngularModule, UserPlus } from 'lucide-angular';
import { AuthService } from '../../services/auth.service';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register-form',
  imports: [CommonModule, LucideAngularModule, ReactiveFormsModule, RouterModule],
  templateUrl: './register-form.html',
  styleUrl: './register-form.css',
})
export class RegisterForm {
  private readonly authService: AuthService = inject(AuthService);
  private readonly fb: FormBuilder = inject(FormBuilder);
  private readonly router: Router = inject(Router);
  registerForm!: FormGroup

  readonly UserPlus = UserPlus

  constructor() {
    this.registerForm = this.fb.group({
      name: ["", [Validators.required]],
      email: ["", [Validators.required, Validators.email]],
      password: ["", [Validators.required, Validators.minLength(6)]],
      confirmPassword: ["", [Validators.required, Validators.minLength(6)]]
    })
  }

  onSubmit() {
    const formData = this.registerForm.value
    if (formData.password != formData.confirmPassword) {
      alert("Las contraseñas no coinciden")
      return;
    }

    const { confirmPassword, ...userData } = formData
    this.authService.register(userData)
      .subscribe({
        next: () => {
          alert("Registro Exitoso")
          this.router.navigate([''])
        }
      })
  }

}
