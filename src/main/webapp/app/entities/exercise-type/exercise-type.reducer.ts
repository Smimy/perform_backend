import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IExerciseType, defaultValue } from 'app/shared/model/exercise-type.model';

export const ACTION_TYPES = {
  FETCH_EXERCISETYPE_LIST: 'exerciseType/FETCH_EXERCISETYPE_LIST',
  FETCH_EXERCISETYPE: 'exerciseType/FETCH_EXERCISETYPE',
  CREATE_EXERCISETYPE: 'exerciseType/CREATE_EXERCISETYPE',
  UPDATE_EXERCISETYPE: 'exerciseType/UPDATE_EXERCISETYPE',
  DELETE_EXERCISETYPE: 'exerciseType/DELETE_EXERCISETYPE',
  RESET: 'exerciseType/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IExerciseType>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ExerciseTypeState = Readonly<typeof initialState>;

// Reducer

export default (state: ExerciseTypeState = initialState, action): ExerciseTypeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EXERCISETYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EXERCISETYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_EXERCISETYPE):
    case REQUEST(ACTION_TYPES.UPDATE_EXERCISETYPE):
    case REQUEST(ACTION_TYPES.DELETE_EXERCISETYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_EXERCISETYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EXERCISETYPE):
    case FAILURE(ACTION_TYPES.CREATE_EXERCISETYPE):
    case FAILURE(ACTION_TYPES.UPDATE_EXERCISETYPE):
    case FAILURE(ACTION_TYPES.DELETE_EXERCISETYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EXERCISETYPE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EXERCISETYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_EXERCISETYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_EXERCISETYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_EXERCISETYPE):
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

const apiUrl = 'api/exercise-types';

// Actions

export const getEntities: ICrudGetAllAction<IExerciseType> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_EXERCISETYPE_LIST,
  payload: axios.get<IExerciseType>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IExerciseType> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EXERCISETYPE,
    payload: axios.get<IExerciseType>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IExerciseType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EXERCISETYPE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IExerciseType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EXERCISETYPE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IExerciseType> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EXERCISETYPE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
