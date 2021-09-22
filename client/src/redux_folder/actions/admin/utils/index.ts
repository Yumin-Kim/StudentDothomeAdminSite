import axios from "axios";
import { I_UtilDatabase, I_UtilDomain } from "../../../../types/storeType";
import { RESET_UTIL_MESSAGE } from "./type";
import {
  GET_UTIL_ADMIN_DOMAIN,
  CREATE_UTIL_ADMINDOMAIN,
  DELTETE_UTIL_ADMIN_DOMAIN,
} from "./type";
import {
  I_AxiosDefaultDataFormat,
  createActionAxiosGetVerionToAPIPARMA,
} from "../../../../types/action";
import {
  GET_UTIL_ADMINDATABASE,
  CREATE_UTIL_ADMINDB,
  DELTETE_UTIL_ADMIN_DATABASE,
} from "./type";

axios.defaults.baseURL =
  process.env.NODE_ENV !== "production"
    ? "http://localhost:5050/api"
    : "http://media.seowon.ac.kr:8080/api";
interface I_GetOriginDatabase {
  user: string;
}
//util 데이터 베이스 생성
export const createOriginTableConnectionAPI = async (
  param: I_UtilDatabase
): Promise<I_AxiosDefaultDataFormat<null>> => {
  return await axios.post(
    `/util/db?database=${param.database}&password=${param.password}`
  );
};

export const createOriginTableConnectionAction =
  createActionAxiosGetVerionToAPIPARMA(
    CREATE_UTIL_ADMINDB,
    createOriginTableConnectionAPI
  );
//util 데이터 베이스 정보 조회
export const getOriginTableConnectionAPI = async (): Promise<
  I_AxiosDefaultDataFormat<{ user: string }[]>
> => {
  return await axios.get("/util/db");
};

export const getOriginTableConnectionAction =
  createActionAxiosGetVerionToAPIPARMA(
    GET_UTIL_ADMINDATABASE,
    getOriginTableConnectionAPI
  );

//util 데이터 베이스 삭제
export const deleteOriginTableConnectionAPI = async (
  datatbaseName: string
): Promise<I_AxiosDefaultDataFormat<null>> => {
  return await axios.delete(`/util/db?database=${datatbaseName}`);
};

export const deleteOriginTableConnectionAction =
  createActionAxiosGetVerionToAPIPARMA(
    DELTETE_UTIL_ADMIN_DATABASE,
    deleteOriginTableConnectionAPI
  );

//util 도메인 생성
export const createOriginDomainAPI = async (
  param: I_UtilDomain
): Promise<I_AxiosDefaultDataFormat<null>> => {
  return await axios.post(
    `/util/domain?domain=${param.domain}&password=${param.password}`
  );
};

export const createOriginDomainAction = createActionAxiosGetVerionToAPIPARMA(
  CREATE_UTIL_ADMINDOMAIN,
  createOriginDomainAPI
);

//util 도메인 조회
export const getOriginDomainAPI = async (): Promise<
  I_AxiosDefaultDataFormat<string[]>
> => {
  return await axios.get(`/util/domain`);
};

export const getOriginDomainAction = createActionAxiosGetVerionToAPIPARMA(
  GET_UTIL_ADMIN_DOMAIN,
  getOriginDomainAPI
);

//util 도메인 삭제
export const deleteOriginDomainAPI = async (
  domainName: string
): Promise<I_AxiosDefaultDataFormat<null>> => {
  return await axios.delete(`/util/domain?domain=${domainName}`);
};

export const deleteOriginDomainAction = createActionAxiosGetVerionToAPIPARMA(
  DELTETE_UTIL_ADMIN_DOMAIN,
  deleteOriginDomainAPI
);

//메세지 리셋
export const resetToUtilMessageAction = () => ({
  type: RESET_UTIL_MESSAGE,
  payload: null,
});
