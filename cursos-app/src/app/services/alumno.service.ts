import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import { Observable } from "rxjs";
import { Alumno } from "../models/alumno";

@Injectable({
  providedIn: 'root'
})
export class AlumnoService {

  private baseEndpoint: string = 'http://localhost:8090/api/alumnos';
  private cabeceras: HttpHeaders = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private httpClient : HttpClient) {

  }

  public listar() : Observable<Alumno[]> {
    return this.httpClient.get<Alumno[]>(this.baseEndpoint);
  }

  public listarPaginas(page : string, size : string) : Observable<any> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size);

    return this.httpClient.get<Alumno[]>(`${this.baseEndpoint}/paginas`, {params: params})
  }

  public ver(id: number) : Observable<Alumno> {
    return this.httpClient.get<Alumno>(`${this.baseEndpoint}/${id}`);
  }

  public crear(alumno : Alumno) : Observable<Alumno> {
    return this.httpClient.post<Alumno>(this.baseEndpoint, alumno, {headers: this.cabeceras});
  }

  public editar(alumno : Alumno) : Observable<Alumno> {
    return this.httpClient.put<Alumno>(`${this.baseEndpoint}/${alumno.id}`, alumno, {headers: this.cabeceras});
  }

  public eliminar(id : number) : Observable<void> {
    return this.httpClient.delete<void>(`${this.baseEndpoint}/${id}`);
  }
}