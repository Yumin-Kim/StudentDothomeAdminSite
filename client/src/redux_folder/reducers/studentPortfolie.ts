import { StudentPortfolioStore } from "../../types/storeType";
import { LOGIN_STUDENT_PORTFOLIO } from "../actions/studentPortfolio/type";
import {
  RESET_STUDENT_PORTFOLIO_MESSAGE,
  CREATE_STUDENT_PORTFOLIO_BASIC_INFO,
} from "../actions/studentPortfolio/type";
import {
  STUDENT_PORTFOLIO_MERGE_ACIONS,
  CHECK_STDUENT_INSCHOOL,
  CREATE_STUDENT_PORTFOLIO,
  MODIFIED_STUDENT_PORTFOLIO,
} from "../actions/studentPortfolio/type";
const studentPortfolioState: StudentPortfolioStore = {
  basicStudentInfo: null,
  resultStudentPortFolio: null,
  integrationSucessMessage: null, // 통신 후 성공 메세지
  integrationErrorMessage: null, // 통신 후 에러 메세지
  integrationRequestMessage: null, // 통신 중 메세지 표시
};

const studentPortfolioReducer = (
  state = studentPortfolioState,
  action: STUDENT_PORTFOLIO_MERGE_ACIONS
): StudentPortfolioStore => {
  switch (action.type) {
    case CHECK_STDUENT_INSCHOOL.REQUEST:
    case LOGIN_STUDENT_PORTFOLIO.REQUEST:
    case MODIFIED_STUDENT_PORTFOLIO.REQUEST:
      return {
        ...state,
        integrationRequestMessage: "정보 전송중...",
      };
    case CREATE_STUDENT_PORTFOLIO_BASIC_INFO.REQUEST:
    case CREATE_STUDENT_PORTFOLIO.REQUEST:
      return {
        ...state,
        integrationRequestMessage:
          "입력하신 정보를 저장하기 위해 정보 전송중... ",
      };

    case CHECK_STDUENT_INSCHOOL.SUCCESS:
      return {
        ...state,
        integrationSucessMessage: action.payload.message,
      };
    case CREATE_STUDENT_PORTFOLIO_BASIC_INFO.SUCCESS:
      return {
        ...state,
        basicStudentInfo: action.payload.data,
        integrationSucessMessage: action.payload.message,
      };
    case LOGIN_STUDENT_PORTFOLIO.SUCCESS:
      return {
        ...state,
        resultStudentPortFolio: action.payload.data,
        integrationSucessMessage: action.payload.message,
      };

    case MODIFIED_STUDENT_PORTFOLIO.SUCCESS:
    case CREATE_STUDENT_PORTFOLIO.SUCCESS:
      return {
        ...state,
        resultStudentPortFolio: action.payload.data,
        integrationSucessMessage: action.payload.message,
      };
    case CREATE_STUDENT_PORTFOLIO_BASIC_INFO.FAILURE:
    case LOGIN_STUDENT_PORTFOLIO.FAILURE:
    case MODIFIED_STUDENT_PORTFOLIO.FAILURE:
    case CREATE_STUDENT_PORTFOLIO.FAILURE:
    case CHECK_STDUENT_INSCHOOL.FAILURE:
      return {
        ...state,
        integrationErrorMessage: action.payload.message,
        integrationRequestMessage: null,
      };
    case RESET_STUDENT_PORTFOLIO_MESSAGE:
      return {
        ...state,
        integrationErrorMessage: null,
        integrationSucessMessage: null,
        integrationRequestMessage: null,
      };
    default:
      return state;
  }
};

export default studentPortfolioReducer;
