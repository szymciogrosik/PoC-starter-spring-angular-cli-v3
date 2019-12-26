import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatFormFieldModule, MatSliderModule, MatSnackBarModule} from '@angular/material';
import { RouterModule } from '@angular/router';
import { AdminComponent } from './admin';
import { HomeComponent } from './home';
import { LoginComponent } from './login';
import { NavbarComponent } from './navbar';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { ErrorInterceptor, HttpJwtInterceptor } from './_helpers';
import { ReactiveFormsModule } from '@angular/forms';
import { routing } from './app-routing.module';

@NgModule({
  declarations: [
    AppComponent,
    AdminComponent,
    HomeComponent,
    LoginComponent,
    NavbarComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatSliderModule,
    RouterModule,
    ReactiveFormsModule,
    HttpClientModule,
    routing,
    MatFormFieldModule,
    MatSnackBarModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: HttpJwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
