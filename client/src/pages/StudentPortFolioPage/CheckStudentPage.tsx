import React, { useCallback, useEffect } from "react";
import { Button, Form, Input, message, Typography } from "antd";
import { layout, tailLayout } from "../StudentPage/EditStudentPage";
import { useDispatch, useSelector } from "react-redux";
import { ROOTSTATE } from "../../redux_folder/reducers/root";
import {
  findBasicStudentPortfolioInfoAction,
  loginStudentPortfolioAction,
} from "../../redux_folder/actions/studentPortfolio/index";
import { Redirect } from "react-router";
import { Link } from "react-router-dom";
import { Content } from "antd/lib/layout/layout";
import Title from "antd/lib/typography/Title";

const { Text } = Typography;

//학번 이름 조회
//조회 결과 시 존재하면 수정하는 장소로 이동
//존재 하지 않으면 생성 페이지로 이동

const CheckStudentPage = () => {
  const [form] = Form.useForm();
  const dispatch = useDispatch();
  const {
    integrationErrorMessage,
    integrationRequestMessage,
    integrationSucessMessage,
    resultStudentPortFolio,
  } = useSelector((state: ROOTSTATE) => state.studentPortfolio);

  const onFinishForm = useCallback(value => {
    dispatch(
      loginStudentPortfolioAction.ACTION.REQUEST({
        password: value.password,
        studentCode: value.studentCode,
      })
    );
  }, []);

  useEffect(() => {
    if (integrationErrorMessage) {
      message.error(integrationErrorMessage);
    }
    // console.log(resultStudentPortFolio?.brochureImage);
  }, [integrationErrorMessage, integrationSucessMessage]);
  if (integrationSucessMessage && resultStudentPortFolio?.brochureImageSrc) {
    return <Redirect to="/portfolio/default" />;
  }
  if (integrationSucessMessage && !resultStudentPortFolio?.brochureImageSrc) {
    return <Redirect to="/portfolio/create" />;
  }

  return (
    <>
      <Content className="home-page wrapper content0">
        <Title level={2}>현 4학년 정보 확인</Title>
        <Form {...layout} form={form} onFinish={onFinishForm}>
          <Form.Item
            label="학번 입력"
            name="studentCode"
            rules={[
              { required: true, message: "학번을 입력하세요" },
              ({ getFieldValue }) => ({
                validator(_, value) {
                  if (!value || String(value).length === 9) {
                    return Promise.resolve();
                  }
                  return Promise.reject(
                    new Error("학번을 9자리를 입력해주세요")
                  );
                },
              }),
            ]}
          >
            <Input type="number" />
          </Form.Item>

          <Form.Item
            name="password"
            label="비밀 번호"
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
          <Form.Item {...tailLayout}>
            <Button type="primary" htmlType="submit">
              로그인
            </Button>
            <Button type="default">
              <Link to="/portfolio/signup">회원 가입</Link>
            </Button>
          </Form.Item>
        </Form>
      </Content>
    </>
  );
};

export default CheckStudentPage;
