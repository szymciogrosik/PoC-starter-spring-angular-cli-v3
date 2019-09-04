import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../_models';
import {Subject} from 'rxjs';

@Injectable({ providedIn: 'root' })
export class UserService {

    loggedUser: User = new User();
    loggedUserSubject = new Subject<User>();

    constructor(private http: HttpClient) { }

    getAll() {
        return this.http.get<User[]>(`${config.BACKEND_URL}/users`);
    }

    setLoggedUser(user: User) {
      this.loggedUser = user;
      this.loggedUserSubject.next(user);
    }

    userHasRoleAdmin(): boolean {
      return (this.loggedUser.roles.find(role => role.name === 'ROLE_ADMIN') != null);
    }

    doRegister(newUser: string) {
      return this.http.post(`${config.BACKEND_URL}/user/create`, newUser);
    }

    doUpdateAccount(changedUser: string) {
      return this.http.put(`${config.BACKEND_URL}/user/update`, changedUser);
    }

    doUpdatePassword(changedUser: string) {
      return this.http.put(`${config.BACKEND_URL}/user/update/password`, changedUser);
    }

    getAllUsers(userFilter: string, pageNumber: string) {
      return this.http.post(`${config.BACKEND_URL}/user/getAllFilteredOnPage/?pageNumber=` + pageNumber, userFilter);
    }

    setActiveOrBanned(userId: string) {
      return this.http.put(`${config.BACKEND_URL}/user/` + userId + '/ban', '');
    }
}
