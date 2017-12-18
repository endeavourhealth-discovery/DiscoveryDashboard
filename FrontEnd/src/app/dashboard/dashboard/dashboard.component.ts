import {Component, OnInit, OnDestroy, ViewContainerRef} from '@angular/core';
import {DashboardService} from '../dashboard.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {LoggerService, MessageBoxDialog, SecurityService} from 'eds-angular4';
import {ToastsManager} from 'ng2-toastr';
import {Observable} from 'rxjs/Observable';
import {Subscription} from 'rxjs/Subscription';
import {GraphOptions} from '../models/GraphOptions';
import {List} from 'linqts/linq';
import {Chart} from 'eds-angular4/dist/charting/models/Chart';
import {GraphData} from '../models/GraphData';
import {Series} from 'eds-angular4/dist/charting/models/Series';
import {Layout} from '../models/Layout';
import {ConfigurationService} from '../../configuration/configuration.service';
import {DashboardItem} from '../../configuration/models/DashboardItem';
import {User} from 'eds-angular4/dist/security/models/User';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, OnDestroy {
  refreshRate = 20;
  currentUser: User;
  userName: string;

  private height = 200;
  private legend = {align: 'right', layout: 'vertical', verticalAlign: 'middle', width: 100};

  private subscription: Subscription;

  layout: Layout[] = [];
  dashboardItems: DashboardItem[] = [];

  constructor(private dashboardService: DashboardService,
              private configService: ConfigurationService,
              private securityService: SecurityService,
              private $modal: NgbModal,
              public toastr: ToastsManager,
              vcr: ViewContainerRef,
              private log: LoggerService) {
    this.toastr.setRootViewContainerRef(vcr);
  }

  ngOnInit() {
    const vm = this;
    vm.currentUser = this.securityService.getCurrentUser();
    vm.userName = vm.currentUser.surname + ':' + vm.currentUser.forename;
    vm.getDashboardItems();
    vm.setTimer();
  }

  getDashboardData() {
    const vm = this;
    for (let i = 0; i < vm.layout.length; i++) {
      const item = vm.dashboardItems.find(items => items.id === vm.layout[i].dashboardItem);
      if (item.dashboardType === 0) {
        vm.getStandardAppInfo(vm.layout[i], item);
      } else {
        vm.getStandardGraphData(vm.layout[i], item);
      }
    }
  }

  getStandardAppInfo(layout: Layout, item: DashboardItem) {
    const vm = this;
      vm.dashboardService.getStandardDashboardInformation(item.apiUrl)
        .subscribe(
          (result) => {
            layout.appInfo = result.applicationInformation;
            layout.healthStatus = result.appHealth;
          },
          (error) => console.log(error)
        );
  }

  getStandardGraphData(layout: Layout, item: DashboardItem) {
    const vm = this;
    vm.dashboardService.getStandardGraphInformation(item.apiUrl, vm.initialiseGraphOptions(layout.graphDays, layout.graphPeriod))
      .subscribe(
        (result) => {
          layout.healthStatus = result.appHealth;
          vm.createChartFromResult(result.graphResults, layout);
        },
        (error) => console.log(error)
      );
  }

  getDashboardItems() {
    const vm = this;
    vm.configService.getDashboardItems()
      .subscribe(
        (result) => {
          console.log(result);
          vm.dashboardItems = result;
          vm.getLayoutItems();
        },
        (error) => console.log(error)
      );
  }

  getLayoutItems() {
    const vm = this;
    vm.configService.getLayoutItems(vm.userName)
      .subscribe(
        (result) => {
          console.log(result);
          vm.layout = result;
          vm.getDashboardData();
        },
        (error) => console.log(error)
      );
  }

  setTimer() {
    const vm = this;
    const timer = Observable.timer(0, vm.refreshRate * 1000);
    vm.subscription = timer.subscribe(t => {
      vm.getDashboardData();
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
    if (vm.subscription != null) {
      vm.subscription.unsubscribe();
    }
  }

  setSize(size: number) {
    if (size === 0) {
      return 'col-md-3';
    }
    if (size === 1) {
      return 'col-md-6';
    }
    if (size === 2) {
      return 'col-md-12';
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

  setCardColour(status: string) {
    if (status === 'primary') {
      return 'card-primary';
    }
    if (status === 'warning') {
      return 'card-warning';
    }
    if (status === 'success') {
      return 'card-success';
    }
    if (status === 'danger') {
      return 'card-danger';
    }
    if (status === 'info') {
      return 'card-info';
    }
  }

  initialiseGraphOptions(days: number, period: string): GraphOptions {
    const vm = this;
    const graphOptions: GraphOptions = <GraphOptions>{};
    const today = new Date();
    const backDate = new Date();
    console.log(today);
    graphOptions.period = period;
    graphOptions.endTime = today;
    graphOptions.startTime = backDate;
    graphOptions.startTime.setDate(backDate.getDate() - days);
    return graphOptions;
  }



  private createChartFromResult(results: GraphData[], layout: Layout) {
    const vm = this;
    let chartCreated = false;

    for (const result of results) {
      if (!chartCreated) {
        layout.graph = vm.getTotalChartData(result.title, result.results);
        chartCreated = true;
      } else {
        vm.addSeriesToExistingGraph(result.title, result.results, layout);
      }
    }

  }

  private addSeriesToExistingGraph(title: string, results: any, layout: Layout) {
    const vm = this;
    const categories: string[] = new List(results).Select(row => row[0]).ToArray();
    const data = vm.getSeriesData(categories, results);
    layout.graph.addSeries(new Series()
      .setName(title)
      .setType('spline')
      .setData(data)
    );
  }

  private getTotalChartData(title: string, results: any) {
    const categories: string[] = new List(results).Select(row => row[0]).ToArray();
    const data: number[] = this.getSeriesData(categories, results);

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

    for (const category of categories) {
      const result = new List(results).Where(r => category === r[0]).SingleOrDefault();
      if (!result || result[1] == null) {
        data.push(0);
      } else {
        // data.push(this.getSeriesDataGraphAsValue(graphAs, result, category, series));
        data.push(result[1]);
      }
    }

    return data;
  }

}
