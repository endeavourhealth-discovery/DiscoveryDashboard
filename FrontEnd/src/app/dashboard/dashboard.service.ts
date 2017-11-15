import { Injectable } from '@angular/core';
import {URLSearchParams, Http} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {MessageStore} from "./models/MessageStore";

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

  public sendXML(xml: string) : Observable<any> {
    let vm = this;
    return vm.http.post("/api/adastra/postMessage", xml,{ withCredentials : true} )
      .map((response) => response.text());
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

  public getMessages(pageNumber: number = 1, pageSize: number = 20,
                     orderColumn: string = 'name', ascending: boolean = false,
                     statusList: number[] = []) : Observable<MessageStore[]> {
    let vm = this;
    const params = new URLSearchParams();
    console.log(statusList.toString());
    params.set('pageNumber', pageNumber.toString());
    params.set('pageSize', pageSize.toString());
    params.set('orderColumn', orderColumn.toString());
    params.set('ascending', ascending.toString());

    for (var status of statusList) {
      params.append('statusList', status.toString());
    }
    return vm.http.get("/api/adastra/getMessages",{ search : params,  withCredentials : true} )
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

}
