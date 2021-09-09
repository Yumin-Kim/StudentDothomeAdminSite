import axios from "axios";
import {
  RESET_MESSAGE,
  SEACRCH_V1_SIMILIAR_CONDITION,
  SITEINFO_ADMIN_INFO,
} from "./type";
import { I_SiteInfo } from "../../../types/storeType";
import { MODIFIED_ADMIN_INFO } from "./type";
import {
  DELETE_ADMINTOSTUDENT_INFO_CONCURRENT,
  SEACRCH_V1_EQUALS_CONDITION,
} from "./type";
import {
  I_SearchCondition_Admin,
  I_AllStudentInfoPaging_Admin,
} from "../../../types/storeType";
import {
  CREAETE_ADMINTOSTUDENTCODE_INFO_CONCURRENT,
  GET_ADMINS_PAGING_INFO,
  MODIFIED_ADMINTOSTUDENT_INFO,
  MODIFIED_ADMINTOSTUDENT_INFO_CONCURRENT,
} from "./type";
import {
  I_AllStudentInfo_Adamin,
  I_DefaultStudentInfo,
} from "../../../types/storeType";
import {
  CREAETE_ADMINTOSTUDENTCODE_INFO,
  DELETE_ADMINTOSTUDENT_INFO,
} from "./type";
import {
  createActionAxiosGetVerionToAPIPARMA,
  I_AxiosDefaultDataFormat,
} from "../../../types/action";
import {
  I_DefaultAdmin_Admin,
  I_AllAdminPaging_Admin,
} from "../../../types/storeType";
import {
  LOGIN_ADMIN_INFO,
  CREATE_ADMIN_INFO,
  GET_ADMIN_STUDENT_INFO,
} from "./type";

axios.defaults.baseURL =
  process.env.NODE_ENV !== "production"
    ? "http://localhost:5050/api"
    : "http://localhost:8080/api";
//관리자 로그인
export const loginAdminAPI = async (
  adminDefault: Pick<I_DefaultAdmin_Admin, "name" | "password">
): Promise<
  I_AxiosDefaultDataFormat<Omit<I_DefaultAdmin_Admin, "password">>
> => {
  return await axios.post(
    `/admin/rootinfo/login?name=${adminDefault.name}&password=${adminDefault.password}`
  );
};

export const loginAdminInfoAction = createActionAxiosGetVerionToAPIPARMA(
  LOGIN_ADMIN_INFO,
  loginAdminAPI
);

// 관리자 계정 생성
export const createAdminAPI = async (
  adminInfo: I_DefaultAdmin_Admin
): Promise<
  I_AxiosDefaultDataFormat<Omit<I_DefaultAdmin_Admin, "password">>
> => {
  return await axios.post("/admin/rootinfo/", adminInfo);
};

export const createAdminInfoAction = createActionAxiosGetVerionToAPIPARMA(
  CREATE_ADMIN_INFO,
  createAdminAPI
);
//관리자 정보 수정
interface I_modifiedAdmin {
  adminId: number;
  modifiedData: Partial<Omit<I_DefaultAdmin_Admin, "id">>;
}
export const modifiedAdminInfoAPI = async (
  data: I_modifiedAdmin
): Promise<I_AxiosDefaultDataFormat<I_DefaultAdmin_Admin>> => {
  return await axios.put(`/admin/rootinfo/${data.adminId}`, data.modifiedData);
};

export const modifiedAdminAction = createActionAxiosGetVerionToAPIPARMA(
  MODIFIED_ADMIN_INFO,
  modifiedAdminInfoAPI
);

//관리자 학생 정보 조회<페이징>
export interface I_DefaultPagingParam {
  size: number | null;
  page: number | null;
  sort: I_StudentSorting | I_StudentSorting[] | null;
}

export interface I_StudentSorting {
  sortCondition: "ASC" | "DESC";
  //student Entity
  name?: string;
  studentCode?: string;
  email?: string;
  phoneNumber?: string;
  //SiteInfo Entity
  siteInfo_domainName?: string;
  siteInfo_databaseName?: string;
  //Admin Entity
  admin_name?: string;
}
function sortingNameParse(sort: I_StudentSorting, sortingContext: string) {
  (Object.keys(sort) as Array<keyof typeof sort>).map(value => {
    value.replace("_", ".");
    console.log(value);
    if (value !== "sortCondition")
      sortingContext = sortingContext.concat(
        `sort=${value},${sort.sortCondition}&`
      );
  });
  return sortingContext;
}
export const getStudentInfoPagingInfoAPI = async ({
  size,
  page,
  sort,
}: Partial<I_DefaultPagingParam>): Promise<
  I_AxiosDefaultDataFormat<I_AllStudentInfoPaging_Admin>
