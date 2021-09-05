import { all, fork, put, takeLatest } from "@redux-saga/core/effects";
import {
  LOGIN_STUDENT_INFO,
  T_StudentLoginAction,
  SIGNUP_STUDENT_INFO,
  T_StudentSignupAction,
  T_StudentFindStudentCodeAction,
  T_StudentModifyStudentInfoAction,
  FIND_STUDENTCODE_INFO,
  MODIFIED_STUDENT_INFO,
} from "../actions/student/type";
import axios from "axios";
import {
  studentLoginInfoAction,
  studentSignupAction,
  studentFindStudentCodeAction,
  studentModifyStudentInfoAction,
} from "../actions/student/index";
import { call } from "redux-saga/effects";

function* sagaMethodLogin(action: T_StudentLoginAction) {
  try {
    if (action.type === "REQUEST_LOGIN_STUDENT_INFO") {
      const { data } = yield call(studentLoginInfoAction.API, action.payload);
      yield put(studentLoginInfoAction.ACTION.SUCCESS(data));
    }
  } catch (error) {
    if (axios.isAxiosError(error)) {
      yield put(studentLoginInfoAction.ACTION.FAILURE(error.response?.data));
    }
  }
}

function* watchStudentLogin() {
  yield takeLatest(LOGIN_STUDENT_INFO.REQUEST, sagaMethodLogin);
}

function* sagaMethodSignup(action: T_StudentSignupAction) {
  try {
    if (action.type === "REQUEST_SIGNUP_STUDENT_INFO") {
      const { data } = yield call(studentSignupAction.API, action.payload);
      yield put(studentSignupAction.ACTION.SUCCESS(data));
    }
  } catch (error) {
    if (axios.isAxiosError(error)) {
      yield put(studentSignupAction.ACTION.FAILURE(error.response?.data));
    }
  }
}

function* watchStudentSignup() {
  yield takeLatest(SIGNUP_STUDENT_INFO.REQUEST, sagaMethodSignup);
}

function* sagaMethodFindStudentCode(action: T_StudentFindStudentCodeAction) {
  console.log("sagaMethodFindStudentCode");

  try {
    if (action.type === "REQUEST_FIND_STUDENTCODE_INFO") {
      const { data } = yield call(
        studentFindStudentCodeAction.API,
        action.payload
      );
      yield put(studentFindStudentCodeAction.ACTION.SUCCESS(data));
    }
  } catch (error) {
    if (axios.isAxiosError(error)) {
      yield put(
        studentFindStudentCodeAction.ACTION.FAILURE(error.response?.data)
      );
    }
  }
}

function* watchStudentFindStudentCode() {
  yield takeLatest(FIND_STUDENTCODE_INFO.REQUEST, sagaMethodFindStudentCode);
}

function* sagaMethodModified(action: T_StudentModifyStudentInfoAction) {
  try {
    if (action.type === "REQUEST_MODIFIED_STUDENT_INFO") {
      const { data } = yield call(
        studentModifyStudentInfoAction.API,
        action.payload
      );
      yield put(studentModifyStudentInfoAction.ACTION.SUCCESS(data));
    }
  } catch (error) {
    if (axios.isAxiosError(error)) {
      yield put(
        studentModifyStudentInfoAction.ACTION.FAILURE(error.response?.data)
      );
    }
  }
}

function* watchStudentModified() {
  yield takeLatest(MODIFIED_STUDENT_INFO.REQUEST, sagaMethodModified);
}

export default function* studentSaga() {
  yield all([
    fork(watchStudentLogin),
    fork(watchStudentSignup),
    fork(watchStudentFindStudentCode),
    fork(watchStudentModified),
  ]);
}
