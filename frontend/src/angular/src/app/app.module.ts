import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {
  MatButtonModule,
  MatFormFieldModule,
  MatListModule,
  MatMenuModule,
  MatSliderModule,
  MatSnackBarModule,
  MatToolbarModule,
  MatSidenavModule,
  MatIconModule,
  MatCardModule, MatInputModule
} from '@angular/material';
import { RouterModule } from '@angular/router';
import { AdminComponent } from './admin';
import { HomeComponent } from './home';
import { LoginComponent } from './login';
import { NavbarComponent } from './navbar';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { ErrorInterceptor, HttpJwtInterceptor } from './_helpers';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { routing } from './app-routing.module';
import { LayoutModule } from '@angular/cdk/layout';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FooterComponent } from './footer/footer.component';

@NgModule({
  declarations: [
    AppComponent,
    AdminComponent,
    HomeComponent,
    LoginComponent,
    NavbarComponent,
    FooterComponent,
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
    MatSnackBarModule,
    MatMenuModule,
    MatButtonModule,
    MatListModule,
    MatToolbarModule,
    LayoutModule,
    MatSidenavModule,
    MatIconModule,
    FlexLayoutModule,
    MatCardModule,
    MatInputModule,
    FormsModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: HttpJwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
