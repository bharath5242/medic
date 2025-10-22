import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MediConnectService } from '../../services/mediconnect.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Doctor } from '../../models/Doctor';
import { Clinic } from '../../models/Clinic'; // Assuming you have a Clinic model
import { RouterModule } from '@angular/router';

@Component({
    selector: 'app-clinic-edit',
    templateUrl: './clinicedit.component.html',
    styleUrls: ['./clinicedit.component.scss']
})
export class ClinicEditComponent implements OnInit {
    clinicForm!: FormGroup;
    submitted = false;
    successMessage: string | null = '';
    errorMessage: string | null = '';
    doctor!: Doctor;
    doctorId!: number;
    clinicId!: number;

    constructor(
        private fb: FormBuilder,
        private route: ActivatedRoute,
        private mediService: MediConnectService,
        private router: Router
    ) { }

    ngOnInit(): void {
        this.clinicForm = this.fb.group({
            clinicName: ['', [Validators.required, Validators.minLength(2)]],
            location: ['', Validators.required],
            contactNumber: ['', [Validators.required, Validators.pattern(/^\d{10}$/)]],
            establishedYear: ['', [Validators.required, Validators.pattern(/^(19|20)\d{2}$/)]]
        });

        this.doctorId = Number(localStorage.getItem('doctor_id'));
        // this.mediService.getDoctorById(this.doctorId).subscribe({
        //     next: (data) => {
        //         this.doctor = data;
        //     },
        //     error: () => {
        //         this.errorMessage = 'Failed to load doctor information.';
        //     }
        // });

        this.clinicId = Number(this.route.snapshot.paramMap.get('clinicId'));
        console.log("clinicid: ", this.clinicId);
        this.mediService.getClinicById(this.clinicId).subscribe({
            next: (clinic: Clinic) => {
                this.clinicForm.patchValue({
                    clinicName: clinic.clinicName,
                    location: clinic.location,
                    contactNumber: clinic.contactNumber,
                    establishedYear: clinic.establishedYear
                })
                this.doctor = clinic.doctor;
                // {
                //     "clinicId": 3,
                //         "clinicName": "yashoda hospitals",
                //             "location": "Hyderabad",
                //                 "contactNumber": "9876543211",
                //                     "establishedYear": 1997,
                //                         "doctor": {
                //         "doctorId": 2,
                //             "fullName": "Damon Salvatore",
                //                 "specialty": "Surgeon",
                //                     "contactNumber": "9876543211",
                //                         "email": "damon@gmail.com",
                //                             "yearsOfExperience": 2
                //     },
                //     "doctorId": 2
                // }
            },
            error: () => {
                this.errorMessage = 'Failed to load clinic data.';
            }
        });
    }



    onSubmit(): void {
        this.submitted = true;

        if (this.clinicForm.valid) {
            const updatedClinic = {
                clinicId: this.clinicId,
                ...this.clinicForm.value,
                doctorId: this.doctorId
                // doctor: this.doctor
            };
            
            this.mediService.updateClinic(updatedClinic).subscribe({
                next: () => {
                    this.successMessage = 'Clinic updated successfully!';
                    this.errorMessage = '';
                    this.router.navigate(['/dashboard']);
                },
                error: (error) => this.handleError(error)
            });
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
        } else {
            this.errorMessage = `Server-side error: ${error.status} ${error.message}`;
            if (error.status === 400) {
                this.errorMessage = 'Bad request. Please check your input.';
            }
        }
        this.successMessage = '';
        console.error('An error occurred:', this.errorMessage);
    }
}