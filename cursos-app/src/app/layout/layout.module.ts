import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './navbar/navbar.component';
import { RouterLink, RouterLinkActive } from "@angular/router";
import { AppRoutingModule } from "../app-routing.module";



@NgModule({
  declarations: [
    NavbarComponent
  ],
  exports: [
    NavbarComponent
  ],
  imports: [
    CommonModule,
    RouterLink,
    RouterLinkActive,
    AppRoutingModule
  ]
})
export class LayoutModule { }
