export interface DashboardStats {
  totalMembers: number;
  activeMembers: number;
  totalCoaches: number;
  totalSessions: number;
  activeSubscriptions: number;
  totalRevenue: number;
  expiredSubscriptions: number;
  membersByMonth: { [key: string]: number };
  revenueByMonth: { [key: string]: number };
}
