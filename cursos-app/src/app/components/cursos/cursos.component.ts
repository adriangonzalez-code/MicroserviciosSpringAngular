import { Component, OnInit } from '@angular/core';
import { CommonListarComponent } from "../common-listar.component";
import { Curso } from "../../models/curso";
import { CursoService } from "../../services/curso.service";

@Component({
  selector: 'app-cursos',
  templateUrl: './cursos.component.html',
  styleUrls: ['./cursos.component.css']
})
export class CursosComponent extends CommonListarComponent<Curso, CursoService> {

  constructor(service : CursoService) {
    super(service);
    this.titulo = 'Listado de Cursos';
    this.nombreModel = Curso.name;
  }
}
