import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { User } from '../_models';
import { EncrDecrService } from './encr.decr.service';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;

  constructor(private http: HttpClient,
              private encrDecr: EncrDecrService) {
    this.currentUserSubject = new BehaviorSubject<User>(this.getUserFromStorage());
    this.currentUser = this.currentUserSubject.asObservable();
  }

  private static removeDataFromStorage(key: string): void {
    localStorage.removeItem(key);
  }

  private static removeUserFromStorage(): void {
    AuthService.removeDataFromStorage(`${config.LOGGED_USER}`);
  }

  private static removeTokenFromStorage(): void {
    AuthService.removeDataFromStorage(`${config.TOKEN_NAME}`);
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  public login(loginData: string) {
    return this.http.post<any>(`${config.BACKEND_URL}/api/auth/signin`, loginData)
      .pipe(map(res => {
        let user = new User();
        // login successful if there's a jwt token in the response
        if (res && res.token && res.user) {
          // parse received data
          user = res.user;
          user.token = res.token;
          // store user details and jwt token in local storage to keep user logged in between page refreshes
          this.addOrUpdateUserToStorage(user);
          this.currentUserSubject.next(user);
        }
        return user;
      }));
  }

  public logout(): void {
    AuthService.removeUserFromStorage();
    this.currentUserSubject.next(null);
  }

  public isLoggedIn(): boolean {
    return !!this.currentUserValue;
  }

  private addDataToStorage(key: string, value: any, cryptKey: string): void {
    localStorage.setItem(key, this.encrDecr.set(cryptKey, JSON.stringify(value)));
  }

  private addOrUpdateUserToStorage(user: User): void {
    AuthService.removeUserFromStorage();
    this.addDataToStorage(`${config.LOGGED_USER}`, user, `${config.SECRET_USER_KEY}`);
  }

  private getDataFromStorage(key: string, cryptKey: string): any {
    let valueFromStorage = null;
    try {
      valueFromStorage = JSON.parse(this.encrDecr.get(cryptKey, localStorage.getItem(key)));
    } catch (ex) {
      AuthService.removeUserFromStorage();
      AuthService.removeTokenFromStorage();
      console.log('Can not parse data, please log one more time');
    }
    return valueFromStorage;
  }

  private getUserFromStorage(): User {
    return this.getDataFromStorage(`${config.LOGGED_USER}`, `${config.SECRET_USER_KEY}`);
  }
}
