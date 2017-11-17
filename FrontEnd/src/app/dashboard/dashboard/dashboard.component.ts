import {Component, OnInit, OnDestroy, ViewContainerRef} from '@angular/core';
import {DashboardService} from "../dashboard.service";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {LoggerService, MessageBoxDialog} from "eds-angular4";
import {ToastsManager} from 'ng2-toastr';
import {ProcessorService} from "../../processor/processor.service";
import {Observable} from "rxjs/Observable";
import {Subscription} from "rxjs/Subscription";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, OnDestroy {
  totalMessageCount: number = 0;
  receivedMessageCount: number = 0;
  sentMessageCount: number = 0;
  errorMessageCount: number = 0;
  messageXML: string = "";
  runningStatus: string = "";
  beforeResend: number;
  afterResend: number;

  lastRun: string;
  delay: string;
  nextRun: string;

  refreshRate = 10;
  private subscription: Subscription;


  constructor(private dashboardService: DashboardService,
              private processorService: ProcessorService,
              private $modal: NgbModal,
              public toastr: ToastsManager,
              vcr: ViewContainerRef,
              private log: LoggerService) {
    this.toastr.setRootViewContainerRef(vcr);
  }

  ngOnInit() {
    const vm = this;
    vm.setTimer();
  }

  setTimer() {
    const vm = this;
    let timer = Observable.timer(0, vm.refreshRate * 1000);
    vm.subscription = timer.subscribe(t => {
      vm.refreshScreen();
    });
  }

  updateTimer() {
    const vm = this;
    vm.destroyTimer();
    vm.setTimer();
  }

  destroyTimer() {
    const vm = this;
    vm.subscription.unsubscribe();
  }

  ngOnDestroy() {
    const vm = this;
    vm.subscription.unsubscribe();
  }

  refreshScreen() {
    const vm = this;
    vm.getDashboardStatistics();
    vm.getProcessorStatistics();
  }

  getDashboardStatistics() {
    var vm = this;
    vm.getTotalMessageCount();
    vm.getReceivedMessageCount();
    vm.getSentMessageCount();
    vm.getErrorMessageCount();
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

  startProcessor() {
    const vm = this;
    vm.processorService.startProcessor()
      .subscribe(
        (result) => {
          console.log(result);
        },
        (error) => console.log(error)
      );
  }

  stopProcessor() {
    const vm = this;
    vm.processorService.stopProcessor()
      .subscribe(
        (result) => {
          console.log(result);
        },
        (error) => console.log(error)
      );
  }

  checkProcessorIsRunning() {
    const vm = this;
    vm.processorService.isRunning()
      .subscribe(
        (result) => {
          console.log(result);
          vm.runningStatus = result;
        },
        (error) => console.log(error)
      );
  }

  clearCache() {
    const vm = this;
    vm.processorService.clearCache()
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
      'Are you sure you want to resend all messages in Error? ' +
      '\n\nThis will resend ' + vm.errorMessageCount + ' messages.', 'Yes', 'No')
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

  getNextRun() {
    const vm = this;
    vm.processorService.getNextRun()
      .subscribe(
        (result) => {
          vm.nextRun = result;
        },
        (error) => console.log(error)
      );
  }

  setDelay() {
    const vm = this;
    vm.processorService.setDelay(vm.delay)
      .subscribe(
        (result) => {
          vm.log.success(result);
          vm.log.success('Restart the processor for the change to take effect');
        },
        (error) => console.log(error)
      );
  }

  getDelay() {
    const vm = this;
    vm.processorService.getDelay()
      .subscribe(
        (result) => {
          vm.delay = result;
        },
        (error) => console.log(error)
      );
  }

  getLastRun() {
    const vm = this;
    vm.processorService.getLastRun()
      .subscribe(
        (result) => {
          vm.lastRun = result;
          console.log(result);
        },
        (error) => console.log(error)
      );
  }

  getProcessorStatistics() {
    const vm = this;
    vm.getLastRun();
    vm.getDelay();
    vm.getNextRun();
    vm.checkProcessorIsRunning();
  }

}
