export type APIENDPOINT<R, P extends any[]> = (...p: P) => Promise<R>;

export interface I_AxiosDefaultDataFormat<D> {
  data: D;
  message: string;
}

export interface I_ActionType<R, S, F> {
  REQUEST: R;
  SUCCESS: S;
  FAILURE: F;
}

export const createAction = <R, S, F, PARAM extends any[], DATA>(
  action: I_ActionType<R, S, F>,
  api: APIENDPOINT<DATA, PARAM>
) => ({
  ACTION: {
    REQUEST: (data: DATA) => ({ type: action.REQUEST, payload: data }),
    SUCCESS: (data: ReturnType<APIENDPOINT<DATA, PARAM>>) => ({
      type: action.SUCCESS,
      payload: data,
    }),
    FAILURE: (error: any) => ({ type: action.FAILURE, payload: error }),
  },
  API: api,
});

export interface IEntityAction {
  ACTION: {
    REQUEST: (...p: any[]) => any;
    SUCCESS: (...p: any[]) => any;
    FAILURE: (...p: any[]) => any;
    [key: string]: (...p: any[]) => any;
  };
  API: APIENDPOINT<any, any> & APIENDPOINT<any, any>;
}

export type EntityAction<T extends IEntityAction> = ReturnType<
  T["ACTION"][keyof T["ACTION"]]
>;

type AxiosGetType_PARAM<PARMA, DATA> = (parma: PARMA) => Promise<DATA>;

export const createActionAxiosGetVerionToAPIPARMA = <R, S, F, PARAM, DATA>(
  actionType: I_ActionType<R, S, F>,
  API: AxiosGetType_PARAM<PARAM, DATA>
) => ({
  ACTION: {
    REQUEST: (param: PARAM) => ({ type: actionType.REQUEST, payload: param }),
    SUCCESS: (data: DATA) => ({ type: actionType.SUCCESS, payload: data }),
    FAILURE: (data: any) => ({ type: actionType.FAILURE, payload: data }),
  },
  API,
});
