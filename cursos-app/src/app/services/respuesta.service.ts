import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Respuesta } from "../models/respuesta";
import { Observable } from "rxjs";
import { BASE_ENDPOINT } from "../config/app";
import { Alumno } from "../models/alumno";
import { Examen } from "../models/examen";

@Injectable({
  providedIn: 'root'
})
export class RespuestaService {

  private cabeceras : HttpHeaders = new HttpHeaders({'Content-Type': 'application/json'});
  private baseEndpoint : string = BASE_ENDPOINT + '/respuestas';

  constructor(private httpClient : HttpClient) {}

  public crear(respuestas : Respuesta[]) : Observable<Respuesta[]> {
    return this.httpClient.post<Respuesta[]>(this.baseEndpoint, respuestas, {headers : this.cabeceras});
  }

  public obtenerRespuestasPorAlumnoPorExamen(alumno : Alumno, examen : Examen) : Observable<Respuesta[]> {
    return this.httpClient.get<Respuesta[]>(`${this.baseEndpoint}/alumno/${alumno.id}/examen/${examen.id}`);
  }
}
