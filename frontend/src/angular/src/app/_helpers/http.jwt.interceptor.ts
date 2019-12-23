import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthService} from '../_services';

@Injectable()
export class HttpJwtInterceptor implements HttpInterceptor {
    constructor(private authenticationService: AuthService) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // add authorization header with jwt token if available
        const currentUser = this.authenticationService.currentUserValue;

        const headers = {
            'Content-Type': 'application/json; charset=utf-8',
            Accept: 'application/json',
            Authorization: ''
        };

        if (currentUser && currentUser.token) {
            headers.Authorization = `Bearer ${currentUser.token}`;
        }

        request = request.clone({
          setHeaders: headers
        });
        return next.handle(request);
    }
}
