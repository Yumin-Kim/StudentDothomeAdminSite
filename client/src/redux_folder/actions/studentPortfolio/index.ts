import axios from "axios";
import {
  createActionAxiosGetVerionToAPIPARMA,
  I_AxiosDefaultDataFormat,
} from "../../../types/action";
import {
  CHECK_STDUENT_INSCHOOL,
  CREATE_STUDENT_PORTFOLIO,
  MODIFIED_STUDENT_PORTFOLIO,
} from "./type";
import { I_PortFolioFormData } from "../../../types/storeType";
import { I_ModifyStudentData } from "../student/index";
import { RESET_STUDENT_PORTFOLIO_MESSAGE } from "./type";

axios.defaults.baseURL =
  process.env.NODE_ENV !== "production"
    ? "http://localhost:5050/api"
    : "http://media.seowon.ac.kr:8080/api";
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
//학생 정보 생성
export const createStudentPortfolioAPI = async (
  formdata: FormData
): Promise<I_AxiosDefaultDataFormat<string>> => {
  return await axios.post(`/portfolie/person`, formdata, {
    headers: { "Content-Type": "multipart/form-data" },
  });
};

export const createStudentPortfolioAction =
  createActionAxiosGetVerionToAPIPARMA(
    CREATE_STUDENT_PORTFOLIO,
    createStudentPortfolioAPI
  );
export interface I_ModfiyStudentPortfolio {
  studentCode: number;
  formData: FormData;
}
//학생 정보 수정
export const modifiyStudentPortfolioAPI = async ({
  studentCode,
  formData,
}: I_ModfiyStudentPortfolio): Promise<I_AxiosDefaultDataFormat<string>> => {
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
