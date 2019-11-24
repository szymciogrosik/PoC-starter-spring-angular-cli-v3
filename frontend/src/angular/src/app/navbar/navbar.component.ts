import { Component, OnInit } from '@angular/core';
import { User } from '../_models';
import { Router } from '@angular/router';
import { AuthService } from '../_services';
import {RedirectionPath} from '../../utils/redirection.path';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})

export class NavbarComponent implements OnInit {
  private currentUser: User;
  private isCollapse: boolean;

  private rp = RedirectionPath;

  constructor(
    private router: Router,
    private authenticationService: AuthService
  ) {
    this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
  }

  ngOnInit() {
    this.isCollapse = true;
  }

  logout() {
    this.authenticationService.logout();
    this.router.navigate(['/']);
  }
}
