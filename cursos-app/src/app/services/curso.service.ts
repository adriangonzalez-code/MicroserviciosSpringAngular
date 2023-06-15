import { Injectable } from '@angular/core';
import { CommonService } from "./common.service";
import { HttpClient } from "@angular/common/http";
import { Curso } from "../models/curso";
import { BASE_ENDPOINT } from "../config/app";
import { Alumno } from "../models/alumno";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CursoService extends CommonService<Curso> {

  protected override baseEndpoint : string = BASE_ENDPOINT + '/cursos';

  constructor(private http: HttpClient) {
    super(http);
  }

  public asignarAlumnos(curso : Curso, alumnos : Alumno[]) : Observable<Curso> {
    return this.http.put<Curso>(`${this.baseEndpoint}/${curso.id}/asignar-alumnos`, alumnos, {headers : this.cabeceras});
  }

  public eliminarAlumno(curso : Curso, alumno : Alumno) : Observable<Curso> {
    return this.http.put<Curso>(`${this.baseEndpoint}/${curso.id}/eliminar-alumno`, alumno, {'headers' : this.cabeceras});
  }
}
