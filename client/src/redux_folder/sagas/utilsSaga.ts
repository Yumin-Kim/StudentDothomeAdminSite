import { put, takeLatest, all, fork, call } from "redux-saga/effects";
import {
  GET_UTIL_ADMINDATABASE,
  T_CreateOriginDomainAction,
  T_GetOriginConnectionDatabaseAction,
  T_GetOriginDomainAction,
} from "../actions/admin/utils/type";
import {
  getOriginTableConnectionAction,
  createOriginTableConnectionAction,
} from "../actions/admin/utils/index";
import axios from "axios";
import { CREATE_UTIL_ADMINDOMAIN } from "../actions/admin/utils/type";
import {
  T_DelteOriginConnectionDatabaseAction,
  DELTETE_UTIL_ADMIN_DATABASE,
} from "../actions/admin/utils/type";
import {
  deleteOriginTableConnectionAction,
  getOriginDomainAction,
} from "../actions/admin/utils/index";
import {
  GET_UTIL_ADMIN_DOMAIN,
  DELTETE_UTIL_ADMIN_DOMAIN,
  T_DeleteOriginDomainAction,
} from "../actions/admin/utils/type";
import {
  createOriginDomainAction,
  deleteOriginDomainAction,
} from "../actions/admin/utils/index";
import {
  CREATE_UTIL_ADMINDB,
  T_CreateOriginConnectionDatabaseAction,
} from "../actions/admin/utils/type";

function* sagaMethodGetDatabase(action: T_GetOriginConnectionDatabaseAction) {
  try {
    if (action.type === "REQUEST_GET_UTIL_ADMINDATABASE") {
      const { data } = yield call(getOriginTableConnectionAction.API);
      yield put(getOriginTableConnectionAction.ACTION.SUCCESS(data));
    }
  } catch (error) {
    if (axios.isAxiosError(error)) {
      yield put(
        getOriginTableConnectionAction.ACTION.FAILURE(error.response?.data)
      );
    }
  }
}

function* watchGetUtilDabase() {
  yield takeLatest(GET_UTIL_ADMINDATABASE.REQUEST, sagaMethodGetDatabase);
}

function* sagaMethodCreateDatabase(
  action: T_CreateOriginConnectionDatabaseAction
) {
  try {
    if (action.type === "REQUEST_CREATE_UTIL_ADMINDB") {
      const { data } = yield call(
        createOriginTableConnectionAction.API,
        action.payload
      );
      yield put(createOriginTableConnectionAction.ACTION.SUCCESS(data));
    }
  } catch (error) {
    if (axios.isAxiosError(error)) {
      yield put(
        createOriginTableConnectionAction.ACTION.FAILURE(error.response?.data)
      );
    }
  }
}

function* watchCreateUtilDabase() {
  yield takeLatest(CREATE_UTIL_ADMINDB.REQUEST, sagaMethodCreateDatabase);
}

function* sagaMethodDeleteDatabase(
  action: T_DelteOriginConnectionDatabaseAction
) {
  try {
    if (action.type === "REQUEST_DELTETE_UTIL_ADMIN_DATABASE") {
      const { data } = yield call(
        deleteOriginTableConnectionAction.API,
        action.payload
      );
      yield put(deleteOriginTableConnectionAction.ACTION.SUCCESS(data));
    }
  } catch (error) {
    if (axios.isAxiosError(error)) {
      yield put(
        deleteOriginTableConnectionAction.ACTION.FAILURE(error.response?.data)
      );
    }
  }
}

function* watchDeleteUtilDabase() {
  yield takeLatest(
    DELTETE_UTIL_ADMIN_DATABASE.REQUEST,
    sagaMethodDeleteDatabase
  );
}

function* sagaMethodGetDomain(action: T_GetOriginDomainAction) {
  try {
    if (action.type === "REQUEST_GET_UTIL_ADMIN_DOMAIN") {
      const { data } = yield call(getOriginDomainAction.API);
      yield put(getOriginDomainAction.ACTION.SUCCESS(data));
    }
  } catch (error) {
    if (axios.isAxiosError(error)) {
      yield put(getOriginDomainAction.ACTION.FAILURE(error.response?.data));
    }
  }
}

function* watchGetUtilDomain() {
  yield takeLatest(GET_UTIL_ADMIN_DOMAIN.REQUEST, sagaMethodGetDomain);
}

function* sagaMethodCreateDomain(action: T_CreateOriginDomainAction) {
  try {
    if (action.type === "REQUEST_CREATE_UTIL_ADMINDOMAIN") {
      const { data } = yield call(createOriginDomainAction.API, action.payload);
      yield put(createOriginDomainAction.ACTION.SUCCESS(data));
    }
  } catch (error) {
    if (axios.isAxiosError(error)) {
      yield put(createOriginDomainAction.ACTION.FAILURE(error.response?.data));
    }
  }
}

function* watchCreateUtilDomain() {
  yield takeLatest(CREATE_UTIL_ADMINDOMAIN.REQUEST, sagaMethodCreateDomain);
}

function* sagaMethodDeleteDomain(action: T_DeleteOriginDomainAction) {
  try {
    if (action.type === "REQUEST_DELTETE_UTIL_ADMIN_DOMAIN") {
      const { data } = yield call(deleteOriginDomainAction.API, action.payload);
      yield put(deleteOriginDomainAction.ACTION.SUCCESS(data));
    }
  } catch (error) {
    if (axios.isAxiosError(error)) {
      yield put(deleteOriginDomainAction.ACTION.FAILURE(error.response?.data));
    }
  }
}

function* watchDeleteUtilDomain() {
  yield takeLatest(DELTETE_UTIL_ADMIN_DOMAIN.REQUEST, sagaMethodDeleteDomain);
}
export default function* utilSaga() {
  yield all([
    fork(watchGetUtilDabase),
    fork(watchCreateUtilDabase),
    fork(watchDeleteUtilDabase),
    fork(watchGetUtilDomain),
    fork(watchCreateUtilDomain),
    fork(watchDeleteUtilDomain),
  ]);
}
