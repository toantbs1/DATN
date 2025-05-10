import { Moment } from 'moment';

export interface ITask {
  id?: number;
  name?: string;
  description?: string;
  startTime?: string;
  latitude?: number;
  longitude?: number;
  altitude?: number;
  status?: string;
  userId?: number;
  createdDate?: string;
  createdBy?: string;
  lastModifiedDate?: string;
  lastModifiedBy?: string;
}

export const defaultValue: Readonly<ITask> = {};
