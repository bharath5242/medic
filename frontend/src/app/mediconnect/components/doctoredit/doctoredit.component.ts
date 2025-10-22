
    import { HttpErrorResponse } from '@angular/common/http';
    import { Component, OnInit } from '@angular/core';
    import { FormBuilder, FormGroup, Validators } from '@angular/forms';
    import { ActivatedRoute, Router } from '@angular/router';
    import { Doctor } from '../../models/Doctor';
    import { DoctorDTO } from '../../models/DoctorDTO';
    import { User } from '../../models/User';
    import { MediConnectService } from '../../services/mediconnect.service';
     
    @Component({
      selector: 'app-doctoredit',
      templateUrl: './doctoredit.component.html',
      styleUrls: ['./doctoredit.component.scss']
    })
    export class DoctorEditComponent implements OnInit {
      doctorForm!: FormGroup;
      successMessage: string | null = null;
      errorMessage: string | null = null;
      submitted = false;
     
      doctorId!: number;
      userId!: number;
      doctor!: Doctor;
      user!: User;
     
      constructor(
        private formBuilder: FormBuilder,
        private mediconnectService: MediConnectService,
        private route: ActivatedRoute,
        private router: Router
      ) {}
     
      ngOnInit(): void {
        this.userId = Number(localStorage.getItem("user_id"));
        this.initializeForm();
        this.doctorId = Number(localStorage.getItem("doctor_id"));
        this.loadDoctorDetails();
      }
     
      initializeForm(): void {
        this.doctorForm = this.formBuilder.group({
          fullName: ['', [Validators.required, Validators.minLength(2)]],
          //username: ['', [Validators.required, Validators.pattern(/^[a-zA-Z0-9]+$/)]],
          //password: ['', [Validators.required, Validators.minLength(8), Validators.pattern(/^(?=.*[A-Z])(?=.*\d).+$/)]],
          specialty: ['', [Validators.required]],
          contactNumber: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]],
          email: ['',[Validators.required, Validators.email]],
          yearsOfExperience: [null, [Validators.required, Validators.min(1)]]
        });
      }
     
      loadDoctorDetails(): void {
        this.mediconnectService.getDoctorById(this.doctorId).subscribe({
          next: (response) => {
            this.doctor = response;
            this.doctorForm.patchValue(response);
          },
          error: (error) => console.error('Error loading doctor details:', error)
        });
      }
     
      onSubmit(): void {
        this.submitted = true;
     
        if (this.doctorForm.valid) {
          const doctor: DoctorDTO = {
            ...this.doctorForm.getRawValue(),
            doctorId: this.doctorId,
          };
          this.mediconnectService.updateDoctor(doctor).subscribe({
            next: (response) => {
              this.errorMessage = null;
              this.successMessage = 'Doctor updated successfully!';
              this.router.navigate(['/dashboard']);
              this.doctorForm.reset();
              this.submitted = false;
            },
            error: (error) => {
              this.handleError(error);
            }
          });
        } else {
          this.errorMessage = 'Please correct the errors in the form.';
          this.successMessage = null;
        }
      }
     
      allowOnlyDigits(event: KeyboardEvent): void {
        const charCode = event.charCode;
        if (charCode < 48 || charCode > 57) {
          event.preventDefault();
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
     


// import { Component } from "@angular/core";
// import { FormBuilder, FormGroup, Validators } from "@angular/forms";
// import { Doctor } from "../../models/Doctor";
// import { User } from "../../models/User";
// import { MediConnectService } from "../../services/mediconnect.service";
// import { ActivatedRoute } from "@angular/router";
// import { HttpErrorResponse } from "@angular/common/http";
// import { DoctorDTO } from "../../models/DoctorDTO";

// @Component({
//     selector: 'doctor-edit',
//     templateUrl: './doctoredit.component.html',
//     styleUrls: ['./doctoredit.component.scss'],
// })
// export class DoctorEditComponent {
//     doctorForm: FormGroup;
//     successMessage: string | null;
//     errorMessage: string | null;
//     doctorId: number;
//     userId: number;
//     doctor: Doctor;
//     user: User;

//     constructor(private fb: FormBuilder, private mediService: MediConnectService, private route: ActivatedRoute) { }

//     ngOnInit(): void {
//         this.userId = Number(localStorage.getItem('user_id'));
//         this.initializeForm();
//         this.doctorId = Number(this.route.snapshot.paramMap.get('id'));
        
//         this.loadDoctorDetails();
//     }
//     initializeForm() {
//         this.doctorForm = this.fb.group({
//             fullName: ['', [Validators.required, Validators.minLength(2)]],
//             username: ['', [Validators.required, Validators.pattern('^[a-zA-Z0-9]+$/')]],
//             password: ['', [Validators.required, Validators.minLength(8), Validators.pattern('/^(?=.*[A-Z])(?=.*\d).+$/')]],
//             specialty: ['', [Validators.required]],
//             contactNumber: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]],
//             email: [{value: '', disabled: true}],
//             yearsOfExperience: [null, [Validators.required, Validators.min(1)]]
//         })

//         // this.doctorId = Number(this.route.snapshot.paramMap.get('doctorId'));
//         // this.userId = Number(this.route.snapshot.paramMap.get('userId'));
//     }

//     loadDoctorDetails() {
//         this.mediService.getDoctorById(this.doctorId).subscribe((data) => {
//             this.doctor = data;
//             this.doctorForm.patchValue({
//                 fullName: this.doctor.fullName,
//                 contactNumber: this.doctor.contactNumber,
//                 email: this.doctor.email,
//                 specialty: this.doctor.specialty,
//                 yearsOfExperience: this.doctor.yearsOfExperience,
//             });
//         })

//         this.mediService.getUserById(this.userId).subscribe({
//             next: (response)=>{
//                 this.user = response;
//                 this.doctorForm.patchValue({
//                     username: this.user.username,
//                     password: this.user.password
//                 })
//             }
//         })
//     }

//     onSubmit() {
//         if (this.doctorForm.valid) {
//             const doctor: DoctorDTO={
//                 ...this.doctorForm.getRawValue(),
//                 doctorId: this.doctorId,
//             }
//             console.log("doctor in edit: ", doctor);
//             this.mediService.updateDoctor(doctor).subscribe({
//                 next: (data) => {
//                     console.log("Doctor details: ", data);
//                     this.successMessage = 'Doctor updated successfully!';
//                     this.doctorForm.reset();
//                     this.errorMessage = null;
//                 },
//                 error: (error) => this.handleError(error)
//             })
//         }
//         console.log("doctor in edit with invalid form: ", this.doctorForm.value);
//         this.errorMessage = 'Please fill out all the required fields';
//         this.successMessage = null;
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
//         this.successMessage = '';
//         console.log('An error occured:', this.errorMessage);
//     }
// }