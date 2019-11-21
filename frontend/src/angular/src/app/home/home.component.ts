import { Component, OnInit } from '@angular/core';
import { User } from '../_models';
import { first } from 'rxjs/operators';
import {AuthenticationService, UserService} from '../_services';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})

export class HomeComponent implements OnInit {

  constructor() { }

  ngOnInit() {

  }

}

