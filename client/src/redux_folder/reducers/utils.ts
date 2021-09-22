import { UtilStore } from "../../types/storeType";
import { RESET_UTIL_MESSAGE } from "../actions/admin/utils/type";
import {
  DELTETE_UTIL_ADMIN_DOMAIN,
  CREATE_UTIL_ADMINDOMAIN,
} from "../actions/admin/utils/type";
import {
  CREATE_UTIL_ADMINDB,
  DELTETE_UTIL_ADMIN_DATABASE,
} from "../actions/admin/utils/type";
import {
  UTIL_MERGE_ACTIONS,
  GET_UTIL_ADMIN_DOMAIN,
  GET_UTIL_ADMINDATABASE,
} from "../actions/admin/utils/type";

const utilInitialState: UtilStore = {
  originConnectionDatabases: null,
  originConnectionDomains: null,
  originDBConnectionRequestMessage: null, // 통신 후 성공 메세지
  originDBConnectionSucessMessage: null, // 통신 후 에러 메세지
  originDBConnectionErrorMessage: null, // 통신 중 메세지 표시
  originDomainConnectionRequestMessage: null, // 통신 후 성공 메세지
  originDomainConnectionSucessMessage: null, // 통신 후 에러 메세지
  originDomainConnectionErrorMessage: null, // 통신 중 메세지 표시
};

const utilReducer = (
  state = utilInitialState,
  action: UTIL_MERGE_ACTIONS
): UtilStore => {
  switch (action.type) {
    case CREATE_UTIL_ADMINDB.REQUEST:
    case DELTETE_UTIL_ADMIN_DATABASE.REQUEST:
    case GET_UTIL_ADMINDATABASE.REQUEST:
      return {
        ...state,
        originDBConnectionRequestMessage: "정보 요청중",
        originDBConnectionErrorMessage: null,
        originDBConnectionSucessMessage: null,
      };
    case CREATE_UTIL_ADMINDOMAIN.REQUEST:
    case DELTETE_UTIL_ADMIN_DOMAIN.REQUEST:
    case GET_UTIL_ADMIN_DOMAIN.REQUEST:
      return {
        ...state,
        originDomainConnectionRequestMessage: "정보 요청중",
        originDomainConnectionErrorMessage: null,
        originDomainConnectionSucessMessage: null,
      };

    case GET_UTIL_ADMINDATABASE.SUCCESS:
      return {
        ...state,
        originDBConnectionRequestMessage: null,
        originDBConnectionErrorMessage: null,
        originDBConnectionSucessMessage: action.payload.message,
        originConnectionDatabases: action.payload.data,
      };
    case DELTETE_UTIL_ADMIN_DATABASE.SUCCESS:
    case CREATE_UTIL_ADMINDB.SUCCESS:
      return {
        ...state,
        originDBConnectionRequestMessage: null,
        originDBConnectionErrorMessage: null,
        originDBConnectionSucessMessage: action.payload.message,
      };
    case GET_UTIL_ADMIN_DOMAIN.SUCCESS:
      return {
        ...state,
        originDomainConnectionErrorMessage: null,
        originDomainConnectionRequestMessage: null,
        originDomainConnectionSucessMessage: action.payload.message,
        originConnectionDomains: action.payload.data,
      };

    case DELTETE_UTIL_ADMIN_DOMAIN.SUCCESS:
    case CREATE_UTIL_ADMINDOMAIN.SUCCESS:
      return {
        ...state,
        originDomainConnectionErrorMessage: null,
        originDomainConnectionRequestMessage: null,
        originDomainConnectionSucessMessage: action.payload.message,
      };
    case GET_UTIL_ADMIN_DOMAIN.FAILURE:
    case DELTETE_UTIL_ADMIN_DOMAIN.FAILURE:
    case CREATE_UTIL_ADMINDOMAIN.FAILURE:
      return {
        ...state,
        originDomainConnectionErrorMessage: null,
        originDomainConnectionRequestMessage: action.payload.message,
        originDomainConnectionSucessMessage: null,
      };

    case CREATE_UTIL_ADMINDB.FAILURE:
    case DELTETE_UTIL_ADMIN_DATABASE.FAILURE:
    case GET_UTIL_ADMINDATABASE.FAILURE:
      return {
        ...state,
        originDBConnectionRequestMessage: null,
        originDBConnectionErrorMessage: action.payload.message,
        originDBConnectionSucessMessage: null,
      };

    case RESET_UTIL_MESSAGE:
      return {
        ...state,
        originDomainConnectionErrorMessage: null,
        originDomainConnectionRequestMessage: null,
        originDomainConnectionSucessMessage: null,
        originDBConnectionRequestMessage: null,
        originDBConnectionErrorMessage: null,
        originDBConnectionSucessMessage: null,
      };
    default:
      return state;
  }
};

export default utilReducer;
