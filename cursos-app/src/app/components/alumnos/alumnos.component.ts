import { Component, OnInit } from '@angular/core';
import {AlumnoService} from "../../services/alumno.service";
import {Alumno} from "../../models/alumno";

@Component({
  selector: 'app-alumnos',
  templateUrl: './alumnos.component.html',
  styleUrls: ['./alumnos.component.css']
})
export class AlumnosComponent implements OnInit {

  titulo: string = 'Listado de Alumnos';
  alumnos: Alumno[];

  constructor(private service: AlumnoService ) {
  }

  ngOnInit(): void {
    this.service.listar().subscribe(alumnos => this.alumnos = alumnos);
  }
}
