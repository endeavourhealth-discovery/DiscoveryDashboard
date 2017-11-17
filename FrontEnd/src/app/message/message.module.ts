import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MessageComponent } from './message/message.component';
import {MessageService} from "./message.service";
import {DialogsModule, LoggerService} from "eds-angular4";
import {FormsModule} from "@angular/forms";
import {NgxPaginationModule} from "ngx-pagination";
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {ToastModule} from "ng2-toastr";
import {PayloadViewerComponent} from "./payload-viewer/payload-viewer.component";

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
    MessageComponent,
    PayloadViewerComponent
  ],
  entryComponents: [
    PayloadViewerComponent
  ],
  providers: [
    MessageService,
    LoggerService
  ]
})
export class MessageModule { }
