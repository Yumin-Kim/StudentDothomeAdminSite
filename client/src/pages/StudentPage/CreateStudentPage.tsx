import React, { FC, useEffect, useState } from "react";
import { message, Form, Input, Button, Select } from "antd";
import { useSelector, useDispatch } from "react-redux";
import { ROOTSTATE } from "../../redux_folder/reducers/root";
import {
  studentSignupAction,
  changeDefaultToCreatePage,
} from "../../redux_folder/actions/student/index";
import { Redirect } from "react-router";
import { useCookies } from "react-cookie";
import { getStudentCookieInfo } from "../../redux_folder/actions/student/index";
const { Option } = Select;

export const layout = {
  labelCol: { span: 2, offset: 1 },
  wrapperCol: { span: 10 },
};

export const tailLayout = {
  wrapperCol: { offset: 2, span: 16 },
};

const inputArrayData = [
  "Your Key",
  "학번",
  "이름",
  "password",
  "confirm password",
  "Phone Number",
  "Email",
] as const;

const CreateStudentPage = () => {
  const [form] = Form.useForm();
  const {
    requestStudentInfo,
    studentInfo,
    integrationErrorMessage,
    integrationSucessMessage,
  } = useSelector((state: ROOTSTATE) => state.student);
  const [requestState, setRequestState] = useState(false);
  const [cookies, setCookie, removeCookie] = useCookies(["studentInfo"]);
  const dispatch = useDispatch();

  const onFinish = (values: any) => {
    const {
      multiCode,
      name,
      studentCode,
      password,
      phoneNumber,
      email,
      domainName,
    } = values;
    dispatch(
      studentSignupAction.ACTION.REQUEST({
        name,
        password,
        studentCode,
        email,
        phoneNumber: phoneNumber.replaceAll("-", ""),
        hashCode: multiCode,
        domainName,
      })
    );
    setRequestState(true);
  };

  useEffect(() => {
    if (integrationErrorMessage) {
      message.error(integrationErrorMessage);
      setRequestState(false);
    }
  }, [integrationErrorMessage]);
  useEffect(() => {
    form.resetFields();
    dispatch(changeDefaultToCreatePage());
  }, []);
  useEffect(() => {
    if (!studentInfo && cookies.studentInfo) {
      dispatch(getStudentCookieInfo(cookies.studentInfo));
    }
    if (studentInfo) {
      const expires = new Date();
      expires.setDate(expires.getDate() + 1);
      if (!cookies.studentInfo) {
        setCookie("studentInfo", studentInfo, {
          path: "/",
          expires,
        });
      } else {
        if (cookies.studentInfo.id !== studentInfo.id) {
          removeCookie("studentInfo");
          setCookie("studentInfo", studentInfo, {
            path: "/",
            expires,
          });
          dispatch(getStudentCookieInfo(studentInfo));
        }
      }
    }
  });
  const onReset = () => {
    form.resetFields();
  };

  const onFill = () => {
    console.log(requestStudentInfo);
    form.setFieldsValue({
      multiCode: "class2021",
      name: studentInfo?.name,
      studentCode: studentInfo?.studentCode,
      confirm: 970417,
      password: 970417,
      phoneNumber: "010-8939-8932",
      email: "seowon@email.com",
      domainName: "domain",
    });
  };

  if (integrationSucessMessage && requestState) {
    console.log("integrationSucessMessage && studentInfo?.domainName");
    return <Redirect to="/student/main" />;
  }

  return (
    <>
      <Form {...layout} form={form} name="control-hooks" onFinish={onFinish}>
        <Form.Item
          name="multiCode"
          label="Your Key 입력"
          rules={[{ required: true, message: "코드를 입력해주세요" }]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          name="studentCode"
          label="학번"
          rules={[
            {
              required: true,
              message: "이름을 입력해주세요",
            },
            ({ getFieldValue }) => ({
              validator(_, value) {
                if (studentInfo?.studentCode) {
                  if (
                    !value ||
                    String(studentInfo?.studentCode) === String(value)
                  ) {
                    return Promise.resolve();
                  }
                  return Promise.reject(
                    new Error("학번 조회한 학번과 다릅니다.")
                  );
                }
              },
            }),
          ]}
        >
          <Input type="number" placeholder={String(studentInfo?.studentCode)} />
        </Form.Item>
        <Form.Item
          name="name"
          label="이름"
          rules={[
            {
              required: true,
              message: "이름을 입력해주세요",
            },
            ({ getFieldValue }) => ({
              validator(_, value) {
                if (studentInfo?.name) {
                  if (!value || studentInfo?.name === value) {
                    return Promise.resolve();
                  }
                  return Promise.reject(
                    new Error("학번 조회한 이름과 다릅니다.")
                  );
                }
              },
            }),
          ]}
        >
          <Input placeholder={studentInfo?.name} />
        </Form.Item>
        <Form.Item
          name="password"
          label="Password"
          rules={[
            {
              required: true,
              message: "비밀 번호를 입력해주세요",
            },
          ]}
          hasFeedback
        >
          <Input.Password />
        </Form.Item>

        <Form.Item
          name="confirm"
          label="Confirm Password"
          dependencies={["password"]}
          hasFeedback
          rules={[
            {
              required: true,
              message: "비밀번호를 입력해주세요",
            },
            ({ getFieldValue }) => ({
              validator(_, value) {
                if (!value || getFieldValue("password") === value) {
                  return Promise.resolve();
                }
                return Promise.reject(
                  new Error("비밀번호가 동일하지 않습니다.")
                );
              },
            }),
          ]}
        >
          <Input.Password />
        </Form.Item>
        <Form.Item
          name="phoneNumber"
          label="Phone Number"
          rules={[
            {
              required: true,
              message: "전화번호를 입력해주세요",
            },
          ]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          name="email"
          label="E-mail"
          rules={[
            {
              type: "email",
              message: "이메일 형식에 맞지 않습니다.",
            },
            {
              required: true,
              message: "이메일을 입력해주세요",
            },
          ]}
        >
          <Input />
        </Form.Item>
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
                return Promise.reject(
                  new Error("domain 영소문자만 가능합니다")
                );
              },
            }),
          ]}
        >
          <Input type="text" />
        </Form.Item>
        <Form.Item {...tailLayout}>
          <Button type="primary" htmlType="submit">
            가입
          </Button>
          <Button htmlType="button" onClick={onReset}>
            리셋
          </Button>
          <Button type="link" htmlType="button" onClick={onFill}>
            예시 확인 하기
          </Button>
        </Form.Item>
      </Form>
    </>
  );
};

export default CreateStudentPage;
