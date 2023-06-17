import {Component, OnInit, ViewChild} from '@angular/core';
import { Curso } from "../../models/curso";
import { ActivatedRoute, Router } from "@angular/router";
import { CursoService } from "../../services/curso.service";
import { ExamenService } from "../../services/examen.service";
import { FormControl } from "@angular/forms";
import { Examen } from "../../models/examen";
import { flatMap, map } from "rxjs";
import { MatAutocompleteSelectedEvent } from "@angular/material/autocomplete";
import Swal, {SweetAlertResult} from "sweetalert2";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {Alumno} from "../../models/alumno";

@Component({
  selector: 'app-asignar-examenes',
  templateUrl: './asignar-examenes.component.html',
  styleUrls: ['./asignar-examenes.component.css']
})
export class AsignarExamenesComponent implements OnInit {

  curso : Curso;
  autocompleteControl = new FormControl();
  examenesFiltrados: Examen[] = [];
  examenesAsignar: Examen[] = [];
  mostrarColumnas: string[] = ['nombre', 'asignatura', 'eliminar'];
  examenes : Examen[] = [];
  tabIndex : number = 0;
  mostrarColumnasExamenes : string[] = ['id', 'nombre', 'asignaturas', 'eliminar'];
  pageSizeOptions : number[] = [3, 5, 10, 20, 50];
  dataSource : MatTableDataSource<Examen>;
  @ViewChild(MatPaginator, {static: true})  paginator : MatPaginator;

  constructor(private route : ActivatedRoute, router : Router, private cursoService : CursoService, private examenService : ExamenService) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id : number = +params.get('id');
      this.cursoService
        .ver(id).subscribe(c => {
        this.curso = c;
        this.examenes = this.curso.examenes;
        this.iniciarPaginador();
      });
    });

    this.autocompleteControl.valueChanges.pipe(
      map(valor => typeof valor === 'string' ? valor : valor.nombre),
      flatMap(valor => valor ? this.examenService.filtrarPorNombre(valor) : [])
    ).subscribe(examenes => this.examenesFiltrados = examenes);
  }

  public mostrarNombre(examen? : Examen) : string {
    return examen? examen.nombre : '';
  }

  public seleccionarExamen(event: MatAutocompleteSelectedEvent) {
    const examen = event.option.value as Examen;

    if (!this.existe(examen.id)) {
      this.examenesAsignar = this.examenesAsignar.concat(examen);
      console.log(this.examenesAsignar);
    } else {
      Swal.fire('Error', `El exámen ${examen.nombre} ya está asignado al curso`, 'error');
    }

    this.autocompleteControl.setValue('');
    event.option.deselect();
    event.option.focus();
  }

  private existe(id : number) : boolean {
    let existe = false;
    this.examenesAsignar.concat(this.examenes).forEach((e : Examen) => {
      if (id == e.id) {
        existe = true;
      }
    });

    return existe;
  }

  public eliminarDelAsignar(examen: Examen) : void {
    this.examenesAsignar = this.examenesAsignar.filter(e => examen.id !== e.id);
  }

  public asignar() : void {
    console.log(this.examenesAsignar);
    this.cursoService.asignarExamenes(this.curso, this.examenesAsignar).subscribe(curso => {
      this.examenes = this.examenes.concat(this.examenesAsignar);
      this.iniciarPaginador();
      this.examenesAsignar = [];
      Swal.fire('Asignados', `Exámenes asignados con éxito al curso ${curso.nombre}`, 'success');
      this.tabIndex = 2;
    });
  }

  public eliminarExamenDelCurso(examen : Examen) : void {
    Swal.fire({
      title: '¿Estás seguro?',
      text: `¿Seguro que desea eliminar a ${examen.nombre} del curso ${this.curso.nombre}?`,
      icon: 'warning',
      showCancelButton: true,
      cancelButtonColor: '#3085d6',
      confirmButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No'
    }).then((result : SweetAlertResult) => {
      if (!result.dismiss) {
        this.cursoService.eliminarExamen(this.curso, examen)
          .subscribe(curso => {
            this.examenes = this.examenes.filter(e => e.id !== examen.id);
            this.iniciarPaginador();
            Swal.fire('Eliminado', `Examen ${examen.nombre} eliminado con éxito del curso ${curso.nombre}`, 'success');
          });
      }
    });
  }

  private iniciarPaginador() : void {
    this.dataSource = new MatTableDataSource<Examen>(this.examenes);
    this.dataSource.paginator = this.paginator;
    this.paginator._intl.itemsPerPageLabel = 'Registros por Página';
  }
}
