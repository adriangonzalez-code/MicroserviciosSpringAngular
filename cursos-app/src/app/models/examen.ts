import { Asignatura } from "./asignatura";
import { Pregunta } from "./pregunta";
import { Generic } from "./generic";

export class Examen implements Generic {

  id: number;
  nombre: string;
  createAt: string;
  preguntas: Pregunta[] = [];
  asignatura: Asignatura;
  respondido: boolean;
}
