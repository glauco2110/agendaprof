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

export interface DTO {}

export abstract class ServicoBase extends HTTPServicoBase {
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

  salvar(entidade: DTO): Observable<string> {
    return this.http.post<string>(this.getUrlEndPoint(), entidade, HTTPServicoBase.getHttpOptions())
      .pipe(catchError(this.formatarErros));
  }

  alterar(entidade: DTO): Observable<string> {
    // @ts-ignore
    return this.http.put<string>(`${this.getUrlEndPoint()}/${entidade.id}`, entidade, HTTPServicoBase.getHttpOptions())
      .pipe(catchError(this.formatarErros));
  }

  consultarEntidade(id: string, params?: HttpParams): Observable<DTO> {
    return this.http.get<DTO>(this.getUrlEndPoint() + '/' + id, HTTPServicoBase.getHttpOptions({params: params}))
      .pipe(catchError(this.formatarErros));
  }

  consultar(id: string, params?: HttpParams): Observable<DTO> {
    const result: Observable<DTO> = this.http.get<DTO>(this.getUrlEndPoint() + '/' + id, HTTPServicoBase.getHttpOptions({params: params}))
    return result.pipe(catchError(this.formatarErros));

  }

  consultarTodos(params?: HttpParams): Observable<ApiResponse<DTO>> {
    return this.http.get<ApiResponse<DTO>>(this.getUrlEndPoint(), HTTPServicoBase.getHttpOptions({params: params}))
     .pipe(catchError(this.formatarErros));
  }

  excluir(id: string): Observable<void> {
    return this.http.delete<void>(this.getUrlEndPoint() + '/' + id, HTTPServicoBase.getHttpOptions())
      .pipe(catchError(this.formatarErros));
  }

  listar(filtro?: FiltroListagem): Observable<DTO[]> {
    let url = this.getUrlEndPoint() + '/listar'
    if(filtro?.valor != null && filtro?.atributo != null) {
      url = `${url}?${filtro.atributo}=${filtro.valor}`;
    }
    return this.http.get<DTO[]>(url, HTTPServicoBase.getHttpOptions())
      .pipe(catchError(this.formatarErros));
  }

}
