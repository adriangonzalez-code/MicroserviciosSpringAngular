import { Component, Inject, OnInit } from '@angular/core';
import { Curso } from "../../models/curso";
import { Alumno } from "../../models/alumno";
import { Examen } from "../../models/examen";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { Respuesta } from "../../models/respuesta";
import { Pregunta } from "../../models/pregunta";

@Component({
  selector: 'app-responder-examen-modal',
  templateUrl: './responder-examen-modal.component.html',
  styleUrls: ['./responder-examen-modal.component.css']
})
export class ResponderExamenModalComponent implements OnInit {

  curso: Curso;
  alumno: Alumno;
  examen: Examen;

  respuestas : Map<number, Respuesta> = new Map<number, Respuesta>();

  constructor(@Inject(MAT_DIALOG_DATA) public data : any, public modalRef : MatDialogRef<ResponderExamenModalComponent>) {
  }

  ngOnInit(): void {
    this.curso = this.data.curso as Curso;
    this.alumno = this.data.alumno as Alumno;
    this.examen = this.data.examen as Examen;
  }

  public cancelar() : void {
    this.modalRef.close();
  }

  public responder(pregunta: Pregunta, event : any) : void {
    const texto = event.target.value as string;
    const respuesta = new Respuesta();
    respuesta.alumno = this.alumno;
    respuesta.pregunta = pregunta;

    const examen = new Examen();
    examen.id = this.examen.id;
    examen.nombre = this.examen.nombre;

    respuesta.pregunta.examen = examen;
    respuesta.texto = texto;

    this.respuestas.set(pregunta.id, respuesta);
    console.log(this.respuestas);
  }
}
