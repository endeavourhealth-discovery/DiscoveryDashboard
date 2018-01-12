import {Component, OnInit, ViewContainerRef} from '@angular/core';
import {DashboardItem} from '../models/DashboardItem';
import {ConfigurationService} from '../configuration.service';
import {Layout} from '../../dashboard/models/Layout';
import {LoggerService, SecurityService} from 'eds-angular4';
import {User} from 'eds-angular4/dist/security/models/User';
import {ToastsManager} from 'ng2-toastr';

@Component({
  selector: 'app-configuration',
  templateUrl: './configuration.component.html',
  styleUrls: ['./configuration.component.css']
})
export class ConfigurationComponent implements OnInit {
  dashboardItems: DashboardItem[] = [];
  currentUser: User;
  userName: string;
  selectedItem: DashboardItem = {title: ''};
  selectedLayoutItem: Layout = {title: '', size: 0};
  layout: Layout[];

  typeList = [
    {id: 0, value: 'Information'},
    {id: 1, value: 'Graph'}
  ];

  sizeList = [
    {id: 0, value: 'X-Small'},
    {id: 1, value: 'Small'},
    {id: 2, value: 'Large'}
  ];

  periodList = [
    {value: 'HOUR'},
    {value: 'DAY'},
    {value: 'MONTH'},
    {value: 'YEAR'},
  ];

  constructor(private configService: ConfigurationService,
              private securityService: SecurityService,
              public toastr: ToastsManager,
              vcr: ViewContainerRef,
              private log: LoggerService) {
    this.toastr.setRootViewContainerRef(vcr);
  }

  ngOnInit() {
    const vm = this;
    vm.currentUser = this.securityService.getCurrentUser();
    vm.userName = vm.currentUser.surname + ':' + vm.currentUser.forename;
    vm.selectedLayoutItem.username = vm.userName;
    vm.getDashboardItems();
    vm.getLayoutItems();
  }

  getDashboardItems() {
    const vm = this;
    vm.configService.getDashboardItems()
      .subscribe(
        (result) => {
          vm.dashboardItems = result;
          if (result.length > 0 && vm.selectedItem.id == null) {
            vm.selectedItem = vm.dashboardItems[0];
          } else {
            vm.selectedItem = vm.dashboardItems.find(items => items.id === vm.selectedItem.id);
          }
        },
        (error) => console.log(error)
      );
  }

  getLayoutItems() {
    const vm = this;
    vm.configService.getLayoutItems(vm.userName)
      .subscribe(
        (result) => {
          vm.layout = result;
          if (result.length > 0 && vm.selectedLayoutItem.id == null) {
            vm.selectedLayoutItem = vm.layout[0];
          } else {
            vm.selectedLayoutItem = vm.layout.find(items => items.id === vm.selectedLayoutItem.id);
          }
        },
        (error) => console.log(error)
      );
  }

  addNewLayoutItem() {
    const vm = this;

    const newItem: Layout = {title: '', username: vm.userName, size: 0};
    vm.layout.push(newItem);
    vm.selectedLayoutItem = newItem;
  }

  addNewItem() {
    const vm = this;

    const newItem: DashboardItem = {title: '', dashboardType: 0};
    vm.dashboardItems.push(newItem);
    vm.selectedItem = newItem;
  }

  groupChanged() {
  }

  dashboardItemChanged() {
    const vm = this;
    const item = vm.dashboardItems.find(items => items.id === vm.selectedLayoutItem.dashboardItem);
    if (item.dashboardType === 1) {
      vm.selectedLayoutItem.isGraph = true;
    } else {
      vm.selectedLayoutItem.isGraph = false;
    }
  }

  layoutGroupChanged() {
    const vm = this;
    vm.dashboardItemChanged();
  }

  save() {
    const vm = this;
    vm.configService.setDashboardItems(vm.selectedItem)
      .subscribe(
        (result) => {
          vm.log.success('Dashboard item saved');
          vm.selectedItem.id = result;
          vm.getDashboardItems();
        });

  }

  saveLayout() {
    const vm = this;
    vm.configService.setLayoutItems(vm.selectedLayoutItem)
      .subscribe(
        (result) => {
          vm.log.success('Layout item saved');
          vm.selectedLayoutItem.id = result;
          vm.getLayoutItems();
        });
  }

  deleteLayout() {
    const vm = this;
    vm.configService.deleteLayoutItems(vm.selectedLayoutItem.id)
      .subscribe(
        (result) => {
          console.log(result);
          vm.log.success(result);
          vm.selectedLayoutItem = <Layout>{title: '', size: 0};
          vm.getLayoutItems();
        });

  }

  deleteDashboard() {
    const vm = this;
    vm.configService.deleteDashboardItem(vm.selectedItem.id)
      .subscribe(
        (result) => {
          console.log(result);
          vm.log.success(result);
          vm.selectedItem = <DashboardItem>{title: ''};
          vm.getDashboardItems();
        });

  }

}
