import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormsModule} from '@angular/forms';
import { DashboardComponent } from './dashboard/dashboard.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {DashboardService} from "./dashboard.service";
import { LoggerService } from 'eds-angular4';
import {NgxPaginationModule} from 'ngx-pagination';
import { PayloadViewerComponent } from './payload-viewer/payload-viewer.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    NgbModule,
    NgxPaginationModule
  ],
  declarations: [DashboardComponent, PayloadViewerComponent],
  entryComponents: [PayloadViewerComponent],
  providers: [
  DashboardService,
  LoggerService]
})
export class DashboardModule { }
