import {Injectable} from "@angular/core";
import {AbstractMenuProvider} from "eds-angular4";
import {MenuOption} from "eds-angular4/dist/layout/models/MenuOption";
import {Routes} from '@angular/router';
import {DashboardComponent} from './dashboard/dashboard/dashboard.component';
import {MessageComponent} from "./message/message/message.component";
import {GraphComponent} from "./graph/graph/graph.component";

@Injectable()
export class AppMenuService implements AbstractMenuProvider {
  static getRoutes(): Routes {
    return [
      { path: '', redirectTo : 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: DashboardComponent },
      { path: 'messages', component: MessageComponent},
      { path: 'graph', component: GraphComponent}
    ];
  }

  getClientId(): string {
    return 'eds-dsa-manager';
  }

  getApplicationTitle(): string {
    return 'Adastra Receiver';
  }
  getMenuOptions(): MenuOption[] {
    return [
      {caption: 'Dashboard', state: 'dashboard', icon: 'fa fa-tachometer'},
      {caption: 'Messages', state: 'messages', icon: 'fa fa-table'},
      {caption: 'Graphs', state: 'graph', icon: 'fa fa-line-chart'}
    ];
  }
}
