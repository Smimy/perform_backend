import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IWorkoutGoal, defaultValue } from 'app/shared/model/workout-goal.model';

export const ACTION_TYPES = {
  FETCH_WORKOUTGOAL_LIST: 'workoutGoal/FETCH_WORKOUTGOAL_LIST',
  FETCH_WORKOUTGOAL: 'workoutGoal/FETCH_WORKOUTGOAL',
  CREATE_WORKOUTGOAL: 'workoutGoal/CREATE_WORKOUTGOAL',
  UPDATE_WORKOUTGOAL: 'workoutGoal/UPDATE_WORKOUTGOAL',
  DELETE_WORKOUTGOAL: 'workoutGoal/DELETE_WORKOUTGOAL',
  RESET: 'workoutGoal/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IWorkoutGoal>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type WorkoutGoalState = Readonly<typeof initialState>;

// Reducer

export default (state: WorkoutGoalState = initialState, action): WorkoutGoalState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_WORKOUTGOAL_LIST):
    case REQUEST(ACTION_TYPES.FETCH_WORKOUTGOAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_WORKOUTGOAL):
    case REQUEST(ACTION_TYPES.UPDATE_WORKOUTGOAL):
    case REQUEST(ACTION_TYPES.DELETE_WORKOUTGOAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_WORKOUTGOAL_LIST):
    case FAILURE(ACTION_TYPES.FETCH_WORKOUTGOAL):
    case FAILURE(ACTION_TYPES.CREATE_WORKOUTGOAL):
    case FAILURE(ACTION_TYPES.UPDATE_WORKOUTGOAL):
    case FAILURE(ACTION_TYPES.DELETE_WORKOUTGOAL):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_WORKOUTGOAL_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_WORKOUTGOAL):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_WORKOUTGOAL):
    case SUCCESS(ACTION_TYPES.UPDATE_WORKOUTGOAL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_WORKOUTGOAL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/workout-goals';

// Actions

export const getEntities: ICrudGetAllAction<IWorkoutGoal> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_WORKOUTGOAL_LIST,
  payload: axios.get<IWorkoutGoal>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IWorkoutGoal> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_WORKOUTGOAL,
    payload: axios.get<IWorkoutGoal>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IWorkoutGoal> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_WORKOUTGOAL,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IWorkoutGoal> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_WORKOUTGOAL,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IWorkoutGoal> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_WORKOUTGOAL,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
