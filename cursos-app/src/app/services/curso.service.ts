import { Injectable } from '@angular/core';
import { CommonService } from "./common.service";
import { HttpClient } from "@angular/common/http";
import { Curso } from "../models/curso";
import { BASE_ENDPOINT } from "../config/app";

@Injectable({
  providedIn: 'root'
})
export class CursoService extends CommonService<Curso> {

  protected override baseEndpoint : string = BASE_ENDPOINT + '/cursos';

  constructor(private http: HttpClient) {
    super(http);
  }


}
