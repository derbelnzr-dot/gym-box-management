export interface Member {
  id: number;
  userId: number;
  username: string;
  email: string;
  firstName?: string;
  lastName?: string;
  dateOfBirth?: string;
  gender?: string;
  phone?: string;
  address?: string;
  city?: string;
  emergencyContact?: string;
  photo?: string;
  isActive: boolean;
  createdAt?: string;
}
