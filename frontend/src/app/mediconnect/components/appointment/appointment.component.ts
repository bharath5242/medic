// import { Component, OnInit } from '@angular/core';
// import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
// import { Clinic } from '../../models/Clinic';
// import { Patient } from '../../models/Patient';
// import { MediConnectService } from '../../services/mediconnect.service';
// import { HttpErrorResponse } from '@angular/common/http';
// import { Appointment } from '../../models/Appointment';

// @Component({
//     selector: 'app-appointment',
//     templateUrl: './appointment.component.html',
//     styleUrls: ['./appointment.component.scss']
// })
// export class AppointmentCreateComponent implements OnInit {
//     appointmentForm!: FormGroup;
//     successMessage: string | null = null;
//     errorMessage: string | null = null;
//     submitted = false;
//     clinics: Clinic[];
//     selectedPatient: Patient;
//     patientId: number;

//     constructor(private fb: FormBuilder, private mediService: MediConnectService) { }

//     ngOnInit(): void {

//         const storedPatientId = localStorage.getItem('patient_id');
//         console.log("Patient in LC: ", storedPatientId);
//         this.patientId = Number(storedPatientId);

//         this.appointmentForm = this.fb.group({
//             // appointmentId: [null, [Validators.required, Validators.min(1)]],
//             patientId: [{value: this.patientId, disabled: true}],  //[Validators.required, Validators.min(1)]
//             clinic: ["", [Validators.required]],   // , [Validators.required, Validators.min(1)]
//             appointmentDate: ['', [Validators.required]],
//             status: ['', [Validators.required]],
//             purpose: ['', [Validators.required, Validators.minLength(5)]]
//         });

//         // this.patientId = Number(localStorage.getItem('patient_id'));
//         this.mediService.getPatientById(this.patientId).subscribe((data) => {
//             this.selectedPatient = data;
//         })
//         this.mediService.getAllClinics().subscribe((data) => {
//             this.clinics = data;
//         })
//     }

//     // Custom validator for future dates
//     private futureDateValidator(control: AbstractControl): { [key: string]: any } | null {
//         if (!control.value) return null;
//         const selected = new Date(control.value);
//         const today = new Date();
//         today.setHours(0, 0, 0, 0);
//         return selected >= today ? null : { pastDate: true };
//     }

//     get f() {
//         return this.appointmentForm.controls;
//     }

//     onSubmit(): void {
//         console.log("Appointment form isValid? ", this.appointmentForm.valid, this.appointmentForm.value);

//         if (this.appointmentForm.valid) {
//             const appointment: Appointment = {
//                 ...this.appointmentForm.getRawValue(),
//                 patient: this.selectedPatient
//             }
//             this.mediService.createAppointment(appointment).subscribe({
//                 next: () => {
//                     this.successMessage = `Appointment created successfully!`;
//                     this.errorMessage = null;
//                     this.appointmentForm.reset()
//                 },
//                 error: (error) => this.handleError(error)
//             })
//             // this.resetForm();
//         }
//         else {
//             this.errorMessage = 'Please fill out all required fields correctly.';
//             this.successMessage = null;
//         }
//     }

//     resetForm(): void {
//         this.appointmentForm.reset({
//             appointmentId: null,
//             patientId: null,
//             clinicId: null,
//             appointmentDate: '',
//             status: '',
//             purpose: ''
//         });
//     }

//     private handleError(error: HttpErrorResponse) {
//         if (error.error instanceof ErrorEvent) {
//             this.errorMessage = `Client-side error: ${error.error.message}`;
//         }
//         else {
//             this.errorMessage = `Server-side error: ${error.status} ${error.message}`;
//             if (error.status === 400) {
//                 this.errorMessage = 'Bad request. Please check your input.';
//             }
//         }
//         this.successMessage = null;
//         console.log('An error occured:', this.errorMessage);
//     }
// }







import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Appointment } from '../../models/Appointment';
import { Clinic } from '../../models/Clinic';
import { Patient } from '../../models/Patient';
import { MediConnectService } from '../../services/mediconnect.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
    selector: 'app-appointment',
    templateUrl: './appointment.component.html',
    styleUrls: ['./appointment.component.scss']
})
export class AppointmentCreateComponent implements OnInit {
    appointmentForm!: FormGroup;
    successMessage: string | null = null;
    errorMessage: string | null = null;

    clinics: Clinic[];
    selectedPatient: Patient;
    patientId: number;

    constructor(private formBuilder: FormBuilder, private mediconnectService: MediConnectService, private router: Router, private route: ActivatedRoute) { }

    ngOnInit(): void {
        this.patientId = Number(localStorage.getItem("patient_id"));
        this.mediconnectService.getPatientById(this.patientId).subscribe({
            next: (response) => {
                this.selectedPatient = response;
            },
            error: (error) => console.log('Error loading selectedPatient', error)
        });

        const clinicId = Number(this.route.snapshot.paramMap.get('clinicId'));


        this.appointmentForm = this.formBuilder.group({
            patientId: [{ value: this.patientId, disabled: true }],
            clinic: [null, [Validators.required]],
            appointmentDate: ['', [Validators.required]],
            status: ['Pending', [Validators.required]],
            purpose: ['', [Validators.required, Validators.minLength(5)]]
        });
        this.mediconnectService.getClinicById(clinicId).subscribe({
            next: (clinic: Clinic) => {
                this.appointmentForm.patchValue({ clinic });
            },
            error: (error) => console.log('Error loading clinic', error)
        });

        this.mediconnectService.getAllClinics().subscribe({
            next: (response) => {
                this.clinics = response;
            },
            error: (error) => console.log('Error loading clinics', error)
        });
    }

    onSubmit(): void {
        if (this.appointmentForm.valid) {
            const appointment: Appointment = {
                ...this.appointmentForm.getRawValue(),
                patient: this.selectedPatient,
            };
            this.mediconnectService.createAppointment(appointment).subscribe({
                next: (response) => {
                    this.errorMessage = null;
                    console.log(response);
                    this.router.navigate(['/dashboard']);
                    this.appointmentForm.reset();
                    this.successMessage = 'Appointment created successfully!';
                },
                error: (error) => {
                    this.handleError(error);
                }
            });
        } else {
            this.errorMessage = 'Please fill out all required fields correctly.';
            this.successMessage = null;
        }
    }

    private handleError(error: HttpErrorResponse): void {
        if (error.error instanceof ErrorEvent) {
            this.errorMessage = `Client-side error: ${error.error.message}`;
        } else {
            this.errorMessage = `Server-side error: ${error.status} ${error.message}`;
            if (error.status === 400) {
                this.errorMessage = 'Bad request. Please check your input.';
            }
        }
        this.successMessage = null;
        console.error('An error occurred:', this.errorMessage);
    }
}
