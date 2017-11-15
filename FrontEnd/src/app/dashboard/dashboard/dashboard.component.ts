import { Component, OnInit } from '@angular/core';
import {DashboardService} from "../dashboard.service";
import {MessageStore} from "../models/MessageStore";

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

  constructor(private dashboardService: DashboardService) { }

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
      return 'table-success';
    }
    if (status == 1) {
      return 'table-warning';
    }
    if (status == 2) {
      return 'table-danger';
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

}
