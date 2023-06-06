import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AlumnoService } from 'src/app/services/alumno.service';
import { Alumno } from "../../models/alumno";
import {CommonFormComponent} from "../common-form.component";

@Component({
  selector: 'app-alumnos-form',
  templateUrl: './alumnos-form.component.html',
  styleUrls: ['./alumnos-form.component.css']
})
export class AlumnosFormComponent extends CommonFormComponent<Alumno, AlumnoService> implements OnInit {

  constructor(service: AlumnoService, router: Router, route: ActivatedRoute) {
    super(service, router, route);
    this.titulo = 'Crear Alumno';
    this.model = new Alumno();
    this.nombreModel = Alumno.name;
    this.redirect = '/alumnos';
  }
}
