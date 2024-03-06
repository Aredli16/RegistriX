export interface Registration {
  id?: string;
  lastName: string;
  firstName: string;
  email: string;
  phone: string;
  streetAddress: string;
  zipCode: string;
  city: string;
  country: string;
  createdBy?: string;
  createdAt?: Date;
  updatedAt?: Date;
}

export interface RegistrationPage {
  page: number;
  totalPages: number;
  totalElements: number;
  registrations: Registration[];
}
