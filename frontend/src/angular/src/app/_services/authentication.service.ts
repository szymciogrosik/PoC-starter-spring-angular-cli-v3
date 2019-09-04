import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
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
        return this.http.post<any>(`${config.BACKEND_URL}/api/auth`, loginData)
            .pipe(map(res => {
                const user = new User();
                // login successful if there's a jwt token in the response
                if (res && res.token) {
                  // store user details and jwt token in local storage to keep user logged in between page refreshes
                  user.username = res.username;
                  user.token = res.token;
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
}
