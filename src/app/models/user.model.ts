export interface User {
  id: number;
  username: string;
  email: string;
  phone?: string;
  photo?: string;
  role: string;
  isActive?: boolean;
  createdAt?: string;
}

export interface AuthResponse {
  token: string;
  type: string;
  id: number;
  username: string;
  email: string;
  role: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  username: string;
  email: string;
  password: string;
  role?: string;
}
