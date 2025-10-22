// import { Injectable } from "@angular/core";
// import {
//   HttpInterceptor,
//   HttpRequest,
//   HttpHandler,
//   HttpEvent,
// } from "@angular/common/http";
// import { Observable } from "rxjs";
// import { AuthService } from "./auth/services/auth.service";

// @Injectable()
// export class AuthInterceptor implements HttpInterceptor {

// }


import { Injectable } from "@angular/core";
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent,
} from "@angular/common/http";
import { Observable } from "rxjs";
import { AuthService } from "./auth/services/auth.service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) { }

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const authToken = this.authService.getToken();

    if (authToken) {
      const authReq = request.clone({
        setHeaders: {
          Authorization: `Bearer ${authToken}`,
        },
      });
      // console.log("auth obj: ", authReq);
      return next.handle(authReq);
    }

    return next.handle(request);
  }
}