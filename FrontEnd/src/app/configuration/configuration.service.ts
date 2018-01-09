import { Injectable } from '@angular/core';
import {Http, URLSearchParams} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {DashboardItem} from './models/DashboardItem';
import {Layout} from '../dashboard/models/Layout';

@Injectable()
export class ConfigurationService {

  constructor(private http: Http) { }

  public getDashboardItems(): Observable<DashboardItem[]> {
    const vm = this;
    return vm.http.get('api/configuration/getDashboardItems', {withCredentials : true} )
      .map((response) => response.json());
  }

  public setDashboardItems(item: DashboardItem): Observable<any> {
    const vm = this;
    return vm.http.post('api/configuration/setDashboardItems', item, {withCredentials : true} )
      .map((response) => response.json());
  }

  public getLayoutItems(userName: string): Observable<Layout[]> {
    const vm = this;
    const params = new URLSearchParams();

    params.set('username', userName);
    return vm.http.get('api/configuration/getLayoutItems', {search: params, withCredentials : true} )
      .map((response) => response.json());
  }

  public setLayoutItems(item: Layout): Observable<any> {
    const vm = this;
    return vm.http.post('api/configuration/setLayoutItems', item, {withCredentials : true} )
      .map((response) => response.json());
  }

  public deleteLayoutItems(id: number): Observable<any> {
    const vm = this;
    const params = new URLSearchParams();

    params.set('id', id.toString());
    return vm.http.get('api/configuration/deleteLayoutItem', {search: params, withCredentials : true} )
      .map((response) => response.text());
  }

  public deleteDashboardItem(id: number): Observable<any> {
    const vm = this;
    const params = new URLSearchParams();

    params.set('id', id.toString());
    return vm.http.get('api/configuration/deleteDashboardItem', {search: params, withCredentials : true} )
      .map((response) => response.text());
  }
}
