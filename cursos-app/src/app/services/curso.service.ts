import { Injectable } from '@angular/core';
import {CommonService} from "./common.service";
import {HttpClient} from "@angular/common/http";
import { Curso } from "../models/curso";

@Injectable({
  providedIn: 'root'
})
export class CursoService extends CommonService<Curso> {

  protected override baseEndpoint : string = 'http://localhost:8090/api/cursos';

  constructor(http: HttpClient) {
    super(http);
  }
}
