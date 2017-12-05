import { Injectable } from '@angular/core';
import {URLSearchParams, Http} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {ApplicationInformation} from "./models/ApplicationInformation";

@Injectable()
export class DashboardService {

  constructor(private http : Http) { }

  public getApplicationInformation() : Observable<ApplicationInformation[]> {
    let vm = this;
    return vm.http.get("/api/dashboard/getApplicationInformation", {withCredentials : true} )
      .map((response) => response.json());
  }

}
