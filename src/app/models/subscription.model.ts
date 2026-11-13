export interface Subscription {
  id: number;
  memberId: number;
  memberName: string;
  planName: string;
  price: number;
  startDate: string;
  endDate: string;
  durationMonths: number;
  status: string;
  description?: string;
  createdAt?: string;
}
