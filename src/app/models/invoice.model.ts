export interface Invoice {
  id: number;
  invoiceNumber: string;
  memberId: number;
  memberName: string;
  subscriptionId?: number;
  amount: number;
  tax?: number;
  total: number;
  issueDate: string;
  dueDate: string;
  status: string;
  notes?: string;
  createdAt?: string;
}
