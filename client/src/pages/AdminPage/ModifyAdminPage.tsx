import React, { useCallback, useEffect } from "react";
import Navigation from "../../layouts/Navigation";
import { Button, Form, Input, message } from "antd";
import { useDispatch, useSelector } from "react-redux";
import { ROOTSTATE } from "../../redux_folder/reducers/root";
import {
  resetIntegrataionMessage,
  modifiedAdminStudentInfoAction,
  modifiedAdminAction,
} from "../../redux_folder/actions/admin/index";
import { Redirect } from "react-router";

const ModifyAdminPage = () => {
  const {
    integrationErrorMessage,
    integrationRequestMessage,
    integrationSucessMessage,
    defaultAdminInfo,
  } = useSelector((state: ROOTSTATE) => state.admin);
  const dispatch = useDispatch();
  const [form] = Form.useForm();

  const onFinishForm = useCallback((values: any) => {
    const { name, phoneNumber, hashCode, password } = values;
    dispatch(
      modifiedAdminAction.ACTION.REQUEST({
        adminId: Number(defaultAdminInfo?.id),
        modifiedData: { name, hashCode, password, phoneNumber },
      })
    );
  }, []);

  useEffect(() => {
    dispatch(resetIntegrataionMessage());
    console.log(defaultAdminInfo);
    form.setFieldsValue({
      name: defaultAdminInfo?.name,
      phoneNumber: defaultAdminInfo?.phoneNumber,
      hashCode: defaultAdminInfo?.hashCode,
      password: defaultAdminInfo?.password,
    });
  }, []);

  useEffect(() => {
    if (integrationErrorMessage) {
      message.error(integrationErrorMessage);
    }
    if (integrationSucessMessage) {
      message.success(integrationSucessMessage);
    }
    dispatch(resetIntegrataionMessage());
  }, [integrationSucessMessage, integrationErrorMessage]);
  // if (!defaultAdminInfo) {
  //   return <Redirect to="/" />;
  // }
  return (
    <>
      <Navigation />
      <Form form={form} onFinish={onFinishForm}>
        <Form.Item
          name="name"
          label="이름"
          rules={[{ required: true, message: "이름을 입력해주세요" }]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          name="phoneNumber"
          label="전화번호"
          rules={[{ required: true, message: "전화번호을 입력해주세요" }]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          name="hashCode"
          label="HashCode"
          rules={[{ required: true, message: "HashCode를 입력해주세요" }]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          name="password"
          label="비밀번호 입력"
          rules={[{ required: true, message: "password를 입력해주세요" }]}
        >
          <Input />
        </Form.Item>
        <Button htmlType="submit" type="primary">
          수정
        </Button>
      </Form>
    </>
  );
};

export default ModifyAdminPage;
