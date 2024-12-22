import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { StorageService } from './local-storage/local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class ServicoBaseProviderService {

  protected constructor(
    private http: HttpClient,
    private storage: StorageService
  ) {
  }

  public getHttp(): HttpClient {
    return this.http;
  }

  public getStorage(): StorageService {
    return this.storage;
  }
}
