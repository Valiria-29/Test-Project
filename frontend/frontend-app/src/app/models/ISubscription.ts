export interface ISubscription {
  id: number;
  serviceName: string;
  startDate: string;
  endDate: string;
  cost: number;
  reminderEnabled: boolean;
  userEmail?: string;
  editing?: boolean;
}