import {
  findBasicStudentPortfolioInfoAction,
  createStudentPortfolioAction,
  modifyStudentPortfolioAction,
  resetStudentPortfolioMessageAcion,
} from "./index";
import { EntityAction } from "../../../types/action";
//학생 정보 확인
export const CHECK_STDUENT_INSCHOOL = {
  REQUEST: "REQUEST_CHECK_STDUENT_INSCHOOL",
  SUCCESS: "SUCCESS_CHECK_STDUENT_INSCHOOL",
  FAILURE: "FAILURE_CHECK_STDUENT_INSCHOOL",
} as const;
//학생 정보 생성
export const CREATE_STUDENT_PORTFOLIO = {
  REQUEST: "REQUEST_CREATE_STUDENT_PORTFOLIO",
  SUCCESS: "SUCCESS_CREATE_STUDENT_PORTFOLIO",
  FAILURE: "FAILURE_CREATE_STUDENT_PORTFOLIO",
} as const;
//학생 정보 수정
export const MODIFIED_STUDENT_PORTFOLIO = {
  REQUEST: "REQUEST_MODIFIED_STUDENT_PORTFOLIO",
  SUCCESS: "SUCCESS_MODIFIED_STUDENT_PORTFOLIO",
  FAILURE: "FAILURE_MODIFIED_STUDENT_PORTFOLIO",
} as const;
//메세지 리셋
export const RESET_STUDENT_PORTFOLIO_MESSAGE =
  "RESET_STUDENT_PORTFOLIO_MESSAGE" as const;

export type T_CheckStudentInSchoolAction = EntityAction<
  typeof findBasicStudentPortfolioInfoAction
>;
export type T_CreateStudentPortfolioAction = EntityAction<
  typeof createStudentPortfolioAction
>;
export type T_ModifyStudentPortfolioActon = EntityAction<
  typeof modifyStudentPortfolioAction
>;
export type T_ResetStudentPortfolioMessage = ReturnType<
  typeof resetStudentPortfolioMessageAcion
>;

export type STUDENT_PORTFOLIO_MERGE_ACIONS =
  | T_CheckStudentInSchoolAction
  | T_CreateStudentPortfolioAction
  | T_ModifyStudentPortfolioActon
  | T_ResetStudentPortfolioMessage;
