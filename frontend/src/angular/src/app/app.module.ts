import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';

import { HttpJwtInterceptor, ErrorInterceptor } from './_helpers';
import { routing } from './app-routing.module';
import { HomeComponent } from './home';
import { LoginComponent } from './login';
import { NavbarComponent } from './navbar';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { AdminComponent } from './admin';

@NgModule({
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    routing
  ],
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    NavbarComponent,
    AdminComponent,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: HttpJwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})

export class AppModule { }
