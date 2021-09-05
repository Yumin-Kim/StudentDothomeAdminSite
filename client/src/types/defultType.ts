import {
  loginAdminInfoAction,
  createAdminInfoAction,
} from "../redux_folder/actions/admin/index";
import { EntityAction } from "./action";
export const basicRoutePathName = [
  "make",
  "find",
  "step",
  "admin",
  "makeadmin",
] as const;
export interface IRoutePathNameOfInputElement {
  name: string;
  label: string;
  inputType: "number" | "text" | "password" | "email" | "link" | "button";
  inputText: string;
  placeholder?: string;
}
export interface IRoutePathNameComponentToEle {
  pathName: typeof basicRoutePathName[number];
  categoryName: string;
  formTagInInputEl: IRoutePathNameOfInputElement[];
}
export const routeToMappingData: IRoutePathNameComponentToEle[] = [
  {
    pathName: "make",
    categoryName: "학번 확인",
    formTagInInputEl: [
      {
        name: "studentCode",
        label: "학번 입력",
        inputText: "",
        inputType: "number",
      },
      {
        name: "name",
        label: "이름 입력",
        inputText: "",
        inputType: "text",
      },
    ],
  },
  {
    pathName: "admin",
    categoryName: "어드민 로그인",

    formTagInInputEl: [
      {
        name: "name",
        label: "이름 입력",
        inputText: "",
        inputType: "text",
      },
      {
        name: "password",
        label: "비밀 번호 입력",
        inputText: "",
        inputType: "password",
      },
    ],
  },
  {
    pathName: "find",
    categoryName: "계정 찾기",
    formTagInInputEl: [
      {
        name: "name",
        label: "이름 입력",
        inputText: "",
        inputType: "text",
      },
      {
        name: "studentCode",
        label: "학번 입력",
        inputText: "",
        inputType: "number",
      },
    ],
  },
  {
    pathName: "makeadmin",
    categoryName: "관리자 계정 생성",
    formTagInInputEl: [
      {
        name: "name",
        label: "이름 입력",
        inputText: "",
        inputType: "text",
      },
      {
        name: "password",
        label: "비밀번호 입력",
        inputText: "",
        inputType: "password",
      },
      {
        name: "hashCode",
        label: "루트 관리자의 code 입력",
        inputText: "",
        inputType: "text",
      },
      {
        name: "phoneNumber",
        label: "전화 번호를 입력해주세요",
        inputText: "",
        inputType: "number",
      },
    ],
  },
  {
    pathName: "step",
    categoryName: "계정 로그인",
    formTagInInputEl: [
      {
        name: "name",
        label: "이름 입략",
        inputText: "",
        inputType: "text",
      },
      {
        name: "studentCode",
        label: "학번 입력",
        inputText: "",
        inputType: "number",
      },
    ],
  },
];
