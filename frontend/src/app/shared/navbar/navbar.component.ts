import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";

@Component({
    selector: 'navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['./navbar.component.scss'],
})
export class NavBarComponent {
    role: string | null = localStorage.getItem('role');
    isAuthenticated: boolean = localStorage.getItem('token') ? true : false;

    constructor(private router: Router){}

    // ngOnInit(): void {
    //     this.isAuthenticated = localStorage.getItem('token') ? true : false;
    //     this.role = localStorage.getItem('role');
    // }

    logout() {
        localStorage.removeItem('token');
        localStorage.removeItem('role');
        localStorage.removeItem('user_id');
        localStorage.removeItem('patient_id');
        localStorage.removeItem('doctor_id');
        this.router.navigate(['/login']);
      }
}
