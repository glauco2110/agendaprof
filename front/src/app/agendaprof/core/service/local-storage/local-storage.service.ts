import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  private localStorage: Storage;
  
  constructor() { 
    this.localStorage = window.localStorage;
  }

  set(key: string, value: any) {
    this.localStorage.setItem(key, JSON.stringify(value));
  }

  get(key: string): any {
    const value = this.localStorage.getItem(key);
    
    if (value) {
      return JSON.parse(value);
    }

    return null;
  }

  remove(key: string): boolean {
    if (this.localStorage) {
      this.localStorage.removeItem(key);
      return true;
    }
    return false;
  }

  clear(): boolean {
    if (this.localStorage) {
      this.localStorage.clear();
      return true;
    }
    return false;
  }
}
