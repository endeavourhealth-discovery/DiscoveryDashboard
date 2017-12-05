import { Injectable } from '@angular/core';
import {Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {DashboardItem} from './models/DashboardItem';

@Injectable()
export class ConfigurationService {

  constructor(private http: Http) { }

  public getApplicationInformation(): Observable<DashboardItem[]> {
    let vm = this;
    return vm.http.get('/api/configuration/getDashboardItems', {withCredentials : true} )
      .map((response) => response.json());
  }
}
