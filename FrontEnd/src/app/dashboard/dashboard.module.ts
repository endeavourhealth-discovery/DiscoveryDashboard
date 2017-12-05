import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormsModule} from '@angular/forms';
import { DashboardComponent } from './dashboard/dashboard.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {DashboardService} from './dashboard.service';
import { LoggerService, DialogsModule } from 'eds-angular4';
import {NgxPaginationModule} from 'ngx-pagination';
import { ToastModule } from 'ng2-toastr/ng2-toastr';
import { ChartModule } from 'eds-angular4/dist/charting';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    NgbModule,
    DialogsModule,
    ChartModule,
    NgxPaginationModule,
    ToastModule.forRoot()
  ],
  declarations: [
    DashboardComponent],
  entryComponents: [
  ],
  providers: [
    DashboardService,
    LoggerService
  ]
})
export class DashboardModule { }