> => {
  let sortingContext = "&";
  let data = "";
  if (Array.isArray(sort)) {
    sort.map(sortData => (data += sortingNameParse(sortData, sortingContext)));
  } else {
    if (sort) {
      data = sortingNameParse(sort, sortingContext);
    }
  }
  console.log(data);

  data.slice(0, -1);
  return await axios.get(`/admin/studentinfo?size=${size}&page=${page}${data}`);
};

export const getStudentInfoPagingAction = createActionAxiosGetVerionToAPIPARMA(
  GET_ADMIN_STUDENT_INFO,
  getStudentInfoPagingInfoAPI
);
//관리자 정보 페이징
export const getAdminInfoPagingAPI = async ({
  page,
  size,
}: {
  page: number;
  size: number;
}) => {
  return await axios.get(`/admin/rootinfo/adminall?page=${page}&size=${size}`);
};
export const getAdminInfoPagingAction = createActionAxiosGetVerionToAPIPARMA(
  GET_ADMINS_PAGING_INFO,
  getAdminInfoPagingAPI
);

//단일 학생 정보 수정
export const modifiedAdminStudentInfoAPI = async (
  modifiedData: Omit<I_AllStudentInfo_Adamin, "siteInfo" | "adminName">
): Promise<I_AxiosDefaultDataFormat<I_AllStudentInfo_Adamin>> => {
  let concatQuertString = "";
  Object.entries(modifiedData).map((value, index) => {
    if (index !== 0) concatQuertString.concat(`&${value[0]}=${value[1]}`);
    else concatQuertString.concat(`${value[0]}=${value[1]}`);
  });
  return await axios.put(`/admin/studentinfo/student?${concatQuertString}`);
};

export const modifiedAdminStudentInfoAction =
  createActionAxiosGetVerionToAPIPARMA(
    MODIFIED_ADMINTOSTUDENT_INFO,
    modifiedAdminStudentInfoAPI
  );
//일괄 학생 정보 수정 API
export const concurrentModifiedAdminStudentInfoAPI = async (
  modifiedDataList: Omit<I_AllStudentInfo_Adamin, "siteInfo" | "adminName">[]
): Promise<I_AxiosDefaultDataFormat<I_AllStudentInfo_Adamin[]>> => {
  // let concatQuertString = "";
  // modifiedDataList.map(modifiedData => {
  // Object.entries(modifiedData).map((value, index) => {
  //   if (index !== 0) concatQuertString.concat(`&${value[0]}=${value[1]}`);
  //   else concatQuertString.concat(`${value[0]}=${value[1]}`);
  // });
  // })
  return await axios.put(`/admin/studentinfo/students`, modifiedDataList);
};

export const concurrentModifiedAdminStudentInfoAction =
  createActionAxiosGetVerionToAPIPARMA(
    MODIFIED_ADMINTOSTUDENT_INFO_CONCURRENT,
    concurrentModifiedAdminStudentInfoAPI
  );
//단일 학생 정보 저장 API
export interface I_createAdminToStudentParam extends I_DefaultStudentInfo {
  adminId: number;
}

export const createAdminToStudentInfoAPI = async ({
  name,
  studentCode,
  adminId,
}: I_createAdminToStudentParam): Promise<I_AxiosDefaultDataFormat<null>> => {
  return await axios.post(`/admin/studentinfo/insert/${adminId}`, {
    name,
    studentCode,
  });
};
export const createAdminToStudentInfoAction =
  createActionAxiosGetVerionToAPIPARMA(
    CREAETE_ADMINTOSTUDENTCODE_INFO,
    createAdminToStudentInfoAPI
  );
//일괄 학생 정보 저장 API
export interface I_ConcurrentCreateAdminToStudentParam {
  adminId: number;
  studentInfos: I_DefaultStudentInfo[];
}
export const concurrentCreateAdminToStudentAPI = async ({
  adminId,
  studentInfos,
}: I_ConcurrentCreateAdminToStudentParam): Promise<
  I_AxiosDefaultDataFormat<null>
