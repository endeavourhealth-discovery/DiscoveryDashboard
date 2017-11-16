import {Component, OnInit, ViewContainerRef} from '@angular/core';
import {DashboardService} from "../dashboard.service";
import {MessageStore} from "../models/MessageStore";
import {PayloadViewerComponent} from "../payload-viewer/payload-viewer.component";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {LoggerService, MessageBoxDialog} from "eds-angular4";
import {ToastsManager} from 'ng2-toastr';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  totalMessageCount: number = 0;
  receivedMessageCount: number = 0;
  sentMessageCount: number = 0;
  errorMessageCount: number = 0;
  messageXML: string = "";
  runningStatus: string = "";
  messages: MessageStore[];
  beforeResend: number;
  afterResend: number;

  pageNumber : number = 1;
  pageSize : number = 10;
  orderColumn: string = 'id';
  ascending = false;
  selectedOptions = [];
  messageCount: number;

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

  dateNow : Date = new Date();
  dateNowISO = this.dateNow.toISOString();
  dateNowMilliseconds = this.dateNow.getTime();

  constructor(private dashboardService: DashboardService,
              private $modal: NgbModal,
              public toastr: ToastsManager,
              vcr: ViewContainerRef,
              private log: LoggerService,) {
    this.toastr.setRootViewContainerRef(vcr);
  }

  ngOnInit() {
    var vm = this;
    vm.getDashboardStatistics();
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

  getSelectedOptions() {
    return this.options
      .filter(opt => opt.checked)
      .map(opt => opt.value)
  }

  getDashboardStatistics() {
    var vm = this;
    vm.getTotalMessageCount();
    vm.getReceivedMessageCount();
    vm.getSentMessageCount();
    vm.getErrorMessageCount();
    vm.checkProcessorIsRunning();
    vm.getMessages();
  }

  getTotalMessageCount() {
    const vm = this;
    vm.dashboardService.getTotalMessageCount(-1)
      .subscribe(
        (result) => {
          vm.totalMessageCount = result;
        },
        (error) => console.log(error)
      );
  }

  getMessageCount() {
    const vm = this;
    vm.selectedOptions = vm.getSelectedOptions();

    vm.dashboardService.getMessageCount(vm.selectedOptions)
      .subscribe(
        (result) => {
          vm.messageCount = result;
          console.log(vm.messageCount);
        },
        (error) => console.log(error)
      );
  }

  getReceivedMessageCount() {
    const vm = this;
    vm.dashboardService.getTotalMessageCount(0)
      .subscribe(
        (result) => {
          vm.receivedMessageCount = result;
        },
        (error) => console.log(error)
      );
  }

  getSentMessageCount() {
    const vm = this;
    vm.dashboardService.getTotalMessageCount(1)
      .subscribe(
        (result) => {
          vm.sentMessageCount = result;
        },
        (error) => console.log(error)
      );
  }

  getErrorMessageCount() {
    const vm = this;
    vm.dashboardService.getTotalMessageCount(2)
      .subscribe(
        (result) => {
          vm.errorMessageCount = result;
        },
        (error) => console.log(error)
      );
  }

  sendXML() {
    const vm = this;
    vm.dashboardService.sendXML(vm.messageXML)
      .subscribe(
        (result) => {
          console.log(result);
        },
        (error) => console.log(error)
      );
  }

  startProcessor() {
    const vm = this;
    vm.dashboardService.startProcessor()
      .subscribe(
        (result) => {
          console.log(result);
        },
        (error) => console.log(error)
      );
  }

  stopProcessor() {
    const vm = this;
    vm.dashboardService.stopProcessor()
      .subscribe(
        (result) => {
          console.log(result);
        },
        (error) => console.log(error)
      );
  }

  checkProcessorIsRunning() {
    const vm = this;
    vm.dashboardService.isRunning()
      .subscribe(
        (result) => {
          console.log(result);
          vm.runningStatus = result;
        },
        (error) => console.log(error)
      );
  }

  getMessages() {
    const vm = this;
    vm.selectedOptions = vm.getSelectedOptions();
    vm.getMessageCount();

    vm.dashboardService.getMessages(vm.pageNumber, vm.pageSize, vm.orderColumn, vm.ascending, vm.selectedOptions)
      .subscribe(
        (result) => {
          console.log(result);
          vm.messages = result;
        },
        (error) => console.log(error)
      );
  }

  viewDetails(message: MessageStore) {
    const vm = this;
    PayloadViewerComponent.open(vm.$modal, message)
      .result.then();
  }

  clearCache() {
    const vm = this;
    vm.dashboardService.clearCache()
      .subscribe(
        (result) => {
          console.log(result);
        },
        (error) => console.log(error)
      );
  }

  resendMessages(messageId: number, mode: string) {
    const vm = this;
    vm.dashboardService.resendMessages(messageId, mode)
      .subscribe(
        (result) => {
          vm.log.success(result);
        },
        (error) => vm.log.error(error)
      );
  }

  resendErrorMessages() {
    const vm = this;
    MessageBoxDialog.open(vm.$modal, 'Resend Messages',
      'Are you sure you want to resend all messages in Error? \r\n' +
      'This will resend ' + vm.errorMessageCount + ' messages.', 'Yes', 'No')
      .result.then(
      () => vm.resendMessages(0, "error"),
      () => vm.log.info('Resend cancelled', null, 'Cancel')
    );

  }

  resendSingleMessage(messageId: number) {
    const vm = this;
    MessageBoxDialog.open(vm.$modal, 'Resend Messages',
      'Are you sure you want to resend message ' + messageId + '?\n ', 'Yes', 'No')
      .result.then(
      () => vm.resendMessages(messageId, "single"),
      () => vm.log.info('Resend cancelled', null, 'Cancel')
    );
  }

  resendMessagesBefore() {
    const vm = this;
    MessageBoxDialog.open(vm.$modal, 'Resend Messages',
      'Are you sure you want to resend all messages before ' + vm.beforeResend + '?\n ', 'Yes', 'No')
      .result.then(
      () => vm.resendMessages(vm.beforeResend, "before"),
      () => vm.log.info('Resend cancelled', null, 'Cancel')
    );
  }

  resendMessagesAfter() {
    const vm = this;
    MessageBoxDialog.open(vm.$modal, 'Resend Messages',
      'Are you sure you want to resend all messages after ' + vm.afterResend + '?\n ', 'Yes', 'No')
      .result.then(
      () => vm.resendMessages(vm.afterResend, "after"),
      () => vm.log.info('Resend cancelled', null, 'Cancel')
    );
  }

  resendAllMessages() {
    const vm = this;
    MessageBoxDialog.open(vm.$modal, 'Resend All Messages',
      'Are you sure you want to resend all messages?\n ' +
      'This will resend ' + vm.totalMessageCount + ' messages.', 'Yes', 'No')
      .result.then(
      () => vm.resendMessages(0, "all"),
      () => vm.log.info('Resend cancelled', null, 'Cancel')
    );
  }

}
