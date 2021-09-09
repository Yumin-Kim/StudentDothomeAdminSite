import { AdminStore } from "../../types/storeType";
import {
  CREAETE_ADMINTOSTUDENTCODE_INFO_CONCURRENT,
  RESET_MESSAGE,
} from "../actions/admin/type";
import {
  SEACRCH_V1_EQUALS_CONDITION,
  SEACRCH_V1_SIMILIAR_CONDITION,
} from "../actions/admin/type";
import {
  ADMIN_MERGE_ACTIONS,
  CREATE_ADMIN_INFO,
  GET_ADMIN_STUDENT_INFO,
  LOGIN_ADMIN_INFO,
} from "../actions/admin/type";

const adminIniitialState: AdminStore = {
  allStudentInfo_paging: null, // 학생 정보 페이징
  allAdminInfo_paging: null,
  loginAdminInfo: null, // 관리자 로그인 데이터
  modifyStudentInfo: null, //단일 학생 정보 수정 데이터
  concurrentModifyStudentInfo: [], //복수 학생 정보 수정
  modifyAdminInfo: null, //관리자 정보 수정 데이터
  createDefaultStudentInfo: null, // 단일 기본 학생 정보 입력
  createConcurrentDefaultStudentInfo: [], // 복수 학생 정보 입력
  searchConditionInfo: null, // 학생 조회 조건
  createAdminInfo: null,
  defaultAdminInfo: null,
  integrationSucessMessage: null, // 통신 후 성공 메세지
  integrationErrorMessage: null, // 통신 후 에러 메세지
  integrationRequestMessage: null, // 통신 중 메세지 표시
};

const adminReducer = (
  state = adminIniitialState,
  action: ADMIN_MERGE_ACTIONS
): AdminStore => {
  switch (action.type) {
    case GET_ADMIN_STUDENT_INFO.REQUEST:
    case CREATE_ADMIN_INFO.REQUEST:
    case LOGIN_ADMIN_INFO.REQUEST:
    case SEACRCH_V1_SIMILIAR_CONDITION.REQUEST:
    case CREAETE_ADMINTOSTUDENTCODE_INFO_CONCURRENT.REQUEST:
    case SEACRCH_V1_EQUALS_CONDITION.REQUEST:
      return {
        ...state,
        integrationRequestMessage: "정보 전송 중",
      };
    case LOGIN_ADMIN_INFO.SUCCESS:
      return {
        ...state,
        defaultAdminInfo: action.payload.data,
        integrationSucessMessage: action.payload.message,
        integrationRequestMessage: null,
      };
    case CREATE_ADMIN_INFO.SUCCESS:
      return {
        ...state,
        integrationSucessMessage: action.payload.message,
        defaultAdminInfo: action.payload.data,
        integrationRequestMessage: null,
      };
    case SEACRCH_V1_EQUALS_CONDITION.SUCCESS:
    case GET_ADMIN_STUDENT_INFO.SUCCESS:
    case CREAETE_ADMINTOSTUDENTCODE_INFO_CONCURRENT.SUCCESS:
    case SEACRCH_V1_SIMILIAR_CONDITION.SUCCESS:
      return {
        ...state,
        integrationRequestMessage: null,
        integrationSucessMessage: action.payload.message,
        allStudentInfo_paging: action.payload.data,
      };
    case RESET_MESSAGE:
      return {
        ...state,
        integrationSucessMessage: action.payload,
        integrationErrorMessage: action.payload,
        integrationRequestMessage: action.payload,
      };
    case SEACRCH_V1_EQUALS_CONDITION.FAILURE:
    case SEACRCH_V1_SIMILIAR_CONDITION.FAILURE:
    case CREAETE_ADMINTOSTUDENTCODE_INFO_CONCURRENT.FAILURE:
    case GET_ADMIN_STUDENT_INFO.FAILURE:
    case LOGIN_ADMIN_INFO.FAILURE:
    case CREATE_ADMIN_INFO.FAILURE:
      return {
        ...state,
        integrationErrorMessage: action.payload.message,
        integrationRequestMessage: null,
        integrationSucessMessage: null,
      };
    default:
      return state;
  }
};

export default adminReducer;
