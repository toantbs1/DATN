import { Moment } from 'moment';

export interface ITaskHistory {
  id?: number;
  taskId?: number;
  actionCode?: string;
  param?: string;
  userId?: number;
  createdDate?: string;
  createdBy?: string;
  lastModifiedDate?: string;
  lastModifiedBy?: string;
}

export const defaultValue: Readonly<ITaskHistory> = {};
