import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs";
import { Generic } from "../models/generic";

export abstract class CommonService<E extends Generic> {

  protected baseEndpoint: string;

  protected cabeceras: HttpHeaders = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private httpClient : HttpClient) {

  }

  public listar() : Observable<E[]> {
    return this.httpClient.get<E[]>(this.baseEndpoint);
  }

  public listarPaginas(page : string, size : string) : Observable<any> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size);

    return this.httpClient.get<E[]>(`${this.baseEndpoint}/pagina`, {params: params})
  }

  public ver(id: number) : Observable<E> {
    return this.httpClient.get<E>(`${this.baseEndpoint}/${id}`);
  }

  public crear(e : E) : Observable<E> {
    return this.httpClient.post<E>(this.baseEndpoint, e, {headers: this.cabeceras});
  }

  public editar(e : E) : Observable<E> {
    return this.httpClient.put<E>(`${this.baseEndpoint}/${e.id}`, e, {headers: this.cabeceras});
  }

  public eliminar(id : number) : Observable<void> {
    return this.httpClient.delete<void>(`${this.baseEndpoint}/${id}`);
  }
}
