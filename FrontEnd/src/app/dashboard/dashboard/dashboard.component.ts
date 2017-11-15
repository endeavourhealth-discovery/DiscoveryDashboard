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
  ]

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

  pageSizeChange(){
    const vm = this;
    vm.getMessages();
  }

  columnChange() {
    const vm = this;
    vm.getMessages();
  }

  orderChange() {
    const vm = this;
    vm.getMessages();
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
    console.log(vm.pageSize);
    vm.dashboardService.getMessages(vm.pageNumber, vm.pageSize, vm.orderColumn, vm.ascending)
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
