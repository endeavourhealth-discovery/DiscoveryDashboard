import {Component, OnInit, ViewContainerRef} from '@angular/core';
import {MessageService} from "../message.service";
import {MessageStore} from "../models/MessageStore";
import {PayloadViewerComponent} from "../payload-viewer/payload-viewer.component";
import {LoggerService, MessageBoxDialog} from "eds-angular4";
import {ToastsManager} from "ng2-toastr";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.css']
})
export class MessageComponent implements OnInit {
  selectedOptions = [];
  messageCount: number;
  messages: MessageStore[];

  //Pagination
  pageNumber : number = 1;
  pageSize : number = 10;
  orderColumn: string = 'id';
  ascending = false;

  orderList = [
    {id: 0, name: 'Id', column: 'id'},
    {id: 1, name: 'Source', column: 'source'},
    {id: 2, name: 'Received', column: 'receivedDateTime'},
    {id: 3, name: 'Status', column: 'status'},
    {id: 4, name: 'Sent', column: 'sentDateTime'}
  ];

  pageSizeList = [
    {value: 10},
    {value: 50},
    {value: 100},
  ];

  ascendinglist = [
    {name: 'Descending', ascending: false},
    {name: 'Ascending', ascending: true}
  ];

  options = [
    {name:'Received', value:'0', checked:true},
    {name:'Sent to Messaging API', value:'1', checked:true},
    {name:'Error', value:'2', checked:true}
  ];

  constructor(private messageService: MessageService,
              private $modal: NgbModal,
              public toastr: ToastsManager,
              vcr: ViewContainerRef,
              private log: LoggerService) {
    this.toastr.setRootViewContainerRef(vcr);
  }

  ngOnInit() {
    const vm = this;
    vm.getMessages();
  }

  getMessageCount() {
    const vm = this;
    vm.selectedOptions = vm.getSelectedOptions();

    vm.messageService.getMessageCount(vm.selectedOptions)
      .subscribe(
        (result) => {
          vm.messageCount = result;
        },
        (error) => console.log(error)
      );
  }

  getSelectedOptions() {
    return this.options
      .filter(opt => opt.checked)
      .map(opt => opt.value)
  }

  getMessages() {
    const vm = this;
    vm.selectedOptions = vm.getSelectedOptions();
    vm.getMessageCount();

    vm.messageService.getMessages(vm.pageNumber, vm.pageSize, vm.orderColumn, vm.ascending, vm.selectedOptions)
      .subscribe(
        (result) => {
          console.log(result);
          vm.messages = result;
        },
        (error) => console.log(error)
      );
  }

  pageChanged($event) {
    const vm = this;
    vm.pageNumber = $event;
    vm.getMessages();
  }

  setColour(status : number) {
    if (status == 0) {
      return 'table-warning';
    }
    if (status == 1) {
      return 'table-success';
    }
    if (status == 2) {
      return 'table-danger';
    }
  }

  setBgColour(status : number) {
    if (status == 0) {
      return 'bg-warning';
    }
    if (status == 1) {
      return 'bg-success';
    }
    if (status == 2) {
      return 'bg-danger';
    }
  }

  viewDetails(message: MessageStore) {
    const vm = this;
    PayloadViewerComponent.open(vm.$modal, message)
      .result.then();
  }

  resendMessage(message: MessageStore) {
    const vm = this;
    MessageBoxDialog.open(vm.$modal, 'Resend Message',
      'Are you sure you want to resend message ' + message.id + '?', 'Yes', 'No')
      .result.then(
      () => vm.resendSingleMessage(message.id),
      () => vm.log.info('Resend cancelled', null, 'Cancel')
    );
  }

  resendSingleMessage(messageId: number) {
    const vm = this;
    vm.messageService.resendSingleMessages(messageId)
      .subscribe(
        (result) => {
          vm.log.success(result);
          vm.getMessages();
        },
        (error) => vm.log.error(error)
      );
  }
}
