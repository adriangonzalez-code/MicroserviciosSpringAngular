import { Directive, OnInit, ViewChild } from '@angular/core';
import Swal, { SweetAlertResult } from 'sweetalert2';
import { MatPaginator, PageEvent } from "@angular/material/paginator";
import { Generic } from "../models/generic";
import { CommonService } from "../services/common.service";

@Directive()
export abstract class CommonListarComponent<E extends Generic, S extends CommonService<E>> implements OnInit {

  titulo: string;
  lista: E[];
  protected nombreModel: string;

  totalRegistros: number = 0;
  paginaActual: number = 0;
  totalPorPagina: number = 4;
  pageSizeOptions: number[] = [3, 5, 10, 25, 100];

  @ViewChild(MatPaginator) paginator;

  constructor(protected service: S) {
  }

  ngOnInit(): void {
    this.calcularRangos();
  }

  public eliminar(e: E) : void {

    Swal.fire({
      title: '¿Estás seguro?',
      text: `¿Seguro que desea eliminar a ${e.nombre}?`,
      icon: 'warning',
      showCancelButton: true,
      cancelButtonColor: '#3085d6',
      confirmButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No'
    }).then((result : SweetAlertResult) => {
      if (!result.dismiss) {
        this.service.eliminar(e.id).subscribe(() : void => {
          this.calcularRangos();
          Swal.fire('Eliminado', `${this.nombreModel} ${e.nombre} eliminado con éxito`, 'success');
        });
      }
    });
  }

  public paginar(event: PageEvent) : void {
    this.paginaActual = event.pageIndex;
    this.totalPorPagina = event.pageSize;
    this.calcularRangos();
  }

  private calcularRangos() : void {
    this.service.listarPaginas(this.paginaActual.toString(), this.totalPorPagina.toString())
      .subscribe(p => {
        this.lista = p.content as E[];
        this.totalRegistros = p.totalElements as number;
        this.paginator._intl.itemsPerPageLabel = 'Registros por página';
      });
  }
}
