import { Patient } from "../../models/Patient";
import { Component } from "@angular/core";

@Component({
    selector: "",
    templateUrl: "./patientsample.component.html",
    styleUrls: ["./patientsample.component.css"]
})

export class PatientSampleComponent {
    patient: Patient;

    logPatientAttributes() {
        console.log('patientId:', this.patient.patientId);
        console.log('fullName:', this.patient.fullName);
        console.log('dateOfBirth:', this.patient.dateOfBirth);
        console.log('contactNumber:', this.patient.contactNumber);
        console.log('email:', this.patient.email);
        console.log('address:', this.patient.address);
    }
}
