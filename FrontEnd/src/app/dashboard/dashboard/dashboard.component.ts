import {Component, OnInit, OnDestroy, ViewContainerRef} from '@angular/core';
import {DashboardService} from '../dashboard.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {LoggerService, MessageBoxDialog} from 'eds-angular4';
import {ToastsManager} from 'ng2-toastr';
import {Observable} from 'rxjs/Observable';
import {Subscription} from 'rxjs/Subscription';
import {GeneralSettings} from '../../processor/models/GeneralSettings';
import {ApplicationInformation} from '../models/ApplicationInformation';
import {GraphService} from '../../graph/graph.service';
import {GraphOptions} from '../../graph/models/GraphOptions';
import {List} from 'linqts/linq';
import {Chart} from 'eds-angular4/dist/charting/models/Chart';
import {GraphData} from '../../graph/models/GraphData';
import {Series} from 'eds-angular4/dist/charting/models/Series';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, OnDestroy {
  settings: GeneralSettings = <GeneralSettings>{};
  adastraInformation: ApplicationInformation[];
  refreshRate = 20;
  messageChart: Chart;

  private height = 200;
  private legend = {align: 'right', layout: 'vertical', verticalAlign: 'middle', width: 100};
  graphOptions: GraphOptions = <GraphOptions>{};

  private subscription: Subscription;


  constructor(private dashboardService: DashboardService,
              private graphService: GraphService,
              private $modal: NgbModal,
              public toastr: ToastsManager,
              vcr: ViewContainerRef,
              private log: LoggerService) {
    this.toastr.setRootViewContainerRef(vcr);
  }

  ngOnInit() {
    const vm = this;
    vm.getAdastraInformation();
    vm.initialiseGraphOptions();
  }

  setTimer() {
    const vm = this;
    const timer = Observable.timer(0, vm.refreshRate * 1000);
    vm.subscription = timer.subscribe(t => {
      vm.getAdastraInformation();
    });
  }

  updateTimer() {
    const vm = this;
    vm.destroyTimer();
    vm.setTimer();
  }

  getAdastraInformation() {
    const vm = this;
    vm.dashboardService.getApplicationInformation()
      .subscribe(
        (result) => {
          console.log(result);
          vm.adastraInformation = result;
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

  setBadgeColour(status: string) {
    if (status === 'primary') {
      return 'badge-primary';
    }
    if (status === 'warning') {
      return 'badge-warning';
    }
    if (status === 'success') {
      return 'badge-success';
    }
    if (status === 'danger') {
      return 'badge-danger';
    }
  }

  initialiseGraphOptions() {
    const vm = this;
    const today = new Date();
    const backDate = new Date();
    vm.graphOptions.period = 'DAY';
    vm.graphOptions.endTime = today;
    vm.graphOptions.startTime = backDate;
    vm.graphOptions.startTime.setDate(backDate.getDate() - 30);
    vm.getGraphData();
  }

  getGraphData() {
    const vm = this;
    vm.graphService.getGraphValues(vm.graphOptions)
      .subscribe(
        (result) => {
          console.log(result);
          vm.createChartFromResult(result);
        },
        (error) => console.log(error)
      );
  }



  private createChartFromResult(results: GraphData[]) {
    const vm = this;
    let chartCreated = false;

    for (let result of results) {
      if (!chartCreated) {
        vm.messageChart = vm.getTotalChartData(result.title, result.results);
        chartCreated = true;
      } else {
        vm.addSeriesToExistingGraph(result.title, result.results);
      }
    }

  }

  private addSeriesToExistingGraph(title: string, results: any) {
    const vm = this;
    const categories : string[] = new List(results).Select(row => row[0]).ToArray();
    const data = vm.getSeriesData(categories, results);
    vm.messageChart.addSeries(new Series()
      .setName(title)
      .setType('spline')
      .setData(data)
    );
  }

  private getTotalChartData(title : string, results : any) {
    const categories : string[] = new List(results).Select(row => row[0]).ToArray();
    const data : number[] = this.getSeriesData(categories, results);

    return new Chart()
      .setCategories(categories)
      .setHeight(this.height)
      .setLegend(this.legend)
      .setTitle('Message Statistics')
      .addYAxis('Count', false)
      .setSeries([
        new Series()
          .setName(title)
          .setType('spline')
          .setData(data)
      ]);
  }

  private getSeriesData(categories: string[], results: any) {
    const data: number[] = [];

    for (let category of categories) {
      const result = new List(results).Where(r => category == r[0]).SingleOrDefault();
      if (!result || result[1] == null) {
        data.push(0);
      }
      else {
        // data.push(this.getSeriesDataGraphAsValue(graphAs, result, category, series));
        data.push(result[1]);
      }
    }

    return data;
  }

}
