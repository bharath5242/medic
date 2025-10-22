import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Patient } from "../../models/Patient";
import { User } from "../../models/User";
import { MediConnectService } from "../../services/mediconnect.service";
import { ActivatedRoute, Route, Router } from "@angular/router";
import { HttpErrorResponse } from "@angular/common/http";
@Component({
    selector: 'patient-edit',
    templateUrl: './patientedit.component.html',
    styleUrls: ['./patientedit.component.scss'],
})
export class PatientEditComponent implements OnInit {
    patientForm: FormGroup;
    successMessage: string | null;
    errorMessage: string | null;
    patientId: number;
    userId: number;
    patient: Patient;
    user: User;

    constructor(private fb: FormBuilder, private mediService: MediConnectService, private route: ActivatedRoute, private router: Router) { }

    ngOnInit(): void {
        this.initializeForm();
    }

    initializeForm() {
        this.patientForm = this.fb.group({
            fullName: ['', [Validators.required, Validators.minLength(2)]],
            // username: ['', [Validators.required, Validators.pattern('[a-zA-Z0-9]*')]],
            // password: ['', [Validators.required, Validators.pattern('^(?=.*[A-Z])(?=.*\d)[A-Za-z\d]{8,}$')]],
            dateOfBirth: ['', [Validators.required]],
            contactNumber: ['', [Validators.required, Validators.pattern('^\\d{10}$')]],
            email: ['', [Validators.required, Validators.email]],
            address: ['', [Validators.required, Validators.minLength(5)]]
        })

        this.patientId = Number(this.route.snapshot.paramMap.get('patientId'));
        this.userId = Number(this.route.snapshot.paramMap.get('userId'));
        this.loadPatientDetails();
    }

    loadPatientDetails() {
        console.log("Inside load patient");
        this.mediService.getPatientById(this.patientId).subscribe((data) => {
            this.patientForm.patchValue(data);
        })
    }

    onSubmit() {
        if (this.patientForm.valid) {

            const updatedPatient = {
                ...this.patientForm.value,
                patientId: this.patientId
            };

            this.mediService.updatePatient(updatedPatient).subscribe({
                next: (data) => {
                    // this.doctorDetails = data;
                    this.router.navigate(['/dashboard']);
                    console.log("Doctor details: ", data);
                    this.successMessage = 'done';
                    this.errorMessage = null;
                },
                error: (error) => this.handleError(error)
            })
        }
        this.errorMessage = 'Please fill out all the required fields';
        this.successMessage = null;
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
