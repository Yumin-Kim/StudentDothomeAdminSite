import { EntityAction } from "../../../types/action";
import { modifiedAdminInfoAPI, modifiedAdminAction } from "./index";
import {
  searchSimliarV1CondAPI,
  searchSimliarV1CondAction,
  resetIntegrataionMessage,
} from "./index";
import {
  concurrentCreateAdminToStudentAction,
  concurrentDeleteToStudentInfoAction,
  concurrentModifiedAdminStudentInfoAction,
  createAdminAPI,
  createAdminInfoAction,
  deleteToStudentInfoAction,
  getAdminInfoPagingAction,
  getStudentInfoPagingAction,
  getStudentSiteInfoAction,
  loginAdminInfoAction,
  modifiedAdminStudentInfoAction,
  searchEqualsConditionAction,
} from "./index";
//관리자 로그인
export const LOGIN_ADMIN_INFO = {
  REQUEST: "REQUEST_LOGIN_ADMIN_ROOT",
  SUCCESS: "SUCCESS_LOGIN_ADMIN_ROOT",
  FAILURE: "FAILURE_LOGIN_ADMIN_ROOT",
} as const;

export const CREATE_ADMIN_INFO = {
  REQUEST: "REQUEST_CREATE_ADMIN_INFO",
  SUCCESS: "SUCCESS_CREATE_ADMIN_INFO",
  FAILURE: "FAILURE_CREATE_ADMIN_INFO",
} as const;

//관리자 로그아웃
export const LOGOUT_ADMIN_INFO = {
  REQUEST: "REQUEST_LOGOUT_ADMIN_INFO",
  SUCCESS: "SUCCESS_LOGOUT_ADMIN_INFO",
  FAILURE: "FAILURE_LOGOUT_ADMIN_INFO",
} as const;

//관리 수정
export const MODIFIED_ADMIN_INFO = {
  REQUEST: "REQUEST_MODIFIED_ADMIN_INFO",
  SUCCESS: "SUCCESS_MODIFIED_ADMIN_INFO",
  FAILURE: "FAILURE_MODIFIED_ADMIN_INFO",
} as const;

//관리자 학생 정보 조회 페이징
export const GET_ADMIN_STUDENT_INFO = {
  REQUEST: "REQUEST_GET_ADMIN_STUDENT_INFO",
  SUCCESS: "SUCCESS_GET_ADMIN_STUDENT_INFO",
  FAILURE: "FAILURE_GET_ADMIN_STUDENT_INFO",
} as const;

//관리자 정보 페이징
export const GET_ADMINS_PAGING_INFO = {
  REQUEST: "REQUEST_GET_ADMINS_PAGING_INFO",
  SUCCESS: "SUCCESS_GET_ADMINS_PAGING_INFO",
  FAILURE: "FAILURE_GET_ADMINS_PAGING_INFO",
} as const;

//단일 학생 정보 수정
export const MODIFIED_ADMINTOSTUDENT_INFO = {
  REQUEST: "REQUEST_MODIFIED_ADMINTOSTUDENT_INFO",
  SUCCESS: "SUCCESS_MODIFIED_ADMINTOSTUDENT_INFO",
  FAILURE: "FAILURE_MODIFIED_ADMINTOSTUDENT_INFO",
} as const;
//일괄 학생 정보 수정 API
export const MODIFIED_ADMINTOSTUDENT_INFO_CONCURRENT = {
  REQUEST: "REQUEST_MODIFIED_ADMINTOSTUDENT_INFO_CONCURRENT",
  SUCCESS: "SUCCESS_MODIFIED_ADMINTOSTUDENT_INFO_CONCURRENT",
  FAILURE: "FAILURE_MODIFIED_ADMINTOSTUDENT_INFO_CONCURRENT",
} as const;
//단일 학생 정보 저장 API
export const CREAETE_ADMINTOSTUDENTCODE_INFO = {
  REQUEST: "REQUEST_CREAETE_ADMINTOSTUDENTCODE_INFO",
  SUCCESS: "SUCCESS_CREAETE_ADMINTOSTUDENTCODE_INFO",
  FAILURE: "FAILURE_CREAETE_ADMINTOSTUDENTCODE_INFO",
} as const;

//일괄 학생 정보 저장 API
export const CREAETE_ADMINTOSTUDENTCODE_INFO_CONCURRENT = {
  REQUEST: "REQUEST_CREAETE_ADMINTOSTUDENTCODE_INFO_CONCURRENT",
  SUCCESS: "SUCCESS_CREAETE_ADMINTOSTUDENTCODE_INFO_CONCURRENT",
  FAILURE: "FAILURE_CREAETE_ADMINTOSTUDENTCODE_INFO_CONCURRENT",
} as const;

//단일 학생 정보 삭제 API
export const DELETE_ADMINTOSTUDENT_INFO = {
  REQUEST: "REQUEST_DELETE_ADMINTOSTUDENT_INFO",
  SUCCESS: "SUCCESS_DELETE_ADMINTOSTUDENT_INFO",
  FAILURE: "FAILURE_DELETE_ADMINTOSTUDENT_INFO",
} as const;

//일괄 학생 정보 삭제 API
export const DELETE_ADMINTOSTUDENT_INFO_CONCURRENT = {
  REQUEST: "REQUEST_DELETE_ADMINTOSTUDENT_INFO_CONCURRENT",
  SUCCESS: "SUCCESS_DELETE_ADMINTOSTUDENT_INFO_CONCURRENT",
  FAILURE: "FAILURE_DELETE_ADMINTOSTUDENT_INFO_CONCURRENT",
} as const;

