import {Injectable} from '@angular/core';
import {AbstractMenuProvider} from 'eds-angular4';
import {MenuOption} from 'eds-angular4/dist/layout/models/MenuOption';
import {Routes} from '@angular/router';
import {DashboardComponent} from './dashboard/dashboard/dashboard.component';
import {GraphComponent} from './graph/graph/graph.component';
import {ConfigurationComponent} from './configuration/configuration/configuration.component';

@Injectable()
export class AppMenuService implements AbstractMenuProvider {
  static getRoutes(): Routes {
    return [
      { path: '', redirectTo : 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: DashboardComponent },
      { path: 'configuration', component: ConfigurationComponent },
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
      {caption: 'Configuration', state: 'configuration', icon: 'fa fa-cogs'},
      {caption: 'Graphs', state: 'graph', icon: 'fa fa-line-chart'}
    ];
  }
}
