import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICheckInOut, defaultValue } from 'app/shared/model/check-in-out.model';

export const ACTION_TYPES = {
  FETCH_CHECKINOUT_LIST: 'checkInOut/FETCH_CHECKINOUT_LIST',
  FETCH_CHECKINOUT: 'checkInOut/FETCH_CHECKINOUT',
  CREATE_CHECKINOUT: 'checkInOut/CREATE_CHECKINOUT',
  UPDATE_CHECKINOUT: 'checkInOut/UPDATE_CHECKINOUT',
  DELETE_CHECKINOUT: 'checkInOut/DELETE_CHECKINOUT',
  RESET: 'checkInOut/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICheckInOut>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type CheckInOutState = Readonly<typeof initialState>;

// Reducer

export default (state: CheckInOutState = initialState, action): CheckInOutState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CHECKINOUT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CHECKINOUT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CHECKINOUT):
    case REQUEST(ACTION_TYPES.UPDATE_CHECKINOUT):
    case REQUEST(ACTION_TYPES.DELETE_CHECKINOUT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CHECKINOUT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CHECKINOUT):
    case FAILURE(ACTION_TYPES.CREATE_CHECKINOUT):
    case FAILURE(ACTION_TYPES.UPDATE_CHECKINOUT):
    case FAILURE(ACTION_TYPES.DELETE_CHECKINOUT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CHECKINOUT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_CHECKINOUT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CHECKINOUT):
    case SUCCESS(ACTION_TYPES.UPDATE_CHECKINOUT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CHECKINOUT):
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

const apiUrl = 'api/check-in-outs';

// Actions

export const getEntities: ICrudGetAllAction<ICheckInOut> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CHECKINOUT_LIST,
    payload: axios.get<ICheckInOut>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ICheckInOut> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CHECKINOUT,
    payload: axios.get<ICheckInOut>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICheckInOut> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CHECKINOUT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICheckInOut> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CHECKINOUT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICheckInOut> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CHECKINOUT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
