import {Component, OnInit, OnDestroy, ViewContainerRef} from '@angular/core';
import {DashboardService} from "../dashboard.service";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {LoggerService, MessageBoxDialog} from "eds-angular4";
import {ToastsManager} from 'ng2-toastr';
import {ProcessorService} from "../../processor/processor.service";
import {Observable} from "rxjs/Observable";
import {Subscription} from "rxjs/Subscription";
import {DashboardStatistics} from "../models/DashboardStatistics";
import {ProcessorStatistics} from "../../processor/models/ProcessorStatistics";
import {GeneralSettings} from "../../processor/models/GeneralSettings";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, OnDestroy {
  dashboardStatistics: DashboardStatistics = <DashboardStatistics>{};
  processorStatistics: ProcessorStatistics = <ProcessorStatistics>{};
  settings: GeneralSettings = <GeneralSettings>{};
  beforeResend: number;
  afterResend: number;

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
    vm.refreshScreen();
    vm.getGeneralSettings();
  }

  setTimer() {
    const vm = this;
    let timer = Observable.timer(0, vm.settings.refreshRate * 1000);
    vm.subscription = timer.subscribe(t => {
      vm.refreshScreen();
    });
  }

  updateTimer() {
    const vm = this;
    vm.destroyTimer();
    vm.setTimer();
  }

  updateConfig(save: boolean) {
    const vm = this;
    vm.processorService.postSettings(vm.settings)
      .subscribe(
        (result) => {
          vm.log.success(result);
          vm.updateTimer();
          vm.log.success("A restart of the message processor is required if Processor Delay has been changed");
          if (save) {
            console.log(save);
            vm.saveGeneralSettings();
          }
        },
        (error) => console.log(error)
      );
  }

  destroyTimer() {
    const vm = this;
    vm.subscription.unsubscribe();
  }

  ngOnDestroy() {
    const vm = this;
    if (vm.subscription != null) {
      vm.subscription.unsubscribe();
    }
  }

  refreshScreen() {
    const vm = this;
    vm.getDashboardStatistics();
    vm.getProcessorStatistics();
  }

  getDashboardStatistics() {
    const vm = this;
    vm.dashboardService.getDashboardStatistics()
      .subscribe(
        (result) => {
          vm.dashboardStatistics = result;
        },
        (error) => console.log(error)
      );
  }

  getProcessorStatistics() {
    const vm = this;
    vm.processorService.getProcessorStatistics()
      .subscribe(
        (result) => {
          vm.processorStatistics = result;
          console.log(vm.processorStatistics);
        },
        (error) => console.log(error)
      );
  }

  getGeneralSettings() {
    const vm = this;
    vm.processorService.getSettings()
      .subscribe(
        (result) => {
          vm.settings = result;
          vm.setTimer();
        },
        (error) => console.log(error)
      );
  }

  saveSettings() {
    const vm = this;
    MessageBoxDialog.open(vm.$modal, 'Save configuration',
      'Are you sure you want to save the current configuration settings? ', 'Yes', 'No')
      .result.then(
      () => vm.updateConfig(true),
      () => vm.log.info('Cancelled', null, 'Cancel')
    );
  }

  saveGeneralSettings() {
    const vm = this;
    vm.processorService.saveConfig()
      .subscribe(
        (result) => {
          vm.log.success(result);
        },
        (error) => console.log(error)
      );
  }

  startProcessor() {
    const vm = this;
    vm.processorService.startProcessor()
      .subscribe(
        (result) => {
          vm.log.success(result);
          vm.refreshScreen();
        },
        (error) => console.log(error)
      );
  }

  stopProcessor() {
    const vm = this;
    vm.processorService.stopProcessor()
      .subscribe(
        (result) => {
          vm.log.success(result);
          vm.refreshScreen();
        },
        (error) => console.log(error)
      );
  }

  reloadGeneralConfig() {
    const vm = this;
    MessageBoxDialog.open(vm.$modal, 'Save configuration',
      'Are you sure you want to reload the configuration settings from the database? ', 'Yes', 'No')
      .result.then(
      () => vm.reloadConfig(),
      () => vm.log.info('Cancelled', null, 'Cancel')
    );
  }

  reloadConfig() {
    const vm = this;
    vm.processorService.reloadConfig()
      .subscribe(
        (result) => {
          vm.log.success("Configuration successfully reloaded from Database");
          vm.getGeneralSettings();
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
          vm.refreshScreen();
        },
        (error) => vm.log.error(error)
      );
  }

  resendErrorMessages() {
    const vm = this;
    MessageBoxDialog.open(vm.$modal, 'Resend Messages',
      'Are you sure you want to resend all messages in Error? ' +
      '\n\nThis will resend ' + vm.dashboardStatistics.errorMessageCount + ' messages.', 'Yes', 'No')
      .result.then(
      () => vm.resendMessages(0, "error"),
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
      'This will resend ' + vm.dashboardStatistics.totalMessageCount + ' messages.', 'Yes', 'No')
      .result.then(
      () => vm.resendMessages(0, "all"),
      () => vm.log.info('Resend cancelled', null, 'Cancel')
    );
  }

}
