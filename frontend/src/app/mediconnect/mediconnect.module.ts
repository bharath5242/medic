import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";

import { MediConnectRoutingModule } from "./mediconnect-routing.module";
import { ReactiveFormsModule } from "@angular/forms";
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from "@angular/common/http";
import { PatientCreateComponent } from "./components/patientcreate/patientcreate.component";
import { DoctorCreateComponent } from "./components/doctorcreate/doctorcreate.component";
import { ClinicCreateComponent } from "./components/cliniccreate/cliniccreate.component";
import { AppointmentCreateComponent } from "./components/appointment/appointment.component";
import { DashboardComponent } from "./components/dashboard/dashboard.component";
import { PatientEditComponent } from "./components/patientedit/patientedit.component";
import { ClinicEditComponent } from "./components/clinicedit/clinicedit.component";
import { SharedModule } from "../shared/shared.module";
import { RouterModule } from '@angular/router';
import { DoctorEditComponent } from "./components/doctoredit/doctoredit.component";
// import { AppRoutingModule } from "../app-routing.module";

@NgModule({
  declarations: [
    PatientCreateComponent,
    DoctorCreateComponent,
    ClinicCreateComponent,
    ClinicEditComponent,
    AppointmentCreateComponent,
    DashboardComponent,
    PatientEditComponent,
    DoctorEditComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule,
    MediConnectRoutingModule,
    SharedModule,
    RouterModule
  ],
  exports: [
  ]
})
export class MediconnectModule {}
