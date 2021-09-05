import axios from "axios";
import {
  createActionAxiosGetVerionToAPIPARMA,
  I_AxiosDefaultDataFormat,
} from "../../../types/action";
import { I_CreateStudentInfo, I_StudentInfo } from "../../../types/storeType";
import {
  FIND_STUDENTCODE_INFO,
  LOGIN_STUDENT_INFO,
  LOGOUT_STUDENT_INFO,
  MODIFIED_STUDENT_INFO,
  SIGNUP_STUDENT_INFO,
} from "./type";

axios.defaults.baseURL =
  process.env.NODE_ENV !== "production"
    ? "http://localhost:5050/api"
    : "http://localhost:8080/api";
//학생 로그인 api
export const studentLoginAPI = async (
  loginInfo: Pick<I_CreateStudentInfo, "name" | "studentCode">
): Promise<I_AxiosDefaultDataFormat<I_StudentInfo>> => {
  return await axios.post("/student/login", loginInfo);
};

export const studentLoginInfoAction = createActionAxiosGetVerionToAPIPARMA(
  LOGIN_STUDENT_INFO,
  studentLoginAPI
);

//계정 로그아웃 api
// export const studentLogoutAPI = async (
//   loginInfo: Pick<I_CreateStudentInfo, "name" | "password">
// ): Promise<I_StudentInfo> => {
//   return await axios.post("/student/login", loginInfo);
// };

// export const studentLogoutInfoAction = createActionAxiosGetVerionToAPIPARMA(
//   LOGOUT_STUDENT_INFO,
//   studentLogoutAPI
// );

//계정 생성 api
export const studentSignupAPI = async (
  signupData: I_CreateStudentInfo
): Promise<I_AxiosDefaultDataFormat<I_StudentInfo>> => {
  return await axios.post("/student", signupData);
};

export const studentSignupAction = createActionAxiosGetVerionToAPIPARMA(
  SIGNUP_STUDENT_INFO,
  studentSignupAPI
);

//학번 조회 api
export const studentFindStudentCodeAPI = async (
  studentData: Pick<I_CreateStudentInfo, "name" | "studentCode">
): Promise<
  I_AxiosDefaultDataFormat<Pick<I_CreateStudentInfo, "name" | "studentCode">>
> => {
  return await axios.get(
    `/student/studentcode?name=${studentData.name}&studentCode=${studentData.studentCode}`
  );
};

export const studentFindStudentCodeAction =
  createActionAxiosGetVerionToAPIPARMA(
    FIND_STUDENTCODE_INFO,
    studentFindStudentCodeAPI
  );

//계정 수정 api
export const studentModifyStudentInfoCodeAPI = async (
  studentData: Pick<I_CreateStudentInfo, "name" | "studentCode">
): Promise<
  I_AxiosDefaultDataFormat<Pick<I_CreateStudentInfo, "name" | "studentCode">>
> => {
  return await axios.get(
    `/student/studentcode?name=${studentData.name}&studentCode=${studentData.studentCode}`
  );
};

export const studentModifyStudentInfoAction =
  createActionAxiosGetVerionToAPIPARMA(
    MODIFIED_STUDENT_INFO,
    studentModifyStudentInfoCodeAPI
  );
