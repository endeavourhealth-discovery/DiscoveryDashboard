import {Injectable} from "@angular/core";
import {AbstractMenuProvider} from "eds-angular4";
import {MenuOption} from "eds-angular4/dist/layout/models/MenuOption";

@Injectable()
export class AppMenuService implements AbstractMenuProvider {
  getClientId(): string {
    return 'eds-dsa-manager';
  }

  getApplicationTitle(): string {
    return 'Adastra Receiver';
  }
  getMenuOptions():MenuOption[] {
    return [
      {caption: 'Dashboard', state: 'dashboard', icon: 'fa fa-cogs'},
      {caption: 'IM Settings', state: 'settings', icon: 'fa fa-cogs'}
    ];
  }
}
