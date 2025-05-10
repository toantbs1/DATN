import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITaskHistory, defaultValue } from 'app/shared/model/task-history.model';

export const ACTION_TYPES = {
  FETCH_TASKHISTORY_LIST: 'taskHistory/FETCH_TASKHISTORY_LIST',
  FETCH_TASKHISTORY: 'taskHistory/FETCH_TASKHISTORY',
  CREATE_TASKHISTORY: 'taskHistory/CREATE_TASKHISTORY',
  UPDATE_TASKHISTORY: 'taskHistory/UPDATE_TASKHISTORY',
  DELETE_TASKHISTORY: 'taskHistory/DELETE_TASKHISTORY',
  RESET: 'taskHistory/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITaskHistory>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type TaskHistoryState = Readonly<typeof initialState>;

// Reducer

export default (state: TaskHistoryState = initialState, action): TaskHistoryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TASKHISTORY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TASKHISTORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_TASKHISTORY):
    case REQUEST(ACTION_TYPES.UPDATE_TASKHISTORY):
    case REQUEST(ACTION_TYPES.DELETE_TASKHISTORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_TASKHISTORY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TASKHISTORY):
    case FAILURE(ACTION_TYPES.CREATE_TASKHISTORY):
    case FAILURE(ACTION_TYPES.UPDATE_TASKHISTORY):
    case FAILURE(ACTION_TYPES.DELETE_TASKHISTORY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TASKHISTORY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_TASKHISTORY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_TASKHISTORY):
    case SUCCESS(ACTION_TYPES.UPDATE_TASKHISTORY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_TASKHISTORY):
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

const apiUrl = 'api/task-histories';

// Actions

export const getEntities: ICrudGetAllAction<ITaskHistory> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TASKHISTORY_LIST,
    payload: axios.get<ITaskHistory>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ITaskHistory> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TASKHISTORY,
    payload: axios.get<ITaskHistory>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ITaskHistory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TASKHISTORY,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITaskHistory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TASKHISTORY,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITaskHistory> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TASKHISTORY,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