> => {
  return await axios.post(
    `/admin/studentinfo/concurrentinsert/${adminId}`,
    studentInfos
  );
};

export const concurrentCreateAdminToStudentAction =
  createActionAxiosGetVerionToAPIPARMA(
    CREAETE_ADMINTOSTUDENTCODE_INFO_CONCURRENT,
    concurrentCreateAdminToStudentAPI
  );

//단일 학생 정보 삭제 API
export const deleteToStudentInfoAPI = async (
  studentId: number
): Promise<I_AxiosDefaultDataFormat<null>> => {
  return await axios.delete(`/admin/studentinfo/student/${studentId}`);
};

export const deleteToStudentInfoAction = createActionAxiosGetVerionToAPIPARMA(
  DELETE_ADMINTOSTUDENT_INFO,
  deleteToStudentInfoAPI
);

//일괄 학생 정보 삭제 API
export const concurrentDeleteToStudentInfoAPI = async (
  studentIds: number[]
) => {
  let concatQueryString = "";
  studentIds.map(value => {
    concatQueryString.concat(`${value},`);
  });
  concatQueryString.slice(0, -1);

  return await axios.delete(`/admin/studentinfo/students/${concatQueryString}`);
};

export const concurrentDeleteToStudentInfoAction =
  createActionAxiosGetVerionToAPIPARMA(
    DELETE_ADMINTOSTUDENT_INFO_CONCURRENT,
    concurrentDeleteToStudentInfoAPI
  );

//학생 검색 API 동일한지
interface paggableSearchCondition {
  paggable: Partial<I_DefaultPagingParam>;
  search: Partial<I_SearchCondition_Admin>;
}

//검색 관련 메소드
function seachAPIParseMethod(
  paggable: Partial<I_DefaultPagingParam>,
  concatQueryString: string
) {
  Object.entries(paggable).map(value => {
    if (!Array.isArray(value[1])) {
      sortingNameParse(value[1] as I_StudentSorting, concatQueryString);
    } else {
      concatQueryString.concat(`${value[0]}=${value[1]}`);
    }
    concatQueryString.concat("&");
  });
  concatQueryString.slice(0, -1);
}
export const searchEqualsV1CondAPI = async ({
  paggable,
  search,
}: paggableSearchCondition): Promise<
  I_AxiosDefaultDataFormat<I_AllStudentInfoPaging_Admin>
> => {
  let concatQueryString = "";

  seachAPIParseMethod(paggable, concatQueryString);
  return await axios.post(
    `/admin/studentinfo/v1/search?onChange=true&`,
    search
  );
};

export const searchEqualsConditionAction = createActionAxiosGetVerionToAPIPARMA(
  SEACRCH_V1_EQUALS_CONDITION,
  searchEqualsV1CondAPI
);
//학생 검색 API 유사한 조건으로
export const searchSimliarV1CondAPI = async ({
  paggable,
  search,
}: paggableSearchCondition): Promise<
  I_AxiosDefaultDataFormat<I_AllStudentInfoPaging_Admin>
> => {
  let concatQueryString = "";
  seachAPIParseMethod(paggable, concatQueryString);
  console.log("search", search);

  return await axios.post(
    `/admin/studentinfo/v1/search?onChange=false&${concatQueryString}`,
    search
  );
};

export const searchSimliarV1CondAction = createActionAxiosGetVerionToAPIPARMA(
  SEACRCH_V1_SIMILIAR_CONDITION,
  searchSimliarV1CondAPI
);

//단일 학생 사이트 정보 확인
export const getStudentSiteInfoAPI = async (
  studentId: number
): Promise<I_AxiosDefaultDataFormat<I_SiteInfo>> => {
  return await axios.get(`/admin/studentinfo/site/${studentId}`);
};

export const getStudentSiteInfoAction = createActionAxiosGetVerionToAPIPARMA(
  SITEINFO_ADMIN_INFO,
  getStudentSiteInfoAPI
);
//util 데이터 베이스 생성
//util 데이터 베이스 삭제
//util 도메인 삭제
//uitl 도메인 생성

export const resetIntegrataionMessage = () => ({
  type: RESET_MESSAGE,
  payload: null,
});
