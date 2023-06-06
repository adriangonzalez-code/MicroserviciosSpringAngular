import { Injectable } from '@angular/core';
import { CommonService } from "./common.service";
import { HttpClient } from "@angular/common/http";
import { Examen } from "../models/examen";

@Injectable({
  providedIn: 'root'
})
export class ExamenService extends CommonService<Examen> {

  protected override baseEndpoint : string = 'http://localhost:8090/api/examenes';

  constructor(http: HttpClient) {
    super(http);
  }
}
