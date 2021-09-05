import React, { useCallback, useEffect, useState } from "react";
import { useParams } from "react-router";
import { Form, Input, Button, Typography } from "antd";
import {
  basicRoutePathName,
  routeToMappingData,
  IRoutePathNameComponentToEle,
} from "../types/defultType";
import { layout } from "../components/FormComponet";
import { useDispatch, useSelector } from "react-redux";
import { ROOTSTATE } from "../redux_folder/reducers/root";
import {
  loginAdminInfoAction,
  loginAdminAPI,
} from "../redux_folder/actions/admin/index";
import { Link } from "react-router-dom";
import { createAdminInfoAction } from "../redux_folder/actions/admin/index";
import {
  studentFindStudentCodeAction,
  studentModifyStudentInfoAction,
  studentLoginInfoAction,
} from "../redux_folder/actions/student/index";
const { Title } = Typography;
interface IRouteInfo {
  stubing: typeof basicRoutePathName[number];
}

const MainInputPage = () => {
  const [form] = Form.useForm();
  const { stubing } = useParams<IRouteInfo>();
  const [serilizeData, setSerilizeData] =
    useState<IRoutePathNameComponentToEle | null>(null);
  const {
    integrationErrorMessage,
    integrationRequestMessage,
    integrationSucessMessage,
  } = useSelector((state: ROOTSTATE) => state.student);
  const dispatch = useDispatch();
  useEffect(() => {
    console.log("MainInputPage useEffect");
    setSerilizeData(
      routeToMappingData.filter(value => value.pathName === stubing)[0]
    );
  }, [stubing]);
  const onFinish = useCallback(
    (values: any) => {
      console.log(values);
      if (stubing === "admin") {
        const { name, password } = values;
        dispatch(
          loginAdminInfoAction.ACTION.REQUEST({
            name,
            password,
          })
        );
      }
      console.log(stubing);
      if (stubing === "find" || stubing == "make" || stubing === "step") {
        const { name, studentCode } = values;
        if (stubing === "find") {
          dispatch(
            studentLoginInfoAction.ACTION.REQUEST({ name, studentCode })
          );
        }
        if (stubing === "step") {
          dispatch(
            studentFindStudentCodeAction.ACTION.REQUEST({ name, studentCode })
          );
        }
        if (stubing === "make") {
          console.log(name);

          dispatch(
            studentFindStudentCodeAction.ACTION.REQUEST({ name, studentCode })
          );
        }
      }

      if (stubing === "makeadmin") {
        const { name, hashCode, password, phoneNumber } = values;
        dispatch(
          createAdminInfoAction.ACTION.REQUEST({
            name,
            hashCode,
            password,
            phoneNumber,
          })
        );
      }
    },
    [stubing]
  );
  return (
    <>
      <Title level={1}>{serilizeData?.categoryName}</Title>
      <Title level={2}>{serilizeData?.categoryName}</Title>
      <Title level={3}>{integrationErrorMessage}</Title>
      <Title level={3}>{integrationRequestMessage}</Title>
      <Title level={3}>{integrationSucessMessage}</Title>
      <Form {...layout} form={form} name="control-hooks" onFinish={onFinish}>
        {" "}
        {serilizeData?.formTagInInputEl.map((value, index) => (
          <Form.Item
            name={value.name}
            label={value.label}
            rules={[{ required: true }]}
          >
            <Input type={value.inputType} />
          </Form.Item>
        ))}
        <Button type="primary" htmlType="submit">
          제출
        </Button>
        {stubing === "admin" ? (
          <Button>
            <Link to="/makeadmin">관리자 계정 생성</Link>
          </Button>
        ) : null}
      </Form>
    </>
  );
};

export default MainInputPage;
