import { Injectable } from '@angular/core';
import {URLSearchParams, Http} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {GraphData} from "../dashboard/models/GraphData";
import {GraphOptions} from "../dashboard/models/GraphOptions";

@Injectable()
export class GraphService {

  constructor(private http : Http) { }

  public getGraphValues(options: GraphOptions) : Observable<GraphData[]> {
    let vm = this;

    return vm.http.post("/api/graph/getGraphValues", options, { withCredentials : true} )
      .map((response) => response.json());
  }

}
