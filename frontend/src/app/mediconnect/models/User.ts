import { Clinic } from "./Clinic";
import { Doctor } from "./Doctor";
import { Patient } from "./Patient";

export class User {
    userId: number;
    username: string;
    password: string;
    role: string;
    clinic?: Clinic;
    doctor?: Doctor;
    constructor(userId: number, username: string, password: string, role: string, doctor?: Doctor, clinic?: Clinic) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.clinic = clinic;
        this.doctor = doctor;
    }
    // logAttributes() {

    // }
}

// export interface User{
//     userId: number;
//     username: string;
//     password: string;
//     role: string;
//     clinic?: Clinic;
//     doctor?: Doctor;
// }