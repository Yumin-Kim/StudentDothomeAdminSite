import { all, call } from "redux-saga/effects";
import adminSaga from "./adminSaga";
import studentSaga from './studentSaga';

export default function* RootSaga() {
  yield all([call(adminSaga),call(studentSaga)]);
}
