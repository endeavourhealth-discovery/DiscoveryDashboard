import { Injectable } from '@angular/core';
import {URLSearchParams, Http} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {GeneralSettings} from "./models/GeneralSettings";
import {ProcessorStatistics} from "./models/ProcessorStatistics";

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

  public reloadConfig() : Observable<string> {
    let vm = this;
    return vm.http.get("/api/processor/reloadConfig",{ withCredentials : true} )
      .map((response) => response.text());
  }

  public postSettings(settings: GeneralSettings) : Observable<string> {
    let vm = this;
    return vm.http.post("/api/processor/setGeneralSettings", settings, { withCredentials : true} )
      .map((response) => response.text());
  }

  public getSettings() : Observable<GeneralSettings> {
    let vm = this;
    return vm.http.get("/api/processor/getGeneralSettings",{ withCredentials : true} )
      .map((response) => response.json());
  }

  public getProcessorStatistics() : Observable<ProcessorStatistics> {
    let vm = this;
    return vm.http.get("/api/processor/getProcessorStatistics",{ withCredentials : true} )
      .map((response) => response.json());
  }

  public saveConfig() : Observable<number> {
    let vm = this;
    return vm.http.get("/api/processor/saveConfig",{ withCredentials : true} )
      .map((response) => response.json());
  }
}
