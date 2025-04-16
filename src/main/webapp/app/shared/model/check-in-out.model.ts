import { Moment } from 'moment';

export interface ICheckInOut {
  id?: number;
  checkInTime?: string;
  checkInLat?: number;
  checkInLng?: number;
  checkOutTime?: string;
  checkOutLat?: number;
  checkOutLng?: number;
  userId?: number;
  createdDate?: string;
  createdBy?: string;
  lastModifiedDate?: string;
  lastModifiedBy?: string;
}

export const defaultValue: Readonly<ICheckInOut> = {};
