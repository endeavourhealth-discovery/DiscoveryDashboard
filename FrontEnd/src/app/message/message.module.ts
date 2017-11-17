import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MessageComponent } from './message/message.component';
import {MessageService} from "./message.service";
import {DialogsModule, LoggerService} from "eds-angular4";
import {FormsModule} from "@angular/forms";
import {NgxPaginationModule} from "ngx-pagination";
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {ToastModule} from "ng2-toastr";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    NgbModule,
    DialogsModule,
    NgxPaginationModule,
    ToastModule.forRoot()
  ],
  declarations: [
    MessageComponent
  ],
  providers: [
    MessageService,
    LoggerService
  ]
})
export class MessageModule { }
