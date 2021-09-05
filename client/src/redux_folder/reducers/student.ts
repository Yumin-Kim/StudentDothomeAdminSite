import { StudentStore } from "../../types/storeType";
import studentSaga from "../sagas/studentSaga";
import {
  STDUENT_MERGE_ACTIONS,
  FIND_STUDENTCODE_INFO,
  MODIFIED_STUDENT_INFO,
  SIGNUP_STUDENT_INFO,
  LOGIN_STUDENT_INFO,
  LOGOUT_STUDENT_INFO,
} from "../actions/student/type";

const stduentInitialState: StudentStore = {
  studentInfo: null,
  requestStudentInfo: null,
  getDefaultStudentInfo: null,
  createStudent: null,
  modifyStudentInfo: null,
  integrationErrorMessage: null,
  integrationRequestMessage: null,
  integrationSucessMessage: null,
};

const studentReducer = (
  state = stduentInitialState,
  action: STDUENT_MERGE_ACTIONS
): StudentStore => {
  switch (action.type) {
    case FIND_STUDENTCODE_INFO.REQUEST:
    case MODIFIED_STUDENT_INFO.REQUEST:
    case SIGNUP_STUDENT_INFO.REQUEST:
    case LOGIN_STUDENT_INFO.REQUEST:
      return {
        ...state,
        integrationRequestMessage: "정보 전송 중 ...",
        integrationErrorMessage: null,
        integrationSucessMessage: null,
      };
    case LOGIN_STUDENT_INFO.SUCCESS:
    case SIGNUP_STUDENT_INFO.SUCCESS:
      return {
        ...state,
        studentInfo: action.payload.data,
        integrationRequestMessage: null,
        integrationSucessMessage: action.payload.message,
      };
    case FIND_STUDENTCODE_INFO.SUCCESS:
      return {
        ...state,
        requestStudentInfo: action.payload.data,
        integrationRequestMessage: null,
        integrationSucessMessage: action.payload.message,
      };
    case MODIFIED_STUDENT_INFO.SUCCESS:
      return {
        ...state,
        modifyStudentInfo: action.payload.data,
        integrationSucessMessage: action.payload.message,
        integrationRequestMessage: null,
      };
    case FIND_STUDENTCODE_INFO.FAILURE:
    case MODIFIED_STUDENT_INFO.FAILURE:
    case SIGNUP_STUDENT_INFO.FAILURE:
    case LOGIN_STUDENT_INFO.FAILURE:
      return {
        ...state,
        integrationErrorMessage: action.payload.message,
        integrationRequestMessage: null,
      };
    default:
      return state;
  }
};

export default studentReducer;
