import { Injectable } from '@angular/core';
import {URLSearchParams, Http} from "@angular/http";
import {Observable} from "rxjs/Observable";

@Injectable()
export class DashboardService {

  constructor(private http : Http) { }


  public getTotalMessageCount() : Observable<number> {
    let vm = this;
    return vm.http.get("/api/adastra/totalMessageCount", {withCredentials : true} )
      .map((response) => response.json());
  }

}
