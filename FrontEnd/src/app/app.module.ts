import { BrowserModule } from '@angular/platform-browser';
import { NgModule} from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import {KeycloakService} from "eds-angular4/dist/keycloak/keycloak.service";
import {keycloakHttpFactory} from "eds-angular4/dist/keycloak/keycloak.http";
import {Http, HttpModule, RequestOptions, XHRBackend} from "@angular/http";
import {LayoutComponent} from "eds-angular4/dist/layout/layout.component";
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {LayoutModule, AbstractMenuProvider} from "eds-angular4";
import {ToastModule} from 'ng2-toastr/ng2-toastr';
import {AppMenuService} from "./app-menu.service";
import { DashboardComponent } from './dashboard/dashboard/dashboard.component';
import {DashboardModule} from "./dashboard/dashboard.module";

export class DummyComponent {}

const appRoutes: Routes = [
  { path: 'dashboard', component: DashboardComponent }
];

@NgModule({
  declarations: [],
  imports: [
    BrowserModule,
    HttpModule,
    LayoutModule,
    DashboardModule,
    RouterModule.forRoot(appRoutes),
    ToastModule.forRoot(),
    NgbModule.forRoot()
  ],
  providers: [
    KeycloakService,
    { provide: Http, useFactory: keycloakHttpFactory, deps: [XHRBackend, RequestOptions, KeycloakService] },
    { provide: AbstractMenuProvider, useClass : AppMenuService }
  ],
  bootstrap: [LayoutComponent]
})
export class AppModule { }
