import { Moment } from 'moment';

export interface ILeaveRequest {
  id?: number;
  leaveDate?: string;
  reason?: string;
  status?: string;
  approvedUserId?: number;
  approvedName?: string;
  reply?: string;
  userId?: number;
  createdDate?: string;
  createdBy?: string;
  lastModifiedDate?: string;
  lastModifiedBy?: string;
}

export const defaultValue: Readonly<ILeaveRequest> = {};
