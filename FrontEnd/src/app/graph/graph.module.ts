import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GraphComponent } from './graph/graph.component';
import { LoggerService, DialogsModule, ChartModule } from 'eds-angular4';
import {GraphService} from "./graph.service";

@NgModule({
  imports: [
    CommonModule,
    ChartModule
  ],
  declarations: [
    GraphComponent
  ],
  providers: [
    GraphService,
    LoggerService
  ]
})
export class GraphModule { }
