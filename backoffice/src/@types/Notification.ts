export interface Notification {
  id: string;
  title: string;
  message: string;
  sender: string;
  receiver: string;
  type: string;
  status: string;
  createdAt: string;
  updatedAt: string;
}

export interface NotificationPage {
  page: number;
  totalPages: number;
  totalElements: number;
  notifications: Notification[];
}


