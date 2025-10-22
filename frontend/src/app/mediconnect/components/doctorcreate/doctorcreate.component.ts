import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Doctor } from '../../models/Doctor';
import { HttpErrorResponse } from '@angular/common/http';
import { MediConnectService } from '../../services/mediconnect.service';

@Component({
    selector: 'app-doctor-create',
    templateUrl: './doctorcreate.component.html',
    styleUrls: ['./doctorcreate.component.scss']
})
export class DoctorCreateComponent implements OnInit {
    doctorForm!: FormGroup;
    doctor: Doctor;
    submitted = false;
    successMessage: string | null = null;
    errorMessage: string | null = null;

    constructor(private formBuilder: FormBuilder, private mediService: MediConnectService) { }

    ngOnInit(): void {
        this.doctorForm = this.formBuilder.group({
            doctorId: [null, [Validators.required, Validators.min(1)]],
            fullName: ['', [Validators.required, Validators.minLength(2)]],
            specialty: ['', [Validators.required]],
            contactNumber: ['', [Validators.required, Validators.pattern(/^\d{10}$/)]],
            email: ['', [Validators.required, Validators.email]],
            yearsOfExperience: [null, [Validators.required, Validators.min(1)]]
        });
    }

    onSubmit(): void {
        // this.submitted = true;
        if (this.doctorForm.valid) {
            this.mediService.addDoctor(this.doctorForm.value).subscribe({
                next: (data) => {
                    // this.patientForm.patchValue(data);
                    this.doctor = data;
                    this.successMessage = 'Patient created successfully!';
                    this.errorMessage = '';
                    console.log('Patient Created: ', this.doctor);
                    // this.resetForm();
                    this.doctorForm.reset();
                },
                error: (errorr) => {
                    this.handleError(errorr);
                }
            })
            this.successMessage = 'Doctor has been successfully created!';
            this.errorMessage = null;
            console.log('Doctor Data:', this.doctorForm.value);
            this.doctorForm.reset();
            // this.submitted = false;
        } else {
            this.errorMessage = 'Please fill out all required fields correctly.';
            this.successMessage = null;
        }
    }

    resetForm(): void {
        this.doctorForm.reset({
            doctorId: null,
            fullName: '',
            specialty: '',
            contactNumber: '',
            email: '',
            yearsOfExperience: null
        });
        // this.submitted = false;
        // this.successMessage = '';
        // this.errorMessage = '';
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