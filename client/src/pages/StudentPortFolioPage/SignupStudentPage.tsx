import React, { useCallback, useEffect } from "react";
import { Button, Form, Input, message } from "antd";
import { layout, tailLayout } from "../StudentPage/EditStudentPage";
import { useDispatch, useSelector } from "react-redux";
import { ROOTSTATE } from "../../redux_folder/reducers/root";
import {
  createStudentPortfolioBasicInfoAcion,
  resetStudentPortfolioMessageAcion,
} from "../../redux_folder/actions/studentPortfolio/index";
import { Redirect } from "react-router";

const SignupStudentPage = () => {
  const [form] = Form.useForm();
  const dispatch = useDispatch();
  const {
    integrationErrorMessage,
    integrationRequestMessage,
    integrationSucessMessage,
    basicStudentInfo,
  } = useSelector((state: ROOTSTATE) => state.studentPortfolio);
  const onFinishForm = useCallback(value => {
    console.log(value);
    dispatch(
      createStudentPortfolioBasicInfoAcion.ACTION.REQUEST({
        name: value.name,
        password: value.password,
        studentCode: parseInt(value.studentCode),
      })
    );
  }, []);

  useEffect(() => {
    if (integrationSucessMessage) {
      message.success(integrationSucessMessage);
    }
    if (integrationErrorMessage) {
      message.error(integrationErrorMessage);
    }
    dispatch(resetStudentPortfolioMessageAcion());
  }, [
    integrationErrorMessage,
    integrationRequestMessage,
    integrationSucessMessage,
  ]);

  if (basicStudentInfo && integrationSucessMessage) {
    return <Redirect to="/portfolio/valid" />;
  }

  return (
    <>
      {" "}
      <Form
        {...layout}
        form={form}
        name="control-hooks"
        onFinish={onFinishForm}
      >
        <Form.Item
          name="name"
          label="이름"
          rules={[
            {
              required: true,
              message: "이름을 입력해주세요",
            },
          ]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          name="studentCode"
          label="학번"
          rules={[
            { required: true, message: "학번을 입력하세요" },
            ({ getFieldValue }) => ({
              validator(_, value) {
                if (!value || String(value).length === 9) {
                  return Promise.resolve();
                }
                return Promise.reject(new Error("학번을 9자리를 입력해주세요"));
              },
            }),
          ]}
        >
          <Input type="number" />
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

        <Form.Item {...tailLayout}>
          <Button type="primary" htmlType="submit">
            가입
          </Button>
        </Form.Item>
      </Form>
    </>
  );
};

export default SignupStudentPage;
