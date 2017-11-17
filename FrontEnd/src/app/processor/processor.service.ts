import { Injectable } from '@angular/core';
import {URLSearchParams, Http} from "@angular/http";
import {Observable} from "rxjs/Observable";

@Injectable()
export class ProcessorService {

  constructor(private http : Http) { }

  public startProcessor() : Observable<string> {
    let vm = this;
    return vm.http.get("/api/processor/startProcessor",{ withCredentials : true} )
      .map((response) => response.text());
  }

  public stopProcessor() : Observable<string> {
    let vm = this;
    return vm.http.get("/api/processor/stopProcessor",{ withCredentials : true} )
      .map((response) => response.text());
  }

  public isRunning() : Observable<string> {
    let vm = this;
    return vm.http.get("/api/processor/isRunning",{ withCredentials : true} )
      .map((response) => response.json());
  }

  public clearCache() : Observable<string> {
    let vm = this;
    return vm.http.get("/api/processor/clearConfigCache",{ withCredentials : true} )
      .map((response) => response.text());
  }

  public getNextRun() : Observable<string> {
    let vm = this;
    return vm.http.get("/api/processor/nextRun",{ withCredentials : true} )
      .map((response) => response.text());
  }

  public setDelay(delay: string) : Observable<string> {
    let vm = this;
    const params = new URLSearchParams();
    params.set('delayValue', delay);
    return vm.http.get("/api/processor/setDelay",{ search : params, withCredentials : true} )
      .map((response) => response.text());
  }

  public getDelay() : Observable<string> {
    let vm = this;
    return vm.http.get("/api/processor/getDelay",{ withCredentials : true} )
      .map((response) => response.text());
  }

  public getLastRun() : Observable<string> {
    let vm = this;
    return vm.http.get("/api/processor/getLastRun",{ withCredentials : true} )
      .map((response) => response.text());
  }
}
