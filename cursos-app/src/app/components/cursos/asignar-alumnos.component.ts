import {Component, OnInit, ViewChild} from '@angular/core';
import { Curso } from "../../models/curso";
import { ActivatedRoute } from "@angular/router";
import { CursoService } from "../../services/curso.service";
import { AlumnoService } from "../../services/alumno.service";
import { Alumno } from "../../models/alumno";
import { SelectionModel } from "@angular/cdk/collections";
import Swal, {SweetAlertResult} from "sweetalert2";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";

@Component({
  selector: 'app-asignar-alumnos',
  templateUrl: './asignar-alumnos.component.html',
  styleUrls: ['./asignar-alumnos.component.css']
})
export class AsignarAlumnosComponent implements OnInit {

  curso : Curso;
  alumnosAsignar : Alumno[] = [];
  mostrarColumnas : string[] = ['seleccion', 'nombre', 'apellido'];
  mostrarColumnasAlumnos : string[] = ['id', 'nombre', 'apellido', 'email', 'eliminar'];
  seleccion : SelectionModel<Alumno> = new SelectionModel<Alumno>(true, []);
  alumnos : Alumno[] = [];
  tabIndex : number = 0;
  dataSource: MatTableDataSource<Alumno>;
  @ViewChild(MatPaginator, {static : true}) paginator : MatPaginator;
  pageSizeOptions : number[] = [3, 5, 10, 20, 50];

  constructor(private route : ActivatedRoute, private cursoService : CursoService, private alumnoService : AlumnoService) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id : number = +params.get('id');
      this.cursoService.ver(id).subscribe(c => {
        this.curso = c;
        this.alumnos = this.curso.alumnos;
        this.iniciarPaginador();
      });
    });
  }

  public filtrar(nombre : string) : void {
    nombre = nombre !== undefined ? nombre.trim() : '';

    if (nombre !== '') {
      this.alumnoService.filtrarPorNombre(nombre).subscribe(alumnos => this.alumnosAsignar = alumnos.filter(a => {
        let filtrar : boolean = true;
        this.alumnos.forEach(ca => {
          if (a.id === ca.id) {
            filtrar = false;
          }
        });

        return filtrar;
      }));
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
      this.tabIndex = 2;
      Swal.fire('Asignados', `Alumnos asignados con éxito al curso ${this.curso.nombre}`, 'success');
      this.alumnos = this.alumnos.concat(this.seleccion.selected);
      this.iniciarPaginador();
      this.alumnosAsignar = [];
      this.seleccion.clear();

    }, e => {
      if (e.status === 500) {
        const mensaje = e.error.message as string;
        if (mensaje.indexOf('ConstraintViolationException') > -1) {
          Swal.fire('Cuidado', 'No se puede asignar, el alumno ya está asociado a otro curso', 'error');
        }
      }
    });
  }

  public eliminarAlumno(alumno: Alumno) : void {
    Swal.fire({
      title: '¿Estás seguro?',
      text: `¿Seguro que desea eliminar a ${alumno.nombre} del curso ${this.curso.nombre}?`,
      icon: 'warning',
      showCancelButton: true,
      cancelButtonColor: '#3085d6',
      confirmButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No'
    }).then((result : SweetAlertResult) => {
      if (!result.dismiss) {
        this.cursoService.eliminarAlumno(this.curso, alumno)
          .subscribe(curso => {
            this.alumnos = this.alumnos.filter(a => a.id !== alumno.id);
            this.iniciarPaginador();
            Swal.fire('Eliminado', `Alumno ${alumno.nombre} eliminado con éxito del curso ${curso.nombre}`, 'success');
          });
      }
    });
  }

  private iniciarPaginador() : void {
    this.dataSource = new MatTableDataSource<Alumno>(this.alumnos);
    this.dataSource.paginator = this.paginator;
    this.paginator._intl.itemsPerPageLabel = 'Registros por Página';
  }
}
