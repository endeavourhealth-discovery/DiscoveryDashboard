import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormsModule} from '@angular/forms';
import { DashboardComponent } from './dashboard/dashboard.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {DashboardService} from "./dashboard.service";
import { LoggerService } from 'eds-angular4';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    NgbModule
  ],
  declarations: [DashboardComponent],
  providers: [
  DashboardService,
  LoggerService]
})
export class DashboardModule { }
