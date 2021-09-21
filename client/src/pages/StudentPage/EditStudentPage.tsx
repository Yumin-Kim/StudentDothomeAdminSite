import React, { useEffect, useState } from "react";
import { message, Form, Input, Button, Select } from "antd";
import { useDispatch, useSelector } from "react-redux";
import { ROOTSTATE } from "../../redux_folder/reducers/root";
import { I_ModifyStudentInfo } from "../../types/storeType";
import { keys } from "ts-transformer-keys";
import {
  studentModifyStudentInfoAction,
  getStudentCookieInfo,
} from "../../redux_folder/actions/student/index";
import { Redirect } from "react-router";
import { Link } from "react-router-dom";
import { useCookies } from "react-cookie";
import { resetIntegrataionMessage } from "../../redux_folder/actions/admin/index";
export const layout = {
  labelCol: { span: 2, offset: 1 },
  wrapperCol: { span: 10 },
};

export const tailLayout = {
  wrapperCol: { offset: 2, span: 16 },
};

interface data {}

const EditStudentPage = () => {
  const [form] = Form.useForm();
  const { studentInfo, integrationSucessMessage, integrationErrorMessage } =
    useSelector((state: ROOTSTATE) => state.student);
  const [modifiiedData, setModifiedData] = useState(false);
  const dispatch = useDispatch();
  const [cookies, setCookie, removeCookie] = useCookies(["studentInfo"]);

  const onFinish = (values: any) => {
    if (studentInfo) {
      const immtableData = [
        "id",
        "inSchool",
        "isDeleted",
        "siteInfo",
        "studentCode",
      ] as const;
      let data: I_ModifyStudentInfo = studentInfo;
      data["domainName"] = studentInfo.siteInfo.domainName;
      data["databaseName"] = studentInfo.siteInfo.databaseName;
      const notPulicateElements = (
        Object.keys(data) as Array<keyof typeof data>
      )
        .filter(keyData => values[keyData] !== data[keyData])
        .filter((valid: any) => !immtableData.includes(valid));

      console.log(notPulicateElements);
      const studentData = notPulicateElements.reduce((prev, cur, index) => {
        prev[cur] = values[cur];
        if (cur === "domainName") {
          prev.originDomain = studentInfo.siteInfo.domainName;
        }
        return prev;
      }, {} as I_ModifyStudentInfo);
      dispatch(
        studentModifyStudentInfoAction.ACTION.REQUEST({
          studentId: studentInfo.id,
          studentData,
        })
      );
    }
  };

  useEffect(() => {
    if (integrationSucessMessage) {
      setModifiedData(true);
    }
    // if (integrationErrorMessage) message.error(integrationErrorMessage);
  }, [integrationErrorMessage, integrationSucessMessage, modifiiedData]);

  useEffect(() => {
    if (!studentInfo) {
      if (cookies.studentInfo) {
        dispatch(getStudentCookieInfo(cookies.studentInfo));
      }
    } else {
      form.setFieldsValue({
        name: studentInfo?.name,
        studentCode: studentInfo?.studentCode,
        phoneNumber: studentInfo?.phoneNumber,
        email: studentInfo?.email,
        domainName: studentInfo?.siteInfo.domainName,
        databaseName: studentInfo?.siteInfo.databaseName,
        password: studentInfo?.password,
      });
    }
  }, [studentInfo]);

  if (integrationSucessMessage && modifiiedData) {
    return <Redirect to="/student/main" />;
  }

  if (!cookies.studentInfo && !studentInfo) {
    dispatch(resetIntegrataionMessage());
    return <Redirect to="/" />;
  }

  return (
    <Form {...layout} form={form} name="control-hooks" onFinish={onFinish}>
      <Form.Item name="name" label="이름">
        <Input />
      </Form.Item>
      <Form.Item name="phoneNumber" label="전화 번호">
        <Input />
      </Form.Item>
      <Form.Item name="email" label="E-mail">
        <Input />
      </Form.Item>
      {/* <Form.Item name="password" label="Password" hasFeedback>
        <Input.Password />
      </Form.Item> */}
      <Form.Item
        name="domainName"
        label="domainName"
        rules={[
          {
            required: true,
            message: "도메인을 입력해주세요",
          },
          ({ getFieldValue }) => ({
            validator(_, value) {
              if (!value || value.match(/[a-z]*/g).length === 2) {
                return Promise.resolve();
              }
              return Promise.reject(new Error("domain 영소문자만 가능합니다"));
            },
          }),
        ]}
      >
        <Input type="text" />
      </Form.Item>
      {/* <Form.Item name="databaseName" label="databaseName">
        <Input type="text" />
      </Form.Item> */}
      <Form.Item {...tailLayout}>
        <Button type="primary" htmlType="submit">
          변경
        </Button>
        <Button type="primary">
          <Link to="/student/main">이전</Link>
        </Button>
      </Form.Item>
    </Form>
  );
};

export default EditStudentPage;
