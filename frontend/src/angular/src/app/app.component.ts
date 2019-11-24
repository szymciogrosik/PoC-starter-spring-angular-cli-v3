import { Component } from '@angular/core';
import { User } from './_models';
import {RedirectionPath} from '../utils/redirection.path';

@Component({
  selector: 'app-component',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.css'] })
export class AppComponent {
  currentUser: User;
  constructor() { }
}
