import { Injectable } from '@angular/core';
import {Observable} from "rxjs/Observable";
import {URLSearchParams, Http} from "@angular/http";
import {MessageStore} from "./models/MessageStore";

@Injectable()
export class MessageService {

  constructor(private http : Http) { }

  public getMessageCount(statusList: number[] = []) : Observable<number> {
    let vm = this;
    const params = new URLSearchParams();

    for (var status of statusList) {
      params.append('statusList', status.toString());
    }
    return vm.http.get("/api/message/getMessageCount",{ search : params,  withCredentials : true} )
      .map((response) => response.json());
  }

  public getMessages(pageNumber: number = 1, pageSize: number = 20,
                     orderColumn: string = 'name', ascending: boolean = false,
                     statusList: number[] = []) : Observable<MessageStore[]> {
    let vm = this;
    const params = new URLSearchParams();
    params.set('pageNumber', pageNumber.toString());
    params.set('pageSize', pageSize.toString());
    params.set('orderColumn', orderColumn.toString());
    params.set('ascending', ascending.toString());

    for (var status of statusList) {
      params.append('statusList', status.toString());
    }
    return vm.http.get("/api/message/getMessages",{ search : params,  withCredentials : true} )
      .map((response) => response.json());
  }

  public resendSingleMessages(messageId: number) : Observable<string> {
    let vm = this;
    const params = new URLSearchParams();
    params.set('messageId', messageId.toString());
    params.set('mode', 'single');
    return vm.http.get("/api/dashboard/resendMessages",{ search : params, withCredentials : true} )
      .map((response) => response.text());
  }

}
