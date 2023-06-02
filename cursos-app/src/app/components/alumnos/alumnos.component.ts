import { Component, OnInit } from '@angular/core';
import {AlumnoService} from "../../services/alumno.service";
import {Alumno} from "../../models/alumno";
import Swal, {SweetAlertResult} from 'sweetalert2';

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
    this.service.listar().subscribe((alumnos : Alumno[]) => this.alumnos = alumnos);
  }

  public eliminar(alumno: Alumno) : void {

    Swal.fire({
      title: '¿Estás seguro?',
      text: `¿Seguro que desea eliminar a ${alumno.nombre}?`,
      icon: 'warning',
      showCancelButton: true,
      cancelButtonColor: '#3085d6',
      confirmButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No'
    }).then((result : SweetAlertResult) => {
      if (!result.dismiss) {
        this.service.eliminar(alumno.id).subscribe(() : void => {
          this.alumnos = this.alumnos.filter(a =>  a !== alumno);
          Swal.fire('Eliminado', `Alumno ${alumno.nombre} eliminado con éxito`, 'success');
        });
      }
    });
  }
}
