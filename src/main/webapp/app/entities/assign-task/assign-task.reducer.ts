import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAssignTask, defaultValue } from 'app/shared/model/assign-task.model';

export const ACTION_TYPES = {
  FETCH_ASSIGNTASK_LIST: 'assignTask/FETCH_ASSIGNTASK_LIST',
  FETCH_ASSIGNTASK: 'assignTask/FETCH_ASSIGNTASK',
  CREATE_ASSIGNTASK: 'assignTask/CREATE_ASSIGNTASK',
  UPDATE_ASSIGNTASK: 'assignTask/UPDATE_ASSIGNTASK',
  DELETE_ASSIGNTASK: 'assignTask/DELETE_ASSIGNTASK',
  RESET: 'assignTask/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAssignTask>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type AssignTaskState = Readonly<typeof initialState>;

// Reducer

export default (state: AssignTaskState = initialState, action): AssignTaskState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ASSIGNTASK_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ASSIGNTASK):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ASSIGNTASK):
    case REQUEST(ACTION_TYPES.UPDATE_ASSIGNTASK):
    case REQUEST(ACTION_TYPES.DELETE_ASSIGNTASK):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ASSIGNTASK_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ASSIGNTASK):
    case FAILURE(ACTION_TYPES.CREATE_ASSIGNTASK):
    case FAILURE(ACTION_TYPES.UPDATE_ASSIGNTASK):
    case FAILURE(ACTION_TYPES.DELETE_ASSIGNTASK):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ASSIGNTASK_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_ASSIGNTASK):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ASSIGNTASK):
    case SUCCESS(ACTION_TYPES.UPDATE_ASSIGNTASK):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ASSIGNTASK):
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

const apiUrl = 'api/assign-tasks';

// Actions

export const getEntities: ICrudGetAllAction<IAssignTask> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ASSIGNTASK_LIST,
    payload: axios.get<IAssignTask>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IAssignTask> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ASSIGNTASK,
    payload: axios.get<IAssignTask>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAssignTask> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ASSIGNTASK,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAssignTask> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ASSIGNTASK,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAssignTask> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ASSIGNTASK,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
