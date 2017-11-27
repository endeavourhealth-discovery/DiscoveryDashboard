import { Component, OnInit } from '@angular/core';
import {Chart} from "eds-angular4/dist/charting/models/Chart";
import {Series} from "eds-angular4/dist/charting/models/Series";
import { List } from 'linqts';
import {GraphService} from "../graph.service";

@Component({
  selector: 'app-graph',
  templateUrl: './graph.component.html',
  styleUrls: ['./graph.component.css']
})
export class GraphComponent implements OnInit {

  messageChart: Chart;
  private height = 200;
  private legend = {align: 'right', layout: 'vertical', verticalAlign: 'middle', width: 100};

  constructor(private graphService: GraphService) { }

  ngOnInit() {
    const vm = this;
    vm.getData();
  }

  getData() {
    const vm = this;
    vm.graphService.getGraphValues()
      .subscribe(
        (result) => {
          console.log(result);
          vm.messageChart = vm.getGroupedChartData('MessageCount', result, 'count');
        },
        (error) => console.log(error)
      );
  }

  private getChartData() {
    const categories = ['received', 'error'];
    const error = new Series().setName('error').setData([20, 40, 30]);
    const message = new Series().setName('Message').setData([50, 60, 70]);
    const chartSeries: Series[] = [];
    chartSeries.push(error);
    chartSeries.push(message);

    this.messageChart = new Chart()
      .setCategories(categories)
      .setHeight(200)
      .setLegend({align: 'right', layout: 'vertical', verticalAlign: 'middle', width: 100})
      .setTitle('Messages')
      .addYAxis('Count', false)
      .setSeries(chartSeries);
  }

  private getGroupedChartData(title : string, results : any, graphAs : string) {
    let categories : string[] = new List(results)
      .Select(row => row[0])
      .Distinct()
      .ToArray()
      .sort();

    let groupedResults = new List(results)
      .Where(r => r[2] != 'Count')
      .GroupBy(r => r[2], r => r);

    let chartSeries : Series[] = new List(Object.keys(groupedResults))
      .Select(key => this.createSeriesChart(key, categories, groupedResults[key], graphAs))
      .ToArray();

    return new Chart()
      .setCategories(categories)
      .setHeight(this.height)
      .setLegend(this.legend)
      //.setTitle(title)
      .addYAxis(title , false)
      .setSeries(chartSeries);
  }

  private createSeriesChart(series : string, categories : string[], results : any, graphAs : string) : Series {
    //let filter = this.breakdown.filters.find(f => f.id == series);

    //let title = (filter == null) ? 'Unknown' : filter.name;
    let title = 'Count';
    let chartSeries : Series = new Series()
      .setName(title)
      .setType('spline');

    let data : number[] = this.getSeriesData(categories, results, graphAs, series);

    chartSeries.setData(data);

    return chartSeries;
  }

  private getSeriesData(categories : string[], results : any, graphAs : string, series? : string) {
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
