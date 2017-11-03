import { Component, OnInit } from '@angular/core';
import {DashboardService} from "../dashboard.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  totalMessageCount : number = 0;


  constructor(private dashboardService: DashboardService) { }

  ngOnInit() {
    var vm = this;
    vm.getTotalOrganisationCount();
  }

  getTotalOrganisationCount() {
    const vm = this;
    vm.dashboardService.getTotalMessageCount()
      .subscribe(
        (result) => {
          vm.totalMessageCount = result;
        },
        (error) => console.log(error)
      );
  }


}
