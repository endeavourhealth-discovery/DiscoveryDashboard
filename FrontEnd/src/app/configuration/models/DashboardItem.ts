import {GraphOptions} from '../../dashboard/models/GraphOptions';

export class DashboardItem {
  id?: number;
  title: string;
  dashboardType?: number;
  apiUrl?: string;
  graphOptions?: GraphOptions;
}
