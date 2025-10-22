import { Router } from "@angular/router";
import { AuthService } from "../../services/auth.service";

export class LogoutComponent {
    constructor(private authService: AuthService, private router: Router){}

    logout(){
        this.authService.logout();
        this.router.navigate(['/auth']);
    }
}
