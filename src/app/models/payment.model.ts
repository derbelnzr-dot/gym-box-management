export interface Payment {
  id: number;
  memberId: number;
  memberName: string;
  amount: number;
  paymentDate: string;
  paymentMethod: string;
  transactionId?: string;
  status: string;
  notes?: string;
  createdAt?: string;
}
