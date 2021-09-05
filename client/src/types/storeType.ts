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
  defaultAdminInfo: Omit<I_DefaultAdmin_Admin, "password"> | null;
  integrationSucessMessage: string | null; // 통신 후 성공 메세지
  integrationErrorMessage: string | null; // 통신 후 에러 메세지
  integrationRequestMessage: string | null; // 통신 중 메세지 표시
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
}
//데이터 베이스 , 도메인을 직접적으로 생성 하는 코드
export interface UtilStore {
  createDatabase: I_UtilDatabase | null;
  createDomain: I_UtilDomain | null;
  integrationSucessMessage: string | null; // 통신 후 성공 메세지
  integrationErrorMessage: string | null; // 통신 후 에러 메세지
  integrationRequestMessage: string | null; // 통신 중 메세지 표시
}

export interface I_DefaultStudentInfo {
  name: string;
  studentCode: number;
}

export interface I_StudentInfo
  extends Omit<I_CreateStudentInfo, "password" | "hashCode"> {
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
}

export interface I_SiteInfo {
  domainName: string;
  databaseName: string;
  originDomain?: string;
}

export interface I_ModifyStudentInfo
  extends Partial<I_SiteInfo>,
    Omit<Partial<I_StudentInfo>, "inSchool" | "isDeleted" | "id"> {}

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
  info: I_AllStudentInfo_Adamin[] | [];
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
