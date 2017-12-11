import { Component, OnInit } from '@angular/core';
import {DashboardItem} from '../models/DashboardItem';
import {ConfigurationService} from '../configuration.service';
import {Layout} from '../../dashboard/models/Layout';

@Component({
  selector: 'app-configuration',
  templateUrl: './configuration.component.html',
  styleUrls: ['./configuration.component.css']
})
export class ConfigurationComponent implements OnInit {
  dashboardItems: DashboardItem[] = [];
  selectedItem: DashboardItem = {title: ''};
  selectedLayoutItem: Layout = {title: ''};
  layout: Layout[];

  typeList = [
    {id: 0, value: 'Information'},
    {id: 1, value: 'Graph'}
  ];

  sizeList = [
    {id: 0, value: 'Small'},
    {id: 1, value: 'Large'}
  ];

  constructor(private configService: ConfigurationService) { }

  ngOnInit() {
    const vm = this;
    vm.getDashboardItems();
    vm.getLayoutItems();
  }

  getDashboardItems() {
    const vm = this;
    vm.configService.getDashboardItems()
      .subscribe(
        (result) => {
          console.log(result);
          vm.dashboardItems = result;
          vm.selectedItem = vm.dashboardItems[0];
        },
        (error) => console.log(error)
      );
  }

  getLayoutItems() {
    const vm = this;
    vm.configService.getLayoutItems()
      .subscribe(
        (result) => {
          console.log(result);
          vm.layout = result;
          if (result.length > 0) {
            vm.selectedLayoutItem = vm.layout[0];
          }
        },
        (error) => console.log(error)
      );
  }

  addNewLayoutItem() {
    const vm = this;

    const newItem: Layout = {title: ''};
    vm.layout.push(newItem);
    vm.selectedLayoutItem = newItem;
  }

  addNewItem() {
    const vm = this;

    const newItem: DashboardItem = {title: ''};
    vm.dashboardItems.push(newItem);
    vm.selectedItem = newItem;
  }

  groupChanged() {
    console.log('changed');
  }

  layoutGroupChanged() {
    console.log('changed');
  }

  save() {
    const vm = this;
    vm.configService.setDashboardItems(vm.selectedItem)
      .subscribe(
        (result) => {
          console.log('saved');
          vm.selectedItem.id = result;
          vm.getDashboardItems();
        });

  }

  saveLayout() {
    const vm = this;
    vm.configService.setLayoutItems(vm.selectedLayoutItem)
      .subscribe(
        (result) => {
          console.log('saved');
          vm.selectedLayoutItem.id = result;
          vm.getLayoutItems();
        });

  }

}
