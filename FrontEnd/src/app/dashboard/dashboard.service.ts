import { Injectable } from '@angular/core';
import {URLSearchParams, Http} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {ApplicationInformation} from "./models/ApplicationInformation";
import {GraphOptions} from './models/GraphOptions';
import {GraphData} from './models/GraphData';

@Injectable()
export class DashboardService {

  constructor(private http : Http) { }

  public getApplicationInformation() : Observable<ApplicationInformation[]> {
    const vm = this;
    return vm.http.get("/api/dashboard/getApplicationInformation", {withCredentials : true} )
      .map((response) => response.json());
  }

  public getStandardApplicationInformation(url: string): Observable<ApplicationInformation[]> {
    const vm = this;
    return vm.http.get(url, {withCredentials : true} )
      .map((response) => response.json());
  }

  public getStandardGraphInformation(url: string, options: GraphOptions): Observable<GraphData[]> {
    const vm = this;
    return vm.http.post(url, options, {withCredentials : true} )
      .map((response) => response.json());
  }

}
