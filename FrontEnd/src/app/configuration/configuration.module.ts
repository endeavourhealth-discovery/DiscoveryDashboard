import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {FormsModule} from '@angular/forms';
import {ConfigurationComponent} from './configuration/configuration.component';
import {ConfigurationService} from './configuration.service';
import {ToastModule} from 'ng2-toastr';
import {LoggerService} from 'eds-angular4';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    NgbModule,
    ToastModule.forRoot()
  ],
  declarations: [
    ConfigurationComponent
  ],
  providers: [
    ConfigurationService,
    LoggerService
  ]
})
export class ConfigurationModule { }