//학생 검색 API 동일한지
export const SEACRCH_V1_EQUALS_CONDITION = {
  REQUEST: "REQUEST_SEACRCH_V1_EQUALS_CONDITION",
  SUCCESS: "SUCCESS_SEACRCH_V1_EQUALS_CONDITION",
  FAILURE: "FAILURE_SEACRCH_V1_EQUALS_CONDITION",
} as const;

//학생 검색 API 유사한 조건으로
export const SEACRCH_V1_SIMILIAR_CONDITION = {
  REQUEST: "REQUEST_SEACRCH_V1_SIMILIAR_CONDITION",
  SUCCESS: "SUCCESS_SEACRCH_V1_SIMILIAR_CONDITION",
  FAILURE: "FAILURE_SEACRCH_V1_SIMILIAR_CONDITION",
} as const;

//단일 학생 정보 확인
export const SITEINFO_ADMIN_INFO = {
  REQUEST: "REQUEST_SITEINFO_ADMIN_INFO",
  SUCCESS: "SUCCESS_SITEINFO_ADMIN_INFO",
  FAILURE: "FAILURE_SITEINFO_ADMIN_INFO",
} as const;

//util 데이터 베이스 삭제
export const DELTETE_UTIL_ADMIN_DB = {
  REQUEST: "REQUEST_DELTETE_UTIL_ADMIN_DB",
  SUCCESS: "SUCCESS_DELTETE_UTIL_ADMIN_DB",
  FAILURE: "FAILURE_DELTETE_UTIL_ADMIN_DB",
} as const;

//util 데이터 베이스 생성
export const CREATE_UTIL_ADMINDB = {
  REQUEST: "REQUEST_CREATE_UTIL_ADMINDB",
  SUCCESS: "SUCCESS_CREATE_UTIL_ADMINDB",
  FAILURE: "FAILURE_CREATE_UTIL_ADMINDB",
} as const;
//uitl 도메인 생성
export const CREATE_UTIL_ADMINDOMAIN = {
  REQUEST: "REQUEST_CREATE_UTIL_ADMINDOMAIN",
  SUCCESS: "SUCCESS_CREATE_UTIL_ADMINDOMAIN",
  FAILURE: "FAILURE_CREATE_UTIL_ADMINDOMAIN",
} as const;

//util 도메인 삭제
export const DELTETE_UTIL_ADMIN_DOMAIN = {
  REQUEST: "REQUEST_DELTETE_UTIL_ADMIN_DOMAIN",
  SUCCESS: "SUCCESS_DELTETE_UTIL_ADMIN_DOMAIN",
  FAILURE: "FAILURE_DELTETE_UTIL_ADMIN_DOMAIN",
} as const;

//리셋 메세지
export const RESET_MESSAGE = "RESET_MESSAGE" as const;

export type T_loginAdminAction = EntityAction<typeof loginAdminInfoAction>;
export type T_createAdminAction = EntityAction<typeof createAdminInfoAction>;
export type T_GetStudentInfoPagingAction = EntityAction<
  typeof getStudentInfoPagingAction
>;
export type T_GetAdminInfoPagingAction = EntityAction<
  typeof getAdminInfoPagingAction
>;
export type T_ModifiedAdminStudentInfoAction = EntityAction<
  typeof modifiedAdminStudentInfoAction
>;
export type T_ConcurrentModifiedAdminStudentInfoAction = EntityAction<
  typeof concurrentModifiedAdminStudentInfoAction
>;
export type T_CreateAdminToStudentInfoAction = EntityAction<
  typeof createAdminInfoAction
>;
export type T_ConcurrentCreateAdminToStudentInfoAction = EntityAction<
  typeof concurrentCreateAdminToStudentAction
>;
export type T_DeleteToStudentInfoAction = EntityAction<
  typeof deleteToStudentInfoAction
>;
export type T_ConcurrentDeleteToStudentInfoAction = EntityAction<
  typeof concurrentDeleteToStudentInfoAction
>;
export type T_SeatchEqualsV1Action = EntityAction<
  typeof searchEqualsConditionAction
>;
export type T_SeatchSimliarV1Action = EntityAction<
  typeof searchSimliarV1CondAction
>;
export type T_GetStudentSiteInfoAction = EntityAction<
  typeof getStudentSiteInfoAction
>;
export type T_ModifiedAdminInfoAction = EntityAction<
  typeof modifiedAdminAction
>;
export type T_ResetMessage = ReturnType<typeof resetIntegrataionMessage>;

export type ADMIN_MERGE_ACTIONS =
  | T_ModifiedAdminInfoAction
  | T_ResetMessage
  | T_loginAdminAction
  | T_createAdminAction
  | T_GetStudentInfoPagingAction
  | T_ModifiedAdminStudentInfoAction
  | T_ConcurrentModifiedAdminStudentInfoAction
  | T_CreateAdminToStudentInfoAction
  | T_ConcurrentCreateAdminToStudentInfoAction
  | T_DeleteToStudentInfoAction
  | T_ConcurrentDeleteToStudentInfoAction
  | T_SeatchEqualsV1Action
  | T_SeatchSimliarV1Action
  | T_GetStudentSiteInfoAction;
