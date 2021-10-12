import React, { useCallback, useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { resetStudentPortfolioMessageAcion } from "../../redux_folder/actions/studentPortfolio";
import { ROOTSTATE } from "../../redux_folder/reducers/root";
import { message, Form, Input, Upload, Button } from "antd";

import { UploadOutlined, InboxOutlined } from "@ant-design/icons";
import { layout } from "../StudentPage/CreateStudentPage";
import { tailLayout } from "../StudentPage/EditStudentPage";
import { createStudentPortfolioAction } from "../../redux_folder/actions/studentPortfolio/index";
import { Redirect } from "react-router";

const { TextArea } = Input;

// input 제공하며 form-data를 통해서 통신 할 수 있도록
//이름 학번 유튜브 링크 소개글 프로필이미지 브로슈어이미지
const normFile = (e: any) => {
  console.log("Upload event:", e);
  if (Array.isArray(e)) {
    return e;
  }
  return e && e.fileList;
};
const CreatePortFolioPage = () => {
  const [form] = Form.useForm();
  const dispatch = useDispatch();
  const {
    integrationErrorMessage,
    integrationRequestMessage,
    integrationSucessMessage,
    resultStudentPortFolio,
    basicStudentInfo,
  } = useSelector((state: ROOTSTATE) => state.studentPortfolio);

  const [formEvnetCheck, setFormEvnetCheck] = useState(false);

  const onFinishForm = useCallback(values => {
    console.log(values);
    let formData = new FormData();
    formData.append("name", values.name);
    formData.append("studentCode", values.studentCode);
    formData.append("profileFile", values.profileFile[0].originFileObj);
    formData.append("brochureFile", values.brochureFile[0].originFileObj);
    formData.append("youtubeLink", values.youtubeLink);
    formData.append("description", values.description);
    dispatch(createStudentPortfolioAction.ACTION.REQUEST(formData));
    setFormEvnetCheck(true);
  }, []);

  useEffect(() => {
    form.setFieldsValue({
      name: resultStudentPortFolio?.name,
      studentCode: resultStudentPortFolio?.studentCode,
    });

    if (integrationRequestMessage) {
      message.info(integrationRequestMessage);
    }
    if (integrationSucessMessage) {
      message.success(integrationSucessMessage);
    }
    dispatch(resetStudentPortfolioMessageAcion(null));
  }, [integrationRequestMessage]);

  if (integrationSucessMessage && formEvnetCheck) {
    return <Redirect to="/portfolio/default" />;
  }
  if (!resultStudentPortFolio) {
    return <Redirect to="/portfolio/valid" />;
  }
  return (
    <>
      <Form {...layout} form={form} onFinish={onFinishForm}>
        <Form.Item
          name="name"
          label="이름"
          rules={[{ required: true, message: "이름을 입력해주세요" }]}
        >
          <Input disabled />
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
          <Input type="number" disabled />
        </Form.Item>
        <Form.Item
          name="youtubeLink"
          label="유튜브 주소"
          rules={[
            { required: true, message: "본인의 유튜브 주소를 입력해주세요" },
            ({ getFieldValue }) => ({
              validator(_, value) {
                if (!value || String(value).includes("embed")) {
                  return Promise.resolve();
                }
                return Promise.reject(
                  new Error(
                    "유튜브 주소는 공유하기 >> 코드 안에 iframe 태그안 src만 입력 가능합니다."
                  )
                );
              },
            }),
          ]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          name="description"
          label="소개글"
          rules={[{ required: true, message: "소개글을 입력해주세요" }]}
        >
          <TextArea />
        </Form.Item>
        <Form.Item
          name="profileFile"
          label="프로필 이미지"
          valuePropName="fileList"
          getValueFromEvent={normFile}
          rules={[{ required: true, message: "프로필이미지를 입력해주세요" }]}
        >
          <Upload listType="picture">
            <Button icon={<UploadOutlined />}>Click to upload</Button>
          </Upload>
        </Form.Item>
        <Form.Item
          name="brochureFile"
          label="브로셔 이미지 "
          valuePropName="fileList"
          getValueFromEvent={normFile}
          rules={[{ required: true, message: "브로셔이미지를 입력해주세요" }]}
        >
          <Upload listType="picture">
            <Button icon={<UploadOutlined />}>Click to upload</Button>
          </Upload>
        </Form.Item>
        <Form.Item {...tailLayout}>
          <Button type="primary" htmlType="submit">
            등록
          </Button>
        </Form.Item>
      </Form>
    </>
  );
};

export default CreatePortFolioPage;
