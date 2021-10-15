import { Image, Typography, Form, Input, Upload, Button } from "antd";
import TextArea from "antd/lib/input/TextArea";
import React, { useCallback } from "react";
import { useDispatch, useSelector } from "react-redux";
import { ROOTSTATE } from "../../redux_folder/reducers/root";
import { layout, tailLayout } from "../StudentPage/EditStudentPage";
import { UploadOutlined } from "@ant-design/icons";
import { modifiyStudentPortfolioAPI } from "../../redux_folder/actions/studentPortfolio";
import { modifyStudentPortfolioAction } from "../../redux_folder/actions/studentPortfolio/index";
import { Redirect } from "react-router";
import Text from "antd/lib/typography/Text";

const { Title } = Typography;

// 이름 학번 유튜브 링크 소개글 프로필이미지 브로슈어이미지
// 수정 할 수 있으며 생성된 페이지로 이동 할 수 있도록 제공
const normFile = (e: any) => {
  console.log("Upload event:", e);
  if (Array.isArray(e)) {
    return e;
  }
  return e && e.fileList;
};
const ModifiedPortFolioPage = () => {
  const [form] = Form.useForm();
  const dispatch = useDispatch();
  const {
    integrationErrorMessage,
    integrationRequestMessage,
    integrationSucessMessage,
    resultStudentPortFolio,
  } = useSelector((state: ROOTSTATE) => state.studentPortfolio);

  const onFinishForm = useCallback(values => {
    let formData = new FormData();
    let validFormData = false;
    if (resultStudentPortFolio?.studentCode) {
      if (values.profileFile) {
        formData.append("n", values.profileFile[0].originFileObj);
      }
      if (values.brochureFile) {
        formData.append("brochureFile", values.brochureFile[0].originFileObj);
      }
      if (values.youtubeLink) {
        formData.append("youtubeLink", values.youtubeLink);
      }
      if (values.description) {
        formData.append("description", values.description);
      }
      if (values.email) {
        formData.append("email", values.email);
      }
      if (values.phoneNumber) {
        const phoneNumber = [
          values.phoneNumber.slice(0, 3),
          "-",
          values.phoneNumber.slice(3, 7),
          "-",
          values.phoneNumber.slice(7, values.phoneNumber.length),
        ]
          .join()
          .replaceAll(",", "");

        formData.append("phoneNumber", phoneNumber);
      }
      if (values.slogan) {
        formData.append("slogan", values.slogan);
      }
      if (values.job) {
        formData.append("job", values.job);
      }

      for (var pair of formData.entries()) {
        validFormData = true;
      }
      if (validFormData) {
        console.log("validFormData");
        dispatch(
          modifyStudentPortfolioAction.ACTION.REQUEST({
            studentCode: resultStudentPortFolio?.studentCode,
            formData,
          })
        );
      }
    }
  }, []);

  if (integrationSucessMessage) {
    return <Redirect to="/portfolio/default" />;
  }

  if (!resultStudentPortFolio) {
    return <Redirect to="/portfolio/valid" />;
  }

  return (
    <div>
      <Title level={3}>수정하기</Title>
      <Text>수정할 정보만 입력해주세요</Text>
      <Form {...layout} form={form} onFinish={onFinishForm}>
        <Form.Item
          name="slogan"
          label="슬로건"
          rules={[
            {
              required: false,
            },
          ]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          name="job"
          label="직업"
          rules={[
            {
              required: false,
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
              required: false,
            },
          ]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          name="phoneNumber"
          label="전화 번호"
          rules={[
            { required: false },
            ({ getFieldValue }) => ({
              validator(_, value) {
                if (!value || String(value).length === 11) {
                  return Promise.resolve();
                }
                return Promise.reject(
                  new Error("-제외 하고 11자리를 입력해주세요")
                );
              },
            }),
          ]}
        >
          <Input type="number" />
        </Form.Item>

        <Form.Item
          name="youtubeLink"
          label="유튜브 주소"
          rules={[
            { required: false },
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
        <Form.Item name="description" label="소개글">
          <TextArea />
        </Form.Item>
        <Form.Item
          name="profileFile"
          label="프로필 이미지"
          valuePropName="fileList"
          getValueFromEvent={normFile}
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
        >
          <Upload listType="picture">
            <Button icon={<UploadOutlined />}>Click to upload</Button>
          </Upload>
        </Form.Item>
        <Form.Item {...tailLayout}>
          <Button type="primary" htmlType="submit">
            수정
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};

export default ModifiedPortFolioPage;
