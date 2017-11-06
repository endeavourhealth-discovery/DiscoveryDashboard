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

  public message() : Observable<string> {
    let vm = this;
    return vm.http.get("/api/adastra/message",{ withCredentials : true} )
      .map((response) => response.text());
  }

}
