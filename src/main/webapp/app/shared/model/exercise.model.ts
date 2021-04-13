export interface IExercise {
  id?: number;
  name?: string;
  number?: number;
  comment?: string;
  workoutId?: number;
  exerciseTypeId?: number;
}

export const defaultValue: Readonly<IExercise> = {};
