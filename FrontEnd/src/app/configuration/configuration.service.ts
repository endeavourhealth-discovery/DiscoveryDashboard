import { Injectable } from '@angular/core';
import {Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {DashboardItem} from './models/DashboardItem';

@Injectable()
export class ConfigurationService {

  constructor(private http: Http) { }

  public getDashboardItems(): Observable<DashboardItem[]> {
    const vm = this;
    return vm.http.get('/api/configuration/getDashboardItems', {withCredentials : true} )
      .map((response) => response.json());
  }

  public setDashboardItems(item: DashboardItem): Observable<any> {
    const vm = this;
    console.log(item);
    return vm.http.post('/api/configuration/setDashboardItems', item, {withCredentials : true} )
      .map((response) => response.json());
  }
}
