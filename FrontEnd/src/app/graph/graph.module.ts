import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GraphComponent } from './graph/graph.component';
import { LoggerService } from 'eds-angular4';
import { ChartModule } from 'eds-angular4/dist/charting';
import { GraphService } from "./graph.service";
import {FormsModule} from "@angular/forms";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
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
