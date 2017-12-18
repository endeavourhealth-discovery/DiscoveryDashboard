import {ApplicationInformation} from './ApplicationInformation';
import {Chart} from 'eds-angular4/dist/charting/models/Chart';

export class Layout {
  id?: number;
  position?: number;
  size?: number;
  title: string;
  username?: string;
  dashboardItem?: number;
  isGraph?: boolean;
  appInfo?: ApplicationInformation[];
  healthStatus?: string;
  graph?: Chart;
  graphDays?: number;
  graphPeriod?: string;
  dashboardUrl?: string;
}
