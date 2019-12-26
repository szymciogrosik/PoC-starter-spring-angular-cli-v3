import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material';

@Injectable({
  providedIn: 'root'
})
export class SnackbarService {

  static SHORT_DURATION = 5000;
  static MEDIUM_DURATION = 7500;
  static LONG_DURATION = 10000;

  static DISMISS_ACTION = 'Dismiss';

  constructor(private snackBar: MatSnackBar) {}

  public openCustomSnackBar(message: string, action: string, duration: number): void {
    this.snackBar.open(message, action, { duration });
  }

  public openDefaultSnackBar(message: string): void {
    this.snackBar.open(message, SnackbarService.DISMISS_ACTION, {
      duration: SnackbarService.MEDIUM_DURATION
    });
  }

}
