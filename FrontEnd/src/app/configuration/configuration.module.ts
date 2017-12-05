import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {FormsModule} from '@angular/forms';
import {ConfigurationComponent} from './configuration/configuration.component';
import {ConfigurationService} from './configuration.service';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    NgbModule,
  ],
  declarations: [
    ConfigurationComponent
  ],
  providers: [
    ConfigurationService
  ]
})
export class ConfigurationModule { }
