import { Injectable } from '@angular/core';
import * as CryptoJS from 'crypto-js';

@Injectable({
  providedIn: 'root'
})

export class EncrDecrService {

  constructor() { }

  private static getOptions(iv) {
    return {
      keySize: 128 / 8,
      iv,
      mode: CryptoJS.mode.CBC,
      padding: CryptoJS.pad.Pkcs7
    };
  }

  // The set method is use for encrypt the value.
  public set(secretKey: string, value: string): string {
    const key = CryptoJS.enc.Utf8.parse(secretKey);
    const iv = CryptoJS.enc.Utf8.parse(secretKey);
    const encrypted = CryptoJS.AES.encrypt(CryptoJS.enc.Utf8.parse(value.toString()), key, EncrDecrService.getOptions(iv));

    return encrypted.toString();
  }

  // The get method is use for decrypt the value.
  public get(secretKey: string, value: string): string {
    const key = CryptoJS.enc.Utf8.parse(secretKey);
    const iv = CryptoJS.enc.Utf8.parse(secretKey);
    const decrypted = CryptoJS.AES.decrypt(value, key, EncrDecrService.getOptions(iv));

    return decrypted.toString(CryptoJS.enc.Utf8);
  }
}
