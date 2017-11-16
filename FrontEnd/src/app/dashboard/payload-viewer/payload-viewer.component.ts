import {Component, Input, OnInit} from '@angular/core';
import {MessageStore} from "../models/MessageStore";
import {NgbModal, NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-payload-viewer',
  templateUrl: './payload-viewer.component.html',
  styleUrls: ['./payload-viewer.component.css']
})
export class PayloadViewerComponent implements OnInit {
  @Input() message: MessageStore;

  statusMap = [
    {name:'Received'},
    {name:'Sent to Messaging API'},
    {name:'Error'}
  ];

  public static open(modalService: NgbModal, message: MessageStore) {
    const modalRef = modalService.open(PayloadViewerComponent, { backdrop : 'static'});
    modalRef.componentInstance.message = message;

    return modalRef;
  }
  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit() {
  }

  ok() {
    this.activeModal.close();
    console.log('OK Pressed');
  }

  cancel() {
    this.activeModal.dismiss('cancel');
    console.log('Cancel Pressed');
  }

  setBgColour(status : number) {
    if (status == 0) {
      return 'bg-success';
    }
    if (status == 1) {
      return 'bg-warning';
    }
    if (status == 2) {
      return 'bg-danger';
    }
  }

}
