import { Component } from '@angular/core';
import { User } from './_models';

@Component({
  selector: 'app-component',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.css'] })
export class AppComponent {
  currentUser: User;

  constructor() { }
}
