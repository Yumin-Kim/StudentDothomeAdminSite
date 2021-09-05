import { EntityAction } from "../../../types/action";
import {
  createAdminAPI,
  createAdminInfoAction,
  loginAdminInfoAction,
} from "./index";
//관리자 로그인
export const LOGIN_ADMIN_INFO = {
  REQUEST: "REQUEST_LOGIN_ADMIN_INFO",
  SUCCESS: "SUCCESS_LOGIN_ADMIN_INFO",
  FAILURE: "FAILURE_LOGIN_ADMIN_INFO",
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

//관리자 학생 정보 조회 페이징
export const GET_ADMIN_STUDENT_INFO = {
  REQUEST: "REQUEST_GET_ADMIN_STUDENT_INFO",
  SUCCESS: "SUCCESS_GET_ADMIN_STUDENT_INFO",
  FAILURE: "FAILURE_GET_ADMIN_STUDENT_INFO",
} as const;

//관리자 정보 페이징
export const GET_ADMINS_INFO = {
  REQUEST: "REQUEST_GET_ADMINS_INFO",
  SUCCESS: "SUCCESS_GET_ADMINS_INFO",
  FAILURE: "FAILURE_GET_ADMINS_INFO",
};

//학생 정보 수정
export const MODIFY_STUDENT_INFO = {
  REQUEST: "REQUEST_MODIFY_STUDENT_INFO",
  SUCCESS: "SUCCESS_MODIFY_STUDENT_INFO",
  FAILURE: "FAILURE_MODIFY_STUDENT_INFO",
} as const;

export type T_loginAdminAction = EntityAction<typeof loginAdminInfoAction>;
export type T_createAdminAction = EntityAction<typeof createAdminInfoAction>;

export type ADMIN_MERGE_ACTIONS = T_loginAdminAction | T_createAdminAction;
