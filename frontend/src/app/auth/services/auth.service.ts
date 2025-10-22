import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { map, Observable } from "rxjs";
import { User } from "../../mediconnect/models/User";
import { environment } from "src/environments/environment";
import { UserRegistrationDTO } from "src/app/mediconnect/models/UserRegistrationDTO";

interface LoginResponse {
  token: string;
  role: string;
  userId: string;
  patientId: string;
  doctorId: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private loginUrl = `${environment.apiUrl}`;
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    })
  };

  constructor(private httpClient: HttpClient) { }

  login(user: Partial<User>): Observable<{ [key: string]: string }> {
    return this.httpClient.post<{token: string}>(
      `${this.loginUrl}/user/login`,
      user,
      this.httpOptions
    );
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getRole(): string | null {
    return localStorage.getItem('role');
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('user_id');
    localStorage.removeItem('patient_id');
    localStorage.removeItem('doctor_id');
  }

  getUsers(): Observable<User[]>{
    return this.httpClient.get<User[]>(`${this.loginUrl}/user`);
  }

  createUser(user: UserRegistrationDTO): Observable<any> {
    // console.log(this.loginUrl);
    return this.httpClient.post<User>(`${this.loginUrl}/user/register`, user);
  }
  
}
