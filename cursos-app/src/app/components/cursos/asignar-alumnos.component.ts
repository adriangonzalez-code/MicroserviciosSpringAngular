import { Component, OnInit } from '@angular/core';
import { Curso } from "../../models/curso";
import { ActivatedRoute } from "@angular/router";
import { CursoService } from "../../services/curso.service";
import { AlumnoService } from "../../services/alumno.service";
import { Alumno } from "../../models/alumno";
import { SelectionModel } from "@angular/cdk/collections";
import Swal from "sweetalert2";

@Component({
  selector: 'app-asignar-alumnos',
  templateUrl: './asignar-alumnos.component.html',
  styleUrls: ['./asignar-alumnos.component.css']
})
export class AsignarAlumnosComponent implements OnInit {

  curso : Curso;
  alumnosAsignar : Alumno[] = [];
  mostrarColumnas : string[] = ['seleccion', 'nombre', 'apellido'];
  seleccion : SelectionModel<Alumno> = new SelectionModel<Alumno>(true, []);

  constructor(private route : ActivatedRoute, private cursoService : CursoService, private alumnoService : AlumnoService) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id : number = +params.get('id');
      this.cursoService.ver(id).subscribe(c => this.curso = c);
    });
  }

  public filtrar(nombre : string) : void {
    nombre = nombre !== undefined ? nombre.trim() : '';

    if (nombre !== '') {
      this.alumnoService.filtrarPorNombre(nombre).subscribe(alumnos => this.alumnosAsignar = alumnos);
    }
  }

  public estanTodosSeleccionados() : boolean {
    const seleccionados = this.seleccion.selected.length;
    const numAlumnos = this.alumnosAsignar.length;

    return (seleccionados === numAlumnos);
  }

  public seleccionarDeseleccionarTodos() : boolean | void {
    return this.estanTodosSeleccionados() ? this.seleccion.clear() : this.alumnosAsignar.forEach(a => this.seleccion.select(a));

  }

  public asignar() : void {
    console.log(this.seleccion.selected);
    this.cursoService.asignarAlumnos(this.curso, this.seleccion.selected).subscribe(c => {
      Swal.fire('Asignados', `Alumnos asignados con éxito al curso ${this.curso.nombre}`, 'success');
      this.alumnosAsignar = [];
      this.seleccion.clear();
    });
  }
}
