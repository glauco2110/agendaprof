import { HttpHeaders, HttpParams } from '@angular/common/http';


export abstract class HTTPServicoBase {

  public static getHttpHeadersDefault(): HttpHeaders {
    const httpHeaders = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    return httpHeaders;
  }

  public static buildHttpHeaders(customHeaders?: HttpHeaders): HttpHeaders {
    let httpHeaders = HTTPServicoBase.getHttpHeadersDefault();

    if (customHeaders) {
      customHeaders.keys().forEach(key => {
        httpHeaders = httpHeaders.append(key, customHeaders.get(key) as string);
      });
    }

    return httpHeaders;
  }

  public static buildHttpParams(customParams?: HttpParams): HttpParams {
    let httpParams = new HttpParams();

    if (customParams) {
      customParams.keys().forEach(key => {
        httpParams = httpParams.append(key, customParams.get(key) as string);
      });
    }

    return httpParams;
  }

  public static getHttpOptions(options: {
    headers?: HttpHeaders,
    params?: HttpParams,
    responseType?: string,
    observe?: string
  } = {responseType: 'json', observe: 'body'}): {} {

    return {
      headers: HTTPServicoBase.buildHttpHeaders(options.headers),
      params: HTTPServicoBase.buildHttpParams(options.params),
      responseType: options.responseType,
      observe: options.observe
    };
  }

  protected abstract getPathEndPoint(): string;

  public getUrlEndPoint(): string {
    return this.getUrlContext() + '/' + this.getPathEndPoint();
  }

  protected getUrlContext(): string {
    return 'http://localhost:8080/api';
  }
}
