import { Component, OnInit } from '@angular/core';
import {Chart} from "eds-angular4/dist/charting/models/Chart";
import {Series} from "eds-angular4/dist/charting/models/Series";
import { List } from 'linqts';
import {GraphService} from "../graph.service";
import {GraphData} from "../models/GraphData";
import {GraphOptions} from "../models/GraphOptions";

@Component({
  selector: 'app-graph',
  templateUrl: './graph.component.html',
  styleUrls: ['./graph.component.css']
})
export class GraphComponent implements OnInit {

  messageChart: Chart;
  private height = 200;
  private legend = {align: 'right', layout: 'vertical', verticalAlign: 'middle', width: 100};

  graphOptions: GraphOptions = <GraphOptions>{};

  periodList = [
    {value: 'HOUR'},
    {value: 'DAY'},
    {value: 'MONTH'},
    {value: 'YEAR'},
  ];

  constructor(private graphService: GraphService) { }

  ngOnInit() {
    const vm = this;
    vm.initialiseOptions();
  }

  initialiseOptions() {
    const vm = this;
    var today = new Date();
    var backDate = new Date();
    vm.graphOptions.period = 'DAY';
    vm.graphOptions.endTime = today;
    vm.graphOptions.startTime = backDate;
    vm.graphOptions.startTime.setDate(backDate.getDate() - 30);
    vm.getData();
  }

  getData() {
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
    let categories : string[] = new List(results).Select(row => row[0]).ToArray();
    let data = vm.getSeriesData(categories, results);
    vm.messageChart.addSeries(new Series()
      .setName(title)
      .setType('spline')
      .setData(data)
    );
  }

  private getTotalChartData(title : string, results : any) {
    let categories : string[] = new List(results).Select(row => row[0]).ToArray();
    let data : number[] = this.getSeriesData(categories, results);

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

  private getSeriesData(categories : string[], results : any) {
    let data : number[] = [];

    for (let category of categories) {
      let result = new List(results).Where(r => category == r[0]).SingleOrDefault();
      if (!result || result[1] == null)
        data.push(0);
      else {
        //data.push(this.getSeriesDataGraphAsValue(graphAs, result, category, series));
        data.push(result[1]);
      }
    }

    return data;
  }

}
