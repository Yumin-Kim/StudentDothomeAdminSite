import axios from "axios";
import {
  createActionAxiosGetVerionToAPIPARMA,
  I_AxiosDefaultDataFormat,
} from "../../../types/action";
import {
  CHECK_STDUENT_INSCHOOL,
  CREATE_STUDENT_PORTFOLIO,
  CREATE_STUDENT_PORTFOLIO_BASIC_INFO,
  MODIFIED_STUDENT_PORTFOLIO,
} from "./type";
import {
  I_DefaultBasicStudentPortfolioInfo,
  I_PortFolioFormData,
  I_DefaultStudentInfo,
} from "../../../types/storeType";
import { I_ModifyStudentData } from "../student/index";
import {
  I_RegisteredPortfolio,
  I_DefaultBasicStduentInfo,
} from "../../../types/storeType";
import {
  RESET_STUDENT_PORTFOLIO_MESSAGE,
  LOGIN_STUDENT_PORTFOLIO,
} from "./type";

axios.defaults.baseURL =
  process.env.NODE_ENV !== "production"
    ? "http://localhost:5050/api"
    : "http://media.seowon.ac.kr:8080/api";

// 학생 계정 생성
export const createStudentPortfolioBasicInfoAPI = async (
  params: I_DefaultBasicStduentInfo
): Promise<I_AxiosDefaultDataFormat<I_DefaultStudentInfo>> => {
  return await axios.post(`/portfolio/person/signup`, params);
};
export const createStudentPortfolioBasicInfoAcion =
  createActionAxiosGetVerionToAPIPARMA(
    CREATE_STUDENT_PORTFOLIO_BASIC_INFO,
    createStudentPortfolioBasicInfoAPI
  );
//학생 로그인
export const loginStudentPortfolioAPI = async (
  params: Omit<I_DefaultBasicStudentPortfolioInfo, "name">
): Promise<I_AxiosDefaultDataFormat<I_RegisteredPortfolio>> => {
  return await axios.post("/portfolio/person/login", params);
};
export const loginStudentPortfolioAction = createActionAxiosGetVerionToAPIPARMA(
  LOGIN_STUDENT_PORTFOLIO,
  loginStudentPortfolioAPI
);
//학생 정보 확인
export const findBasicStudentPortfolioInfoAPI = async (
  studentCode: string
): Promise<I_AxiosDefaultDataFormat<string>> => {
  return await axios.get(`/portfolio/person/${studentCode}`);
};
export const findBasicStudentPortfolioInfoAction =
  createActionAxiosGetVerionToAPIPARMA(
    CHECK_STDUENT_INSCHOOL,
    findBasicStudentPortfolioInfoAPI
  );

//학생 포트폴리오 생성
export const createStudentPortfolioAPI = async (
  formdata: FormData
): Promise<I_AxiosDefaultDataFormat<I_RegisteredPortfolio>> => {
  return await axios.post(`/portfolio/person`, formdata, {
    headers: { "Content-Type": "multipart/form-data" },
  });
};

export const createStudentPortfolioAction =
  createActionAxiosGetVerionToAPIPARMA(
    CREATE_STUDENT_PORTFOLIO,
    createStudentPortfolioAPI
  );
//학생 정보 수정
export interface I_ModfiyStudentPortfolio {
  studentCode: number;
  formData: FormData;
}
export const modifiyStudentPortfolioAPI = async ({
  studentCode,
  formData,
}: I_ModfiyStudentPortfolio): Promise<
  I_AxiosDefaultDataFormat<I_RegisteredPortfolio>
> => {
  return await axios.put(`/portfolio/person/${studentCode}`, formData, {
    headers: { "Content-Type": "multipart/form-data" },
  });
};
export const modifyStudentPortfolioAction =
  createActionAxiosGetVerionToAPIPARMA(
    MODIFIED_STUDENT_PORTFOLIO,
    modifiyStudentPortfolioAPI
  );
//메세지 리셋
export const resetStudentPortfolioMessageAcion = (param: null) => ({
  type: RESET_STUDENT_PORTFOLIO_MESSAGE,
  payload: param,
});
