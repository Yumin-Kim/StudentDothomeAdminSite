import axios, { AxiosError } from "axios";
import {
  MODIFIED_ADMIN_INFO,
  T_ModifiedAdminInfoAction,
} from "../actions/admin/type";
import { modifiedAdminAction } from "../actions/admin/index";
import {
  MODIFIED_STUDENT_INFO_CONCURRENT,
  T_ConcurrentModifiedAdminStudentInfoAction,
  CREAETE_ADMINTOSTUDENTCODE_INFO,
  T_CreateAdminToStudentInfoAction,
  CREAETE_ADMINTOSTUDENTCODE_INFO_CONCURRENT,
  T_ConcurrentCreateAdminToStudentInfoAction,
  T_DeleteToStudentInfoAction,
  DELETE_ADMINTOSTUDENT_INFO,
  T_ConcurrentDeleteToStudentInfoAction,
  DELETE_ADMINTOSTUDENT_INFO_CONCURRENT,
  SEACRCH_V1_EQUALS_CONDITION,
  T_SeatchEqualsV1Action,
  T_SeatchSimliarV1Action,
  SEACRCH_V1_SIMILIAR_CONDITION,
  SITEINFO_ADMIN_INFO,
  T_GetStudentSiteInfoAction,
} from "../actions/admin/type";
import {
  concurrentModifiedAdminStudentInfoAction,
  concurrentCreateAdminToStudentAction,
  deleteToStudentInfoAPI,
  deleteToStudentInfoAction,
  concurrentDeleteToStudentInfoAction,
  searchEqualsConditionAction,
  searchSimliarV1CondAction,
  getStudentSiteInfoAction,
} from "../actions/admin/index";
import {
  MODIFIED_ADMINTOSTUDENT_INFO,
  T_ModifiedAdminStudentInfoAction,
} from "../actions/admin/type";
import {
  GET_ADMIN_STUDENT_INFO,
  T_GetStudentInfoPagingAction,
} from "../actions/admin/type";
import {
  getStudentInfoPagingAction,
  getAdminInfoPagingAction,
  modifiedAdminStudentInfoAction,
} from "../actions/admin/index";
import {
  GET_ADMINS_PAGING_INFO,
  T_GetAdminInfoPagingAction,
} from "../actions/admin/type";
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
  try {
    if (action.type === "REQUEST_LOGIN_ADMIN_ROOT") {
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

function* sagaMethodGetStudentPaging(action: T_GetStudentInfoPagingAction) {
  try {
    if (action.type === "REQUEST_GET_ADMIN_STUDENT_INFO") {
      const { data } = yield call(
        getStudentInfoPagingAction.API,
        action.payload
      );
      yield put(getStudentInfoPagingAction.ACTION.SUCCESS(data));
    }
  } catch (error) {
    if (axios.isAxiosError(error)) {
      yield put(
        getStudentInfoPagingAction.ACTION.FAILURE(error.response?.data)
      );
    }
  }
}

function* watchGetStudentInfoGetPaging() {
  yield takeLatest(GET_ADMIN_STUDENT_INFO.REQUEST, sagaMethodGetStudentPaging);
}

function* sagaMethodGetAdminInfoPaging(action: T_GetAdminInfoPagingAction) {
  try {
    if (action.type === "REQUEST_GET_ADMINS_PAGING_INFO") {
      const { data } = yield call(getAdminInfoPagingAction.API, action.payload);
      yield put(getAdminInfoPagingAction.ACTION.SUCCESS(data));
    }
  } catch (error) {
    if (axios.isAxiosError(error)) {
      yield put(getAdminInfoPagingAction.ACTION.FAILURE(error.response?.data));
    }
  }
}

function* watchGetAdminIngoPaging() {
  yield takeLatest(
    GET_ADMINS_PAGING_INFO.REQUEST,
    sagaMethodGetAdminInfoPaging
  );
}
function* sagaMethodModifiedStudentInfo(
  action: T_ModifiedAdminStudentInfoAction
) {
  try {
    if (action.type === "REQUEST_MODIFIED_ADMINTOSTUDENT_INFO") {
      const { data } = yield call(
        modifiedAdminStudentInfoAction.API,
        action.payload
      );
      yield put(modifiedAdminStudentInfoAction.ACTION.SUCCESS(data));
    }
  } catch (error) {
    if (axios.isAxiosError(error)) {
      yield put(
        modifiedAdminStudentInfoAction.ACTION.FAILURE(error.response?.data)
      );
    }
  }
}

function* watchModfiedAdminStudent() {
  yield takeLatest(
    MODIFIED_ADMINTOSTUDENT_INFO.REQUEST,
    sagaMethodModifiedStudentInfo
  );
}
function* sagaMethodConcurrentModifiedStudentInfo(
  action: T_ConcurrentModifiedAdminStudentInfoAction
) {
  try {
    if (action.type === "REQUEST_MODIFIED_STUDENT_INFO_CONCURRENT") {
      const { data } = yield call(
        concurrentModifiedAdminStudentInfoAction.API,
        action.payload
      );
      yield put(concurrentModifiedAdminStudentInfoAction.ACTION.SUCCESS(data));
    }
  } catch (error) {
    if (axios.isAxiosError(error)) {
      yield put(
        concurrentModifiedAdminStudentInfoAction.ACTION.FAILURE(
          error.response?.data
        )
      );
    }
  }
}

function* watchModfiedConcurrentAdminStudent() {
  yield takeLatest(
    MODIFIED_STUDENT_INFO_CONCURRENT.REQUEST,
    sagaMethodConcurrentModifiedStudentInfo
  );
}

function* sagaMethodCreateStudentInfo(
  action: T_CreateAdminToStudentInfoAction
) {
  try {
    if (action.type === "REQUEST_CREATE_ADMIN_INFO") {
      const { data } = yield call(createAdminInfoAction.API, action.payload);
      yield put(createAdminInfoAction.ACTION.SUCCESS(data));
    }
  } catch (error) {
    if (axios.isAxiosError(error)) {
      yield put(createAdminInfoAction.ACTION.FAILURE(error.response?.data));
    }
  }
}

function* watchCreateAdminToStudent() {
  yield takeLatest(
    CREAETE_ADMINTOSTUDENTCODE_INFO.REQUEST,
    sagaMethodCreateStudentInfo
  );
}

function* sagaMethodCreateConcurrentStudentInfo(
  action: T_ConcurrentCreateAdminToStudentInfoAction
) {
  try {
    if (action.type === "REQUEST_CREAETE_ADMINTOSTUDENTCODE_INFO_CONCURRENT") {
      const { data } = yield call(
        concurrentCreateAdminToStudentAction.API,
        action.payload
      );
      yield put(concurrentCreateAdminToStudentAction.ACTION.SUCCESS(data));
    }
  } catch (error) {
    if (axios.isAxiosError(error)) {
      yield put(
        concurrentCreateAdminToStudentAction.ACTION.FAILURE(
          error.response?.data
        )
      );
    }
  }
}

function* watchCreateConcurrentAdminToStudent() {
  yield takeLatest(
    CREAETE_ADMINTOSTUDENTCODE_INFO_CONCURRENT.REQUEST,
    sagaMethodCreateConcurrentStudentInfo
  );
}

function* sagaMethodDeleteToStudentInfo(action: T_DeleteToStudentInfoAction) {
  try {
    if (action.type === "REQUEST_DELETE_ADMINTOSTUDENT_INFO") {
      const { data } = yield call(
        deleteToStudentInfoAction.API,
        action.payload
      );
      yield put(deleteToStudentInfoAction.ACTION.SUCCESS(data));
    }
  } catch (error) {
    if (axios.isAxiosError(error)) {
      yield put(deleteToStudentInfoAction.ACTION.FAILURE(error.response?.data));
    }
  }
}

function* watchDeleteToStudentInfo() {
  yield takeLatest(
    DELETE_ADMINTOSTUDENT_INFO.REQUEST,
    sagaMethodDeleteToStudentInfo
  );
}

function* sagaMethodDeleteConcurrentToStudentInfo(
  action: T_ConcurrentDeleteToStudentInfoAction
) {
  try {
    if (action.type === "REQUEST_DELETE_ADMINTOSTUDENT_INFO_CONCURRENT") {
      const { data } = yield call(
        concurrentDeleteToStudentInfoAction.API,
        action.payload
      );
      yield put(concurrentDeleteToStudentInfoAction.ACTION.SUCCESS(data));
    }
  } catch (error) {
    if (axios.isAxiosError(error)) {
      yield put(
        concurrentDeleteToStudentInfoAction.ACTION.FAILURE(error.response?.data)
      );
    }
  }
}

function* watchDeleteConcurrentToStudentInfo() {
  yield takeLatest(
    DELETE_ADMINTOSTUDENT_INFO_CONCURRENT.REQUEST,
    sagaMethodDeleteConcurrentToStudentInfo
  );
}

function* sagaMethodSearchEqaulsCond(action: T_SeatchEqualsV1Action) {
  try {
    if (action.type === "REQUEST_SEACRCH_V1_EQUALS_CONDITION") {
      const { data } = yield call(
        searchEqualsConditionAction.API,
        action.payload
      );
      yield put(searchEqualsConditionAction.ACTION.SUCCESS(data));
    }
  } catch (error) {
    if (axios.isAxiosError(error)) {
      yield put(
        searchEqualsConditionAction.ACTION.FAILURE(error.response?.data)
      );
    }
  }
}

function* watchSearchEqaulsV1Cond() {
  yield takeLatest(
    SEACRCH_V1_EQUALS_CONDITION.REQUEST,
    sagaMethodSearchEqaulsCond
  );
}

function* sagaMethodSearchSimliarCond(action: T_SeatchSimliarV1Action) {
  try {
    if (action.type === "REQUEST_SEACRCH_V1_SIMILIAR_CONDITION") {
      const { data } = yield call(
        searchSimliarV1CondAction.API,
        action.payload
      );
      yield put(searchSimliarV1CondAction.ACTION.SUCCESS(data));
    }
  } catch (error) {
    if (axios.isAxiosError(error)) {
      yield put(searchSimliarV1CondAction.ACTION.FAILURE(error.response?.data));
    }
  }
}

function* watchSearchSimliarV1Cond() {
  yield takeLatest(
    SEACRCH_V1_SIMILIAR_CONDITION.REQUEST,
    sagaMethodSearchSimliarCond
  );
}

function* sagaMethodGetSiteInfo(action: T_GetStudentSiteInfoAction) {
  try {
    if (action.type === "REQUEST_SITEINFO_ADMIN_INFO") {
      const { data } = yield call(getStudentSiteInfoAction.API, action.payload);
      yield put(getStudentSiteInfoAction.ACTION.SUCCESS(data));
    }
  } catch (error) {
    if (axios.isAxiosError(error)) {
      yield put(getStudentSiteInfoAction.ACTION.FAILURE(error.response?.data));
    }
  }
}

function* watchGetStudentSiteInfo() {
  yield takeLatest(SITEINFO_ADMIN_INFO.REQUEST, sagaMethodGetSiteInfo);
}

function* sagaMethodModifiedAdminInfo(action: T_ModifiedAdminInfoAction) {
  try {
    if (action.type === "REQUEST_MODIFIED_ADMIN_INFO") {
      const { data } = yield call(modifiedAdminAction.API, action.payload);
      yield put(modifiedAdminAction.ACTION.SUCCESS(data));
    }
  } catch (error) {
    if (axios.isAxiosError(error)) {
      yield put(modifiedAdminAction.ACTION.FAILURE(error.response?.data));
    }
  }
}

function* watchModifiedAdminInfo() {
  yield takeLatest(MODIFIED_ADMIN_INFO.REQUEST, sagaMethodModifiedAdminInfo);
}
export default function* adminSaga() {
  yield all([
    fork(watchLoginAdminSaga),
    fork(watchCreateAdminAction),
    fork(watchGetStudentInfoGetPaging),
    fork(watchGetAdminIngoPaging),
    fork(watchModfiedAdminStudent),
    fork(watchModfiedConcurrentAdminStudent),
    fork(watchCreateAdminToStudent),
    fork(watchCreateConcurrentAdminToStudent),
    fork(watchDeleteToStudentInfo),
    fork(watchDeleteConcurrentToStudentInfo),
    fork(watchSearchEqaulsV1Cond),
    fork(watchSearchSimliarV1Cond),
    fork(watchGetStudentSiteInfo),
    fork(watchModifiedAdminInfo),
  ]);
}
