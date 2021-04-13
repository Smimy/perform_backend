export interface ISerie {
  id?: number;
  number?: number;
  repsAchieved?: number;
  weight?: number;
  percent1RM?: number;
  restingTime?: number;
  exerciseId?: number;
}

export const defaultValue: Readonly<ISerie> = {};
