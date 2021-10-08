import { all, call } from "redux-saga/effects";
import adminSaga from "./adminSaga";
import stduentPortfolioSaga from "./studentPortfolioSaga";
import studentSaga from "./studentSaga";
import utilSaga from "./utilsSaga";

export default function* RootSaga() {
  yield all([
    call(adminSaga),
    call(studentSaga),
    call(utilSaga),
    call(stduentPortfolioSaga),
  ]);
}
