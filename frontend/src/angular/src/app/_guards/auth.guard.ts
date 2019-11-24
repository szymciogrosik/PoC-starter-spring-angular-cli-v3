import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthService } from '../_services';
import {RedirectionPath} from '../../utils/redirection.path';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
    constructor(
        private router: Router,
        private authenticationService: AuthService
    ) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        if (this.authenticationService.isLoggedIn()) {
            return true;
        }

        this.router.navigate([RedirectionPath.LOGIN], { queryParams: { returnUrl: state.url } });
        return false;
    }
}
