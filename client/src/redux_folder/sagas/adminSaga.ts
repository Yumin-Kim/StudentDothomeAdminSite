import axios, { AxiosError } from "axios";
import {
  all,
  fork,
  put,
  takeEvery,
  takeLatest,
  call,
} from "redux-saga/effects";
import {
  createAdminInfoAction,
  loginAdminInfoAction,
} from "../actions/admin/index";
import {
  T_loginAdminAction,
  LOGIN_ADMIN_INFO,
  T_createAdminAction,
} from "../actions/admin/type";

function* loginAdminFunc(action: T_loginAdminAction) {
  console.log("loginAdminFunc action");
  try {
    if (action.type === "REQUEST_LOGIN_ADMIN_INFO") {
      const { data } = yield call(loginAdminInfoAction.API, action.payload);
      console.log("loginAdminFunc SAGA");
      yield put(loginAdminInfoAction.ACTION.SUCCESS(data));
    }
  } catch (error) {
    if (axios.isAxiosError(error)) {
      yield put(loginAdminInfoAction.ACTION.FAILURE(error.response?.data));
    }
  }
}

function* watchLoginAdminSaga() {
  yield takeLatest(loginAdminInfoAction.ACTION.REQUEST, loginAdminFunc);
}

function* createAdminFunc(action: T_createAdminAction) {
  try {
    if (action.type === "REQUEST_CREATE_ADMIN_INFO") {
      const { data } = yield call(createAdminInfoAction.API, action.payload);
      console.log("createAdmin logic saga");
      yield put(createAdminInfoAction.ACTION.SUCCESS(data));
    }
  } catch (error) {
    if (axios.isAxiosError(error)) {
      yield put(createAdminInfoAction.ACTION.FAILURE(error.response?.data));
    }
  }
}

function* watchCreateAdminAction() {
  yield takeLatest(createAdminInfoAction.ACTION.REQUEST, createAdminFunc);
}

export default function* adminSaga() {
  yield all([fork(watchLoginAdminSaga), fork(watchCreateAdminAction)]);
}
