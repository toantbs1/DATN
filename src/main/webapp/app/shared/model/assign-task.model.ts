import { Moment } from 'moment';

export interface IAssignTask {
  id?: number;
  taskId?: number;
  assigneeId?: number;
  assignAt?: string;
  userId?: number;
  createdDate?: string;
  createdBy?: string;
  lastModifiedDate?: string;
  lastModifiedBy?: string;
}

export const defaultValue: Readonly<IAssignTask> = {};
