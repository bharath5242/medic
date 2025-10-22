import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { PatientCreateComponent } from '../mediconnect/components/patientcreate/patientcreate.component';
import { AppointmentCreateComponent } from "./components/appointment/appointment.component";
import { ClinicCreateComponent } from "./components/cliniccreate/cliniccreate.component";
import { ClinicEditComponent } from "./components/clinicedit/clinicedit.component";
import { DashboardComponent } from "./components/dashboard/dashboard.component";
import { DoctorCreateComponent } from "./components/doctorcreate/doctorcreate.component";
import { DoctorEditComponent } from "./components/doctoredit/doctoredit.component";
// import { PatientCreateComponent } from "../mediconnect/components/patientcreate/patientcreate.component";
import { PatientEditComponent } from "./components/patientedit/patientedit.component";
const routes: Routes = [
  { path:'patient-create', component: PatientCreateComponent},
  {path: 'dashboard', component: DashboardComponent},
  {path: 'patient-edit/:patientId/:userId', component: PatientEditComponent},
  {path: 'clinic-create', component: ClinicCreateComponent},
  {path: 'clinic-edit/:clinicId', component: ClinicEditComponent},
  {path: 'doctor-create', component: DoctorCreateComponent},
  {path: 'doctor-edit', component: DoctorEditComponent},
  {path: 'appointment/:clinicId', component: AppointmentCreateComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MediConnectRoutingModule {}
