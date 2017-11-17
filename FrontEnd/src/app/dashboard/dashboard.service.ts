import { Injectable } from '@angular/core';
import {URLSearchParams, Http} from "@angular/http";
import {Observable} from "rxjs/Observable";

@Injectable()
export class DashboardService {

  constructor(private http : Http) { }

  public getTotalMessageCount(status: number) : Observable<number> {
    let vm = this;
    const params = new URLSearchParams();
    params.set('status', status.toString());
    return vm.http.get("/api/dashboard/messageCount", {search : params, withCredentials : true} )
      .map((response) => response.json());
  }

  public getMessageCount(statusList: number[] = []) : Observable<number> {
    let vm = this;
    const params = new URLSearchParams();

    for (var status of statusList) {
      params.append('statusList', status.toString());
    }
    return vm.http.get("/api/dashboard/getMessageCount",{ search : params,  withCredentials : true} )
      .map((response) => response.json());
  }

  public resendMessages(messageId: number, mode: string) : Observable<string> {
    let vm = this;
    const params = new URLSearchParams();
    params.set('messageId', messageId.toString());
    params.set('mode', mode);
    return vm.http.get("/api/dashboard/resendMessages",{ search : params, withCredentials : true} )
      .map((response) => response.text());
  }

}
