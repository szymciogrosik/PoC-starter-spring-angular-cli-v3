import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { User } from '../_models';
import {UserService} from './user.service';

@Injectable({ providedIn: 'root' })
export class AuthenticationService {
    private currentUserSubject: BehaviorSubject<User>;
    public currentUser: Observable<User>;

    constructor(private http: HttpClient,
                private userService: UserService) {
        this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem(`${config.TOKEN_NAME}`)));
        this.currentUser = this.currentUserSubject.asObservable();
    }

    public get currentUserValue(): User {
        return this.currentUserSubject.value;
    }

    login(loginData: string) {
        return this.http.post<any>(`${config.BACKEND_URL}/api/auth/signin`, loginData, this.getAuthHeader())
            .pipe(map(res => {
                let user = new User();
                // login successful if there's a jwt token in the response
                if (res && res.token && res.user) {
                  // store user details and jwt token in local storage to keep user logged in between page refreshes
                  user = res.user;
                  user.token = res.token;
                  console.log(user);
                  localStorage.setItem(`${config.TOKEN_NAME}`, JSON.stringify(user));
                  this.currentUserSubject.next(user);
                }

                return user;
            }));
    }

    logout() {
        // remove user from local storage to log user out
        localStorage.removeItem(`${config.TOKEN_NAME}`);
        this.currentUserSubject.next(null);
    }

    refresh() {
      console.log('refresh');
      return this.http.get(`${config.BACKEND_URL}/refresh`);
    }

    isLoggedIn(): boolean {
      if (this.userService.loggedUser) {
        return true;
      } else {
        return false;
      }
    }

    getAuthHeader() {
      const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
      return { headers };
    }
}
