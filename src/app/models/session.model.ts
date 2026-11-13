export interface Session {
  id: number;
  title: string;
  description?: string;
  coachId?: number;
  coachName?: string;
  sessionDate: string;
  startTime: string;
  endTime: string;
  maxParticipants?: number;
  room?: string;
  level?: string;
  isActive: boolean;
  createdAt?: string;
}
