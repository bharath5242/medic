
// export class DoctorArrayComponent  {}
import { Component, OnInit } from '@angular/core';

interface Doctor {
  fullName: string;
  specialty: string;
  contactNumber: string;
  email: string;
  yearsOfExperience: number;
}

@Component({
  selector: 'app-doctor-array',
  templateUrl: './doctorarray.component.html',
  styleUrls: ['./doctorarray.component.scss']
})
export class DoctorArrayComponent implements OnInit {
  doctors: Doctor[] = [];
  showDetails: boolean = false;

  ngOnInit(): void {
    this.doctors = [
      {
        fullName: 'Dr. Jane Smith',
        specialty: 'Cardiology',
        contactNumber: '9876543210',
        email: 'jane.smith@example.com',
        yearsOfExperience: 15
      },
      {
        fullName: 'Dr. John Doe',
        specialty: 'Orthopedics', // ✅ Updated to match test expectation
        contactNumber: '9123456780',
        email: 'john.doe@example.com',
        yearsOfExperience: 12
      },
      {
        fullName: 'Dr. Emily Davis',
        specialty: 'Neurology',
        contactNumber: '9988776655',
        email: 'emily.davis@example.com',
        yearsOfExperience: 10
      }
    ];
  }

  toggleDetails(): void {
    this.showDetails = !this.showDetails;
  }
}