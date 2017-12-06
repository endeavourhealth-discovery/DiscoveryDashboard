import { Component, OnInit } from '@angular/core';
import {DashboardItem} from '../models/DashboardItem';
import {ConfigurationService} from '../configuration.service';

@Component({
  selector: 'app-configuration',
  templateUrl: './configuration.component.html',
  styleUrls: ['./configuration.component.css']
})
export class ConfigurationComponent implements OnInit {
  dashboardItems: DashboardItem[] = [];
  selectedItem: DashboardItem = {title: ''};

  typeList = [
    {id: 0, value: 'Information'},
    {id: 1, value: 'Graph'}
  ];

  constructor(private configService: ConfigurationService) { }

  ngOnInit() {
    const vm = this;
    vm.getDashboardItems();
  }

  getDashboardItems() {
    const vm = this;
    vm.configService.getDashboardItems()
      .subscribe(
        (result) => {
          console.log(result);
          vm.dashboardItems = result;
        },
        (error) => console.log(error)
      );
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

}
