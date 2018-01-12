import { Injectable } from '@angular/core';
import {URLSearchParams, Http} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {ApplicationInformation} from "./models/ApplicationInformation";
import {GraphOptions} from './models/GraphOptions';
import {DashboardInformation} from './models/DashboardInformation';

@Injectable()
export class DashboardService {

  constructor(private http : Http) { }

  public getStandardDashboardInformation(url: string): Observable<DashboardInformation> {
    const vm = this;
    return vm.http.get(url, {withCredentials : true} )
      .map((response) => response.json());
  }

  public getStandardGraphInformation(url: string, options: GraphOptions): Observable<DashboardInformation> {
    const vm = this;
    return vm.http.post(url, options, {withCredentials : true} )
      .map((response) => response.json());
  }

}
