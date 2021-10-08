import {
  actionChannel,
  all,
  call,
  fork,
  put,
  takeLatest,
} from "@redux-saga/core/effects";
import axios from "axios";
import { T_ModifyStudentPortfolioActon } from "../actions/studentPortfolio/type";
import { modifyStudentPortfolioAction } from "../actions/studentPortfolio/index";
import {
  CHECK_STDUENT_INSCHOOL,
  T_CheckStudentInSchoolAction,
} from "../actions/studentPortfolio/type";
import {
  findBasicStudentPortfolioInfoAction,
  createStudentPortfolioAction,
} from "../actions/studentPortfolio/index";
import {
  CREATE_STUDENT_PORTFOLIO,
  T_CreateStudentPortfolioAction,
} from "../actions/studentPortfolio/type";

function* sagaMethodFindBasicInfo(action: T_CheckStudentInSchoolAction) {
  try {
    if (action.type === "REQUEST_CHECK_STDUENT_INSCHOOL") {
      const { data } = yield call(
        findBasicStudentPortfolioInfoAction.API,
        action.payload
      );
      yield put(findBasicStudentPortfolioInfoAction.ACTION.SUCCESS(data));
    }
  } catch (error) {
    if (axios.isAxiosError(error)) {
      yield put(
        findBasicStudentPortfolioInfoAction.ACTION.FAILURE(error.response?.data)
      );
    }
  }
}

function* watchFindBasicStudentPortfolio() {
  yield takeLatest(CHECK_STDUENT_INSCHOOL.REQUEST, sagaMethodFindBasicInfo);
}

function* sagaMethodCreateStudentPortfolio(
  action: T_CreateStudentPortfolioAction
) {
  try {
    if (action.type === "REQUEST_CREATE_STUDENT_PORTFOLIO") {
      const { data } = yield call(
        createStudentPortfolioAction.API,
        action.payload
      );
      yield put(createStudentPortfolioAction.ACTION.SUCCESS(data));
    }
  } catch (error) {
    if (axios.isAxiosError(error)) {
      yield put(
        createStudentPortfolioAction.ACTION.FAILURE(error.response?.data)
      );
    }
  }
}

function* watchCreateStudentPortofolio() {
  yield takeLatest(
    CREATE_STUDENT_PORTFOLIO.REQUEST,
    sagaMethodCreateStudentPortfolio
  );
}

function* sagaMethodModifyStudentPortfolio(
  action: T_ModifyStudentPortfolioActon
) {
  try {
    if (action.type === "REQUEST_MODIFIED_STUDENT_PORTFOLIO") {
      const { data } = yield call(
        modifyStudentPortfolioAction.API,
        action.payload
      );
      yield put(modifyStudentPortfolioAction.ACTION.SUCCESS(data));
    }
  } catch (error) {
    if (axios.isAxiosError(error)) {
      yield put(
        modifyStudentPortfolioAction.ACTION.FAILURE(error.response?.data)
      );
    }
  }
}

function* watchModifiyStudentPortofolio() {
  yield takeLatest(
    CREATE_STUDENT_PORTFOLIO.REQUEST,
    sagaMethodModifyStudentPortfolio
  );
}

export default function* stduentPortfolioSaga() {
  yield all([
    fork(watchFindBasicStudentPortfolio),
    fork(watchCreateStudentPortofolio),
    fork(watchModifiyStudentPortofolio),
  ]);
}
