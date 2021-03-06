import { StringLiteralLike } from "typescript";
// 어드민 관련 데이터
export interface AdminStore {
  allStudentInfo_paging: I_AllStudentInfoPaging_Admin | null; // 학생 정보 페이징
  allAdminInfo_paging: I_AllAdminPaging_Admin | null; // 관리자 정보 페이징
  loginAdminInfo: Pick<I_DefaultAdmin_Admin, "name" | "password"> | null; // 관리자 로그인 데이터
  modifyStudentInfo: Omit<
    I_AllStudentInfo_Adamin,
    "siteInfo" | "adminName"
  > | null; //단일 학생 정보 수정 데이터
  concurrentModifyStudentInfo:
    | Omit<I_AllStudentInfo_Adamin, "siteInfo" | "adminName">[]
    | []; //복수 학생 정보 수정
  modifyAdminInfo: I_DefaultAdmin_Admin | null; //관리자 정보 수정 데이터
  createDefaultStudentInfo: I_DefaultStudentInfo | null; // 단일 기본 학생 정보 입력
  createConcurrentDefaultStudentInfo:
    | I_DefaultStudentInfo[]
    | I_DefaultStudentInfo; // 복수 학생 정보 입력
  searchConditionInfo: I_SearchCondition_Admin | null; // 학생 조회 조건
  createAdminInfo: I_DefaultAdmin_Admin | null; // 관리자 정보 생성
  defaultAdminInfo: I_DefaultAdmin_Admin | null;
  integrationSucessMessage: string | null; // 통신 후 성공 메세지
  integrationErrorMessage: string | null; // 통신 후 에러 메세지
  integrationRequestMessage: string | null; // 통신 중 메세지 표시
  sortingEqualCond: null | Partial<I_SearchCondition_Admin>;
  sortingSimilarCond: null | Partial<I_SearchCondition_Admin>;
  pageSize: number;
}
// 학생 관련 데이터
export interface StudentStore {
  studentInfo: I_StudentInfo | null; // 로그인 후 정보
  requestStudentInfo: Pick<I_CreateStudentInfo, "name" | "studentCode"> | null; // 계정 조회 , 로그인 관련 정보
  getDefaultStudentInfo: Pick<
    I_CreateStudentInfo,
    "name" | "studentCode"
  > | null; // 학번 조회 후 확인 정보
  createStudent: I_CreateStudentInfo | null; // 계정 생성 정보
  modifyStudentInfo: I_ModifyStudentInfo | null; // 계정 수정 전송 정보
  integrationSucessMessage: string | null; // 통신 후 성공 메세지
  integrationErrorMessage: string | null; // 통신 후 에러 메세지
  integrationRequestMessage: string | null; // 통신 중 메세지 표시
  prevHistory: null | string;
}
//데이터 베이스 , 도메인을 직접적으로 생성 하는 코드
export interface UtilStore {
  originConnectionDatabases: { user: string }[] | null;
  originConnectionDomains: string[] | null;
  originDBConnectionRequestMessage: string | null; // 통신 후 성공 메세지
  originDBConnectionSucessMessage: string | null; // 통신 후 에러 메세지
  originDBConnectionErrorMessage: string | null; // 통신 중 메세지 표시
  originDomainConnectionRequestMessage: string | null; // 통신 후 성공 메세지
  originDomainConnectionSucessMessage: string | null; // 통신 후 에러 메세지
  originDomainConnectionErrorMessage: string | null; // 통신 중 메세지 표시
}
//졸업 작품 관리 store
export interface StudentPortfolioStore {
  basicStudentInfo: I_DefaultStudentInfo | null; //로그인 및 계정 생성시
  resultStudentPortFolio: I_RegisteredPortfolio | null; //등록 성공시 또는 수정 완료시
  integrationSucessMessage: string | null; // 통신 후 성공 메세지
  integrationErrorMessage: string | null; // 통신 후 에러 메세지
  integrationRequestMessage: string | null; // 통신 중 메세지 표시
}

export interface I_DefaultStudentInfo {
  name: string;
  studentCode: number;
}

export interface I_StudentInfo extends Omit<I_CreateStudentInfo, "hashCode"> {
  id: number;
  siteInfo: I_SiteInfo;
  inSchool: boolean;
  isDeleted: boolean;
}

export interface I_CreateStudentInfo {
  name: string;
  password: string;
  studentCode: number;
  phoneNumber: string;
  email: string;
  hashCode: string;
  domainName: string; //
}

export interface I_SiteInfo {
  domainName: string;
  databaseName: string;
  originDomain?: string;
}

export interface I_ModifyStudentInfo
  extends Partial<I_SiteInfo>,
    Omit<Partial<I_StudentInfo>, "inSchool" | "isDeleted"> {}

export interface I_AllStudentInfo_Adamin
  extends Pick<I_StudentInfo, "id" | "inSchool" | "isDeleted">,
    I_DefaultStudentInfo {
  password: string | null;
  email: string | null;
  phoneNumber: string | null;
  siteInfo: I_SiteInfo | null;
  adminName: string;
}

export interface I_AllStudentInfoPaging_Admin {
  infos: I_AllStudentInfo_Adamin[] | [];
  currentPage: number;
  totalPage: number;
  currentCount: number;
  totalCount: number;
}

export interface I_AllAdminPaging_Admin {
  adminInfos: Omit<I_DefaultAdmin_Admin, "password">[] | [];
  currentPage: number;
  totalPage: number;
  currentCount: number;
  totalCount: number;
}

export interface I_DefaultAdmin_Admin {
  id: number;
  name: string;
  hashCode: string;
  phoneNumber: string;
  password: string;
}

export interface I_SearchCondition_Admin
  extends Partial<I_StudentInfo>,
    Partial<I_SiteInfo> {
  AdminName: string;
}

export interface I_UtilDatabase {
  database: string;
  password: string;
}

export interface I_UtilDomain {
  domain: string;
  password: string;
}
/////////////////////////////////
export interface I_PortFolioFormData {
  name: string;
  studentCode: string;
  profileFile: string;
  brochureFile: string;
  youtubeLink: string;
  description: string;
}

export interface I_RegisteredPortfolio extends I_DefaultStudentInfo {
  lastModifiedAt: string;
  description: string | null;
  profileImageSrc: string | null;
  brochureImageSrc: string | null;
  youtubeLink: string | null;
  slogan: string | null;
  job: string | null;
  phoneNumber: string | null;
  email: String | null;
}
export interface I_DefaultBasicStudentPortfolioInfo
  extends I_DefaultStudentInfo {
  password: string;
}

export interface I_DefaultBasicStduentInfo
  extends I_DefaultBasicStudentPortfolioInfo {
  email: string;
  phoneNumber: string;
}
