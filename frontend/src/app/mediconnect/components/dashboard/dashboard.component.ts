import { Component, OnInit } from "@angular/core";
import { Clinic } from "../../models/Clinic";
import { Appointment } from "../../models/Appointment";
import { Patient } from "../../models/Patient";
import { FormBuilder } from "@angular/forms";
import { MediConnectService } from "../../services/mediconnect.service";
import { Doctor } from "../../models/Doctor";
import { Router } from "@angular/router";
@Component({
    selector: 'dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {
    doctorDetails: any;
    patientDetails: any;
    doctors: Doctor[] = [];
    clinics: Clinic[] = [];
    appointments: Appointment[] = [];
    patients: Patient[] = [];
    role: string | null;
    userId: number;
    doctorId: number;
    patientId: number;
    selectedClinicId: number | undefined;
    selectClinicAppointments: Appointment[] = [];

    constructor(private fb: FormBuilder, private mediService: MediConnectService, private router: Router) { }

    ngOnInit(): void {
        this.role = localStorage.getItem('role');
        this.userId = Number(localStorage.getItem('user_id'));
        this.doctorId = Number(localStorage.getItem('doctor_id'));
        this.patientId = Number(localStorage.getItem('patient_id'));
        console.log("doc", this.doctorId);
        console.log("pat", this.patientId);
        console.log("usr", this.userId);
        if (!isNaN(this.doctorId) && this.doctorId) {
            console.log("Doctor Id: ", this.doctorId);
            this.loadDoctorData();
            this.doctorClinics();
        }
        else {
            console.log("Patient ID: ", this.patientId);
            this.loadPatientData();
        }
    }

    loadDoctorData() {
        console.log("Inside the load doc");
        this.mediService.getDoctorById(this.doctorId).subscribe({
            next: (data) => {
                this.doctorDetails = data;
                console.log("Doctor details: ", this.doctorDetails);
            },
            error: () => {
                console.log("failed to find doctor details")
            }
        });
        this.mediService.getClinicsByDoctorId(this.doctorId).subscribe((data) => {
            this.clinics = data;
        })
    }

    doctorClinics() {
        this.mediService.getClinicsByDoctorId(this.doctorId).subscribe((data) => {
            this.clinics = data;
        })
    }

    loadAppointments(clinicId: number) {
        this.mediService.getAppointmentsByClinic(clinicId).subscribe((data) => {
            this.selectClinicAppointments = data;
        })
    }

    onClinicSelect(clinic: Clinic) {
        this.selectedClinicId = clinic.clinicId;
        this.loadAppointments(this.selectedClinicId);
    }

    deleteDoctor() {
        this.mediService.deleteDoctor(this.doctorId).subscribe((data) => {
            this.doctorDetails = null;
        })
    }

    deleteClinic(clinicId: number) {
        this.mediService.deleteClinic(clinicId).subscribe((data) => {
            this.clinics = this.clinics.filter((clinic)=> clinic.clinicId !== clinicId);
        })
    }

    editDoctor() {
        this.router.navigate(['/doctor-edit']);
    }

    cancelAppointment(appointment: Appointment) {
        const clinicId = appointment.clinic.clinicId;
        this.mediService.deleteAppointment(appointment.appointmentId).subscribe({
            next: ()=>{
                this.loadAppointments(clinicId);
            }
        })
    }

    loadPatientData() {
        this.mediService.getPatientById(this.patientId).subscribe({
            next: (data) => {
                this.patientDetails = data;
                console.log("Patient details: ", this.doctorDetails);
            },
            error: () => console.log("failed to find Patient details")
        })

        this.mediService.getAppointmentsByPatient(this.patientId).subscribe((data) => {
            console.log("appointments:", data);
            this.appointments = data;
        })

        this.mediService.getAllClinics().subscribe((data) => {
            this.clinics = data;
        })

        this.mediService.getAllDoctors().subscribe((data) => {
            this.doctors = data;
        })
    }

    navigateToEditPatient() {
        this.router.navigate(['/patient-edit', this.patientId, this.userId]);
    }

    deletePatient() {
        if (confirm("Are you sure, You want to delete")) {
            this.mediService.deletePatient(this.patientId)
        }
    }

    createClinic() {
        this.router.navigate(['/clinic-create'])
    }

    bookAppointment(clinicId: number){
        this.router.navigate(['/appointment', clinicId]);
    }
}
