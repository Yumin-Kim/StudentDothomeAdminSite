import { EntityAction } from "../../../types/action";
import { studentSignupAction, studentFindStudentCodeAction, studentLoginInfoAction, studentModifyStudentInfoAction } from './index';
//학생 로그인 api
export const LOGIN_STUDENT_INFO = {
  REQUEST: "REQUEST_LOGIN_STUDENT_INFO",
  SUCCESS: "SUCCESS_LOGIN_STUDENT_INFO",
  FAILURE: "FAILURE_LOGIN_STUDENT_INFO",
} as const;

//학생 로그아웃 api
export const LOGOUT_STUDENT_INFO = {
  REQUEST: "REQUEST_LOGOUT_STUDENT_INFO",
  SUCCESS: "SUCCESS_LOGOUT_STUDENT_INFO",
  FAILURE: "FAILURE_LOGOUT_STUDENT_INFO",
}as const;

//계정 생성 api
export const SIGNUP_STUDENT_INFO = {
  REQUEST: "REQUEST_SIGNUP_STUDENT_INFO",
  SUCCESS: "SUCCESS_SIGNUP_STUDENT_INFO",
  FAILURE: "FAILURE_SIGNUP_STUDENT_INFO",
}as const;

//학번 조회 api
export const FIND_STUDENTCODE_INFO = {
  REQUEST: "REQUEST_FIND_STUDENTCODE_INFO",
  SUCCESS: "SUCCESS_FIND_STUDENTCODE_INFO",
  FAILURE: "FAILURE_FIND_STUDENTCODE_INFO",
}as const;

//계정 수정 api
export const MODIFIED_STUDENT_INFO = {
  REQUEST: "REQUEST_MODIFIED_STUDENT_INFO",
  SUCCESS: "SUCCESS_MODIFIED_STUDENT_INFO",
  FAILURE: "FAILURE_MODIFIED_STUDENT_INFO",
}as const;

export type T_StudentSignupAction = EntityAction<typeof studentSignupAction>;
export type T_StudentFindStudentCodeAction = EntityAction<
  typeof studentFindStudentCodeAction
>;
export type T_StudentLoginAction = EntityAction<typeof studentLoginInfoAction>
export type T_StudentModifyStudentInfoAction = EntityAction<typeof studentModifyStudentInfoAction>


export type STDUENT_MERGE_ACTIONS = T_StudentFindStudentCodeAction |
  T_StudentLoginAction |
  T_StudentModifyStudentInfoAction |
  T_StudentSignupAction