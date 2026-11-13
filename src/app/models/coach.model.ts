export interface Coach {
  id: number;
  userId: number;
  username: string;
  email: string;
  phone?: string;
  photo?: string;
  specialty?: string;
  bio?: string;
  yearsExperience?: number;
  certifications?: string;
  isAvailable: boolean;
  createdAt?: string;
}
