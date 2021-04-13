import { Moment } from 'moment';

export interface IWorkout {
  id?: number;
  name?: string;
  date?: string;
  comment?: string;
  workoutGoalId?: number;
  userId?: number;
}

export const defaultValue: Readonly<IWorkout> = {};
