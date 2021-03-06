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
import { useCookies } from "react-cookie";
import { getCookieInfo } from "../../redux_folder/actions/admin/index";

const ModifyAdminPage = () => {
  const {
    integrationErrorMessage,
    integrationRequestMessage,
    integrationSucessMessage,
    defaultAdminInfo,
  } = useSelector((state: ROOTSTATE) => state.admin);
  const dispatch = useDispatch();
  const [form] = Form.useForm();
  const [cookies, setCookie, removeCookie] = useCookies(["adminInfo"]);

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
    if (!defaultAdminInfo) {
      if (cookies.adminInfo) {
        dispatch(getCookieInfo(cookies.adminInfo));
      }
    } else {
      form.setFieldsValue({
        name: defaultAdminInfo?.name,
        phoneNumber: defaultAdminInfo?.phoneNumber,
        hashCode: defaultAdminInfo?.hashCode,
        password: defaultAdminInfo?.password,
      });
    }
  }, [defaultAdminInfo]);

  useEffect(() => {
    if (integrationErrorMessage) {
      message.error(integrationErrorMessage);
    }
    if (integrationSucessMessage) {
      message.success(integrationSucessMessage);
    }
    dispatch(resetIntegrataionMessage());
  }, [integrationSucessMessage, integrationErrorMessage]);

  if (!cookies.adminInfo && !defaultAdminInfo) {
    dispatch(resetIntegrataionMessage());
    return <Redirect to="/" />;
  }

  return (
    <>
      <Navigation />
      <Form form={form} onFinish={onFinishForm}>
        <Form.Item
          name="name"
          label="??????"
          rules={[{ required: true, message: "????????? ??????????????????" }]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          name="phoneNumber"
          label="????????????"
          rules={[{ required: true, message: "??????????????? ??????????????????" }]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          name="hashCode"
          label="HashCode"
          rules={[{ required: true, message: "HashCode??? ??????????????????" }]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          name="password"
          label="???????????? ??????"
          rules={[{ required: true, message: "password??? ??????????????????" }]}
        >
          <Input />
        </Form.Item>
        <Button htmlType="submit" type="primary">
          ??????
        </Button>
      </Form>
    </>
  );
};

export default ModifyAdminPage;
