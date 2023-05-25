import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AlumnoService } from 'src/app/services/alumno.service';
import { Alumno } from "../../models/alumno";

@Component({
  selector: 'app-alumnos-form',
  templateUrl: './alumnos-form.component.html',
  styleUrls: ['./alumnos-form.component.css']
})
export class AlumnosFormComponent implements OnInit {

  titulo: string = 'Crear Alumno';
  alumno: Alumno = new Alumno();
  error: any;

  constructor(private service: AlumnoService, private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id: number = +params.get('id');
      if (id) {
        this.service.ver(id).subscribe(alumno => this.alumno = alumno);
      }
    })
  }

  public crear() : void {
    this.service.crear(this.alumno).subscribe(alumno => {
      console.log(alumno);
      alert(`Alumno ${alumno.nombre} creado con éxito`);
      this.router.navigate(['/alumnos']);
    }, err => {
      if (err.status === 400) {
        this.error = err.error;
      }
    });
  }

  public editar() : void {
    this.service.editar(this.alumno).subscribe(alumno => {
      console.log(alumno);
      alert(`Alumno ${alumno.nombre} actualizado con éxito`);
      this.router.navigate(['/alumnos']);
    }, err => {
      if (err.status === 400) {
        this.error = err.error;
      }
    });
  }
}
