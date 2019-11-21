import { Routes, RouterModule } from '@angular/router';

import { AuthGuard } from './_guards';
import { HomeComponent } from './home';
import { LoginComponent } from './login';
import {AdminComponent} from './admin';

const appRoutes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'login',
    component: LoginComponent
  },

  // otherwise redirect to home
  { path: '**', redirectTo: '' }
];

export const routing = RouterModule.forRoot(appRoutes);
