import { Injectable } from '@angular/core';
import {URLSearchParams, Http} from "@angular/http";
import {Observable} from "rxjs/Observable";

@Injectable()
export class GraphService {

  constructor(private http : Http) { }

  public getGraphValues() : Observable<any> {
    let vm = this;

    return vm.http.get("/api/graph/getGraphValues",{ withCredentials : true} )
      .map((response) => response.json());
  }

}
