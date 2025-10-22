
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-registration',
    templateUrl: './registration.component.html',
    styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {
    registrationForm!: FormGroup;
    successMessage: string | null = null;
    errorMessage: string | null = null;
    selectedRole: string | null = null;

    constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router) { }

    ngOnInit(): void {
        this.registrationForm = this.formBuilder.group({
            role: ['', Validators.required],
            username: [
                '',
                [
                    Validators.required,
                    Validators.pattern('^[a-zA-Z0-9]*$') // No special characters
                ]
            ],
            password: [
                '',
                [
                    Validators.required,
                    Validators.minLength(8),
                    Validators.pattern('^(?=.*[A-Z])(?=.*[0-9]).*$') // At least one capital letter and one number
                ]
            ],
            fullName: ['', Validators.required],
            contactNumber: [
                '',
                [
                    Validators.required,
                    Validators.pattern('^[0-9]{10}$') // 10-digit phone number
                ]
            ],
            email: ['', [Validators.required, Validators.email]],
            specialty: [''],
            yearsOfExperience: [''],
            dateOfBirth: [''],
            address: ['']
        });

        // Update validators based on role
        this.registrationForm.get('role')?.valueChanges.subscribe((role) => {
            this.onRoleChange({ target: { value: role } } as any);
        });
    }

    onRoleChange(event: Event): void {
        const role = (event.target as HTMLSelectElement).value;
        this.selectedRole = role;

        // Reset role-specific fields
        this.registrationForm.get('specialty')?.clearValidators();
        this.registrationForm.get('yearsOfExperience')?.clearValidators();
        this.registrationForm.get('dateOfBirth')?.clearValidators();
        this.registrationForm.get('address')?.clearValidators();

        if (role === 'DOCTOR') {
            this.registrationForm.get('specialty')?.setValidators([Validators.required]);
            this.registrationForm.get('yearsOfExperience')?.setValidators([
                Validators.required,
                Validators.pattern('^[0-9]*$')
            ]);
            this.registrationForm.get('dateOfBirth')?.setValue('');
            this.registrationForm.get('address')?.setValue('');
        } else if (role === 'PATIENT') {
            this.registrationForm.get('dateOfBirth')?.setValidators([Validators.required]);
            this.registrationForm.get('address')?.setValidators([Validators.required]);
            this.registrationForm.get('specialty')?.setValue('');
            this.registrationForm.get('yearsOfExperience')?.setValue('');
        }

        this.registrationForm.get('specialty')?.updateValueAndValidity();
        this.registrationForm.get('yearsOfExperience')?.updateValueAndValidity();
        this.registrationForm.get('dateOfBirth')?.updateValueAndValidity();
        this.registrationForm.get('address')?.updateValueAndValidity();
    }

    onSubmit(): void {
        if (this.registrationForm.valid) {
            // Simulate backend call
            const formData = this.registrationForm.value;
            console.log(formData);
            this.authService.createUser(formData).subscribe((data) => {
                console.log(data);
                this.successMessage = 'Registration successful!';
                this.errorMessage = null;
                this.router.navigate(['/login']);
                this.resetForm();
            })
        } else {
            this.errorMessage = 'Please fill out all fields correctly.';
            this.successMessage = null;
        }
    }

    resetForm(): void {
        this.registrationForm.reset();
        this.selectedRole = null;
        this.registrationForm.get('role')?.setValue('');
    }
}