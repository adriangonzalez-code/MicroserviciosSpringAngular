import { Component } from '@angular/core';
import { CommonFormComponent } from "../common-form.component";
import { Examen } from "../../models/examen";
import { ExamenService } from "../../services/examen.service";
import { ActivatedRoute, Router } from "@angular/router";
import { Asignatura } from "../../models/asignatura";
import { Pregunta } from "../../models/pregunta";

@Component({
  selector: 'app-examen-form',
  templateUrl: './examen-form.component.html',
  styleUrls: ['./examen-form.component.css']
})
export class ExamenFormComponent extends CommonFormComponent<Examen, ExamenService> {

  asignaturasPadre: Asignatura[] = [];
  asignaturasHija: Asignatura[] = [];

  constructor(service: ExamenService, router: Router, route: ActivatedRoute) {
    super(service, router, route);
    this.titulo = 'Crear Exámen';
    this.model = new Examen();
    this.nombreModel = Examen.name;
    this.redirect = '/examenes';
  }

  override ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id: number = +params.get('id');
      if (id) {
        this.service.ver(id).subscribe(model => {
          this.model = model;
          this.titulo = 'Editar ' + this.nombreModel;
          this.cargarHijos();
        });
      }
    });

    this.service.findAllAsignatura().subscribe((asignaturas : Asignatura[]) => {
      this.asignaturasPadre = asignaturas.filter(a => !a.padre);
    });
  }

  public cargarHijos() : void {
    this.asignaturasHija = this.model.asignaturaPadre ? this.model.asignaturaPadre.hijos : [];
  }

  public compararAsignatura(a1 : Asignatura, a2 : Asignatura) : boolean {
    if (a1 === undefined && a2 === undefined) {
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined) ? false : a1.id === a2.id;
  }

  public agregarPregunta() : void {
    this.model.preguntas.push(new Pregunta());
  }

  public asignarTexto(pregunta: Pregunta, event: any) : void {
    pregunta.texto = event.target.value as string;
    console.log(this.model);
  }

  public eliminarPregunta(pregunta: Pregunta) : void {
    this.model.preguntas = this.model.preguntas.filter(p => pregunta.texto !== p.texto);
  }
}
