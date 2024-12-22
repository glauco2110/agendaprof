import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {HTTPServicoBase} from './http.servico.base';
import {StorageService} from './local-storage/local-storage.service';
import {ServicoBaseProviderService} from "./servico-base.provider.service";

export interface ApiResponse<T> {
  items: T[];
  total_count: number;
}

export interface FiltroListagem {
  atributo: string,
  valor?: string,
}

export abstract class ServicoBase<T> extends HTTPServicoBase {
  protected http!: HttpClient;
  protected storage!: StorageService;


  protected constructor(servicoBaseProvider: ServicoBaseProviderService) {
    super();
    this.http = servicoBaseProvider.getHttp();
    this.storage = servicoBaseProvider.getStorage();
  }

  formatarErros(error: any) {
    return throwError(error.error);
  }

  salvar(entidade: T): Observable<string> {
    return this.http.post<string>(this.getUrlEndPoint(), entidade, HTTPServicoBase.getHttpOptions())
      .pipe(catchError(this.formatarErros));
  }

  alterar(entidade: T): Observable<string> {
    // @ts-ignore
    return this.http.put<string>(`${this.getUrlEndPoint()}/${entidade.id}`, entidade, HTTPServicoBase.getHttpOptions())
      .pipe(catchError(this.formatarErros));
  }

  consultarEntidade(id: string, params?: HttpParams): Observable<T> {
    return this.http.get<T>(this.getUrlEndPoint() + '/' + id, HTTPServicoBase.getHttpOptions({params: params}))
      .pipe(catchError(this.formatarErros));
  }

  consultar(id: string, params?: HttpParams): Observable<T> {
    const result: Observable<T> = this.http.get<T>(this.getUrlEndPoint() + '/' + id, HTTPServicoBase.getHttpOptions({params: params}))
    return result.pipe(catchError(this.formatarErros));

  }

  consultarTodos(params?: HttpParams): Observable<ApiResponse<T>> {
    return this.http.get<ApiResponse<T>>(this.getUrlEndPoint(), HTTPServicoBase.getHttpOptions({params: params}))
     .pipe(catchError(this.formatarErros));
  }

  excluir(id: string): Observable<void> {
    return this.http.delete<void>(this.getUrlEndPoint() + '/' + id, HTTPServicoBase.getHttpOptions())
      .pipe(catchError(this.formatarErros));
  }

  listar(filtro?: FiltroListagem): Observable<T[]> {
    let url = this.getUrlEndPoint() + '/listar'
    if(filtro?.valor != null && filtro?.atributo != null) {
      url = `${url}?${filtro.atributo}=${filtro.valor}`;
    }
    return this.http.get<T[]>(url, HTTPServicoBase.getHttpOptions())
      .pipe(catchError(this.formatarErros));
  }

}
