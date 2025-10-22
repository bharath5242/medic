import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Doctor } from '../../models/Doctor';
import { MediConnectService } from '../../services/mediconnect.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-clinic-create',
  templateUrl: './cliniccreate.component.html',
  styleUrls: ['./cliniccreate.component.scss']
})
export class ClinicCreateComponent implements OnInit {
  clinicForm!: FormGroup;
  submitted = false;
  successMessage: string | null = '';
  errorMessage: string | null = '';
  doctor: Doctor;
  doctorId: number;

  constructor(private formBuilder: FormBuilder, private mediService: MediConnectService, private router: Router) { }

  ngOnInit(): void {
    this.clinicForm = this.formBuilder.group({
      // clinicId: [1, [Validators.required, Validators.min(1)]],
      clinicName: ['', [Validators.required, Validators.minLength(2)]],
      location: ['', Validators.required],
      contactNumber: ['', [Validators.required, Validators.pattern(/^\d{10}$/)]],
      establishedYear: ['', [Validators.required, Validators.pattern(/^(19|20)\d{2}$/)]]
    });

    this.doctorId = Number(localStorage.getItem('doctor_id'));
    this.mediService.getDoctorById(this.doctorId).subscribe({
      next: (data) => {
        this.doctor = data;
      },
      error: () => {

      }
    });
  }

  onSubmit(): void {
    this.submitted = true;
    if (this.clinicForm.valid) {

      const clinic = {
        ...this.clinicForm.value,
        doctor: this.doctor // assuming `this.doctor` is defined in your component
      };

      this.mediService.addClinic(clinic).subscribe({
        next: () => {
          this.successMessage = 'Clinic created successfully!';
          this.errorMessage = '';
          console.log('Clinic Data:', this.clinicForm.value);

          this.router.navigate(['/dashboard']);
          this.clinicForm.reset();
          this.submitted = false;
        },
        error: (error) => this.handleError(error)
      })
    } else {
      this.errorMessage = 'Please correct the errors in the form.';
      this.successMessage = '';
    }
  }

  resetForm(): void {
    this.clinicForm.reset();
    this.submitted = false;
    this.successMessage = '';
    this.errorMessage = '';
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      this.errorMessage = `Client-side error: ${error.error.message}`;
    }
    else {
      this.errorMessage = `Server-side error: ${error.status} ${error.message}`;
      if (error.status === 400) {
        this.errorMessage = 'Bad request. Please check your input.';
      }
    }
    this.successMessage = '';
    console.log('An error occured:', this.errorMessage);
  }
}