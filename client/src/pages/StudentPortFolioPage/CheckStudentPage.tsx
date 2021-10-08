import React, { useCallback, useEffect } from "react";
import { Button, Form, Input, message } from "antd";
import { layout, tailLayout } from "../StudentPage/EditStudentPage";
import { useDispatch, useSelector } from "react-redux";
import { ROOTSTATE } from "../../redux_folder/reducers/root";
import { findBasicStudentPortfolioInfoAction } from "../../redux_folder/actions/studentPortfolio/index";
import { Redirect } from "react-router";

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
  } = useSelector((state: ROOTSTATE) => state.studentPortfolio);

  const onFinishForm = useCallback(value => {
    console.log(value);
    dispatch(
      findBasicStudentPortfolioInfoAction.ACTION.REQUEST(value.studentCode)
    );
  }, []);

  if (integrationSucessMessage) {
    return <Redirect to="/portfolio/create" />;
  }
  if (integrationErrorMessage) {
    return <Redirect to="/portfolio/default" />;
  }

  return (
    <>
      <Form {...layout} form={form} onFinish={onFinishForm}>
        <Form.Item name="name" label="이름">
          <Input />
        </Form.Item>
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
                return Promise.reject(new Error("학번을 9자리를 입력해주세요"));
              },
            }),
          ]}
        >
          <Input type="number" />
        </Form.Item>
        <Form.Item {...tailLayout}>
          <Button type="primary" htmlType="submit">
            조회
          </Button>
        </Form.Item>
      </Form>
    </>
  );
};

export default CheckStudentPage;
