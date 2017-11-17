import { Injectable } from '@angular/core';
import {URLSearchParams, Http} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {MessageStore} from "../message/models/MessageStore";

@Injectable()
export class DashboardService {

  constructor(private http : Http) { }

  public getTotalMessageCount(status: number) : Observable<number> {
    let vm = this;
    const params = new URLSearchParams();
    params.set('status', status.toString());
    return vm.http.get("/api/adastra/messageCount", {search : params, withCredentials : true} )
      .map((response) => response.json());
  }

  public startProcessor() : Observable<string> {
    let vm = this;
    return vm.http.get("/api/adastra/startProcessor",{ withCredentials : true} )
      .map((response) => response.text());
  }

  public stopProcessor() : Observable<string> {
    let vm = this;
    return vm.http.get("/api/adastra/stopProcessor",{ withCredentials : true} )
      .map((response) => response.text());
  }

  public isRunning() : Observable<string> {
    let vm = this;
    return vm.http.get("/api/adastra/isRunning",{ withCredentials : true} )
      .map((response) => response.json());
  }

  public getMessageCount(statusList: number[] = []) : Observable<number> {
    let vm = this;
    const params = new URLSearchParams();

    for (var status of statusList) {
      params.append('statusList', status.toString());
    }
    return vm.http.get("/api/adastra/getMessageCount",{ search : params,  withCredentials : true} )
      .map((response) => response.json());
  }

  public clearCache() : Observable<string> {
    let vm = this;
    return vm.http.get("/api/adastra/clearConfigCache",{ withCredentials : true} )
      .map((response) => response.text());
  }

  public resendMessages(messageId: number, mode: string) : Observable<string> {
    let vm = this;
    const params = new URLSearchParams();
    params.set('messageId', messageId.toString());
    params.set('mode', mode);
    return vm.http.get("/api/adastra/resendMessages",{ search : params, withCredentials : true} )
      .map((response) => response.text());
  }

}
