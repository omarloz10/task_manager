import { Component } from '@angular/core';
import { CircleCheck, LucideAngularModule } from 'lucide-angular';
import { Router, RouterLink } from "@angular/router";
import { AuthService } from '../../services/auth.service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginData } from '../../models/login-data';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login-form',
  imports: [CommonModule, RouterLink, LucideAngularModule, ReactiveFormsModule],
  templateUrl: './login-form.html',
  styleUrl: './login-form.css',
})
export class LoginForm {
  CircleCheck = CircleCheck;

  loginForm: FormGroup;

  constructor(
    private readonly authService: AuthService,
    private readonly fb: FormBuilder,
    private readonly router: Router
  ) {
    this.loginForm = this.fb.group({
      email: ["", [Validators.required, Validators.email]],
      password: ["", [Validators.required]]
    })
  }

  onSubmit() {
    const loginData = this.loginForm.value as LoginData

    this.authService.login(loginData)
      .subscribe({
        next: () => { this.loginForm.reset(); this.router.navigate(["/task/"]) },
        error: (err) => console.log(err)
      });
  }
}
