import { EntityAction } from "../../../../types/action";
import { resetToUtilMessageAction } from "./index";
import {
  createOriginTableConnectionAction,
  getOriginDomainAction,
  getOriginTableConnectionAction,
  deleteOriginTableConnectionAction,
  createOriginDomainAction,
  deleteOriginDomainAction,
} from "./index";
//util 데이터 베이스 생성
export const CREATE_UTIL_ADMINDB = {
  REQUEST: "REQUEST_CREATE_UTIL_ADMINDB",
  SUCCESS: "SUCCESS_CREATE_UTIL_ADMINDB",
  FAILURE: "FAILURE_CREATE_UTIL_ADMINDB",
} as const;

//util 데이터 베이스 정보 조회
export const GET_UTIL_ADMINDATABASE = {
  REQUEST: "REQUEST_GET_UTIL_ADMINDATABASE",
  SUCCESS: "SUCCESS_GET_UTIL_ADMINDATABASE",
  FAILURE: "FAILURE_GET_UTIL_ADMINDATABASE",
} as const;

//util 데이터 베이스 삭제
export const DELTETE_UTIL_ADMIN_DATABASE = {
  REQUEST: "REQUEST_DELTETE_UTIL_ADMIN_DATABASE",
  SUCCESS: "SUCCESS_DELTETE_UTIL_ADMIN_DATABASE",
  FAILURE: "FAILURE_DELTETE_UTIL_ADMIN_DATABASE",
} as const;

//util 도메인 생성
export const CREATE_UTIL_ADMINDOMAIN = {
  REQUEST: "REQUEST_CREATE_UTIL_ADMINDOMAIN",
  SUCCESS: "SUCCESS_CREATE_UTIL_ADMINDOMAIN",
  FAILURE: "FAILURE_CREATE_UTIL_ADMINDOMAIN",
} as const;

//util 도메인 조회
export const GET_UTIL_ADMIN_DOMAIN = {
  REQUEST: "REQUEST_GET_UTIL_ADMIN_DOMAIN",
  SUCCESS: "SUCCESS_GET_UTIL_ADMIN_DOMAIN",
  FAILURE: "FAILURE_GET_UTIL_ADMIN_DOMAIN",
} as const;

//util 도메인 삭제
export const DELTETE_UTIL_ADMIN_DOMAIN = {
  REQUEST: "REQUEST_DELTETE_UTIL_ADMIN_DOMAIN",
  SUCCESS: "SUCCESS_DELTETE_UTIL_ADMIN_DOMAIN",
  FAILURE: "FAILURE_DELTETE_UTIL_ADMIN_DOMAIN",
} as const;

//메세지 리셋
export const RESET_UTIL_MESSAGE = "RESET_UTIL_MESSAGE" as const;

export type T_ResetToUtilMessage = ReturnType<typeof resetToUtilMessageAction>;

export type T_CreateOriginConnectionDatabaseAction = EntityAction<
  typeof createOriginTableConnectionAction
>;
export type T_GetOriginConnectionDatabaseAction = EntityAction<
  typeof getOriginTableConnectionAction
>;
export type T_DelteOriginConnectionDatabaseAction = EntityAction<
  typeof deleteOriginTableConnectionAction
>;

export type T_CreateOriginDomainAction = EntityAction<
  typeof createOriginDomainAction
>;
export type T_GetOriginDomainAction = EntityAction<
  typeof getOriginDomainAction
>;
export type T_DeleteOriginDomainAction = EntityAction<
  typeof deleteOriginDomainAction
>;

export type UTIL_MERGE_ACTIONS =
  | T_ResetToUtilMessage
  | T_CreateOriginConnectionDatabaseAction
  | T_GetOriginConnectionDatabaseAction
  | T_DeleteOriginDomainAction
  | T_DelteOriginConnectionDatabaseAction
  | T_GetOriginDomainAction
  | T_CreateOriginDomainAction;
