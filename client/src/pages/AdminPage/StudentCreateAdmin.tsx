import { Switch, Form, Input, Button, message, Upload } from "antd";
import Dragger from "antd/lib/upload/Dragger";
import React, { useCallback, useEffect, useState } from "react";
import Navigation from "../../layouts/Navigation";
import { InboxOutlined } from "@ant-design/icons";
import ExcelParingComponent from "../../components/ExcelParingComponent";
import { useDispatch, useSelector } from "react-redux";
import { resetIntegrataionMessage } from "../../redux_folder/actions/admin";
import { ROOTSTATE } from "../../redux_folder/reducers/root";
import { Redirect } from "react-router";
import { useCookies } from "react-cookie";
import {
  getCookieInfo,
  createAdminToStudentInfoAction,
} from "../../redux_folder/actions/admin/index";
import { values } from "@uifabric/utilities";
import { lowerFirst } from "lodash";

const props = {
  name: "file",
  multiple: true,
  action: "https://www.mocky.io/v2/5cc8019d300000980a055e76",
  beforeUpload: (file: any) => {
    console.log(file.type);

    if (file.type !== "text/csv") {
      message.error(`${file.name} 엑셀 파일을 업로드 하셔야합니다.`);
    }
    return file.type === "text/csv" ? true : Upload.LIST_IGNORE;
  },
  onChange(info: any) {
    const { status } = info.file;
    if (info.file.name.match(/.csv$/g)) {
      if (status !== "uploading") {
        console.log(info.file, info.fileList);
      }
      if (status === "done") {
        message.success(`${info.file.name} file uploaded successfully.`);
      }
    } else if (status === "error") {
      message.error(`${info.file.name}이며 형식에 맞지 습니다.`);
    }
  },
  onDrop(e: any) {
    console.log("Dropped files", e.dataTransfer.files);
  },
};

const StudentCreateAdmin = () => {
  const [form] = Form.useForm();
  const [switchState, setSwitchState] = useState(true);
  const [parseModeState, setParseModeState] = useState(false);
  const dispatch = useDispatch();
  const {
    defaultAdminInfo,
    integrationErrorMessage,
    integrationRequestMessage,
    integrationSucessMessage,
  } = useSelector((state: ROOTSTATE) => state.admin);
  const [cookies, setCookie, removeCookie] = useCookies(["adminInfo"]);

  const onChangeMode = useCallback(
    (value: any) => {
      setSwitchState(value);
    },
    [switchState]
  );

  const onClickParse = useCallback(() => {
    setParseModeState(false);
  }, []);
  const onFinishForm = (value: any) => {
    const { name, studentCode } = value;
    if (defaultAdminInfo) {
      dispatch(
        createAdminToStudentInfoAction.ACTION.REQUEST({
          adminId: defaultAdminInfo?.id,
          name,
          studentCode,
        })
      );
    }
  };
  useEffect(() => {
    dispatch(resetIntegrataionMessage());
    if (!defaultAdminInfo) {
      if (cookies.adminInfo) {
        dispatch(getCookieInfo(cookies.adminInfo));
      }
    }
  }, [defaultAdminInfo]);

  useEffect(() => {
    if (integrationErrorMessage) {
      message.error(integrationErrorMessage);
    } else if (integrationSucessMessage) {
      message.success(integrationSucessMessage);
    }
    dispatch(resetIntegrataionMessage());
  }, [integrationSucessMessage, integrationRequestMessage]);

  if (!cookies.adminInfo && !defaultAdminInfo) {
    dispatch(resetIntegrataionMessage());
    return <Redirect to="/" />;
  }

  return (
    <>
      <Navigation />
      <Switch
        onChange={onChangeMode}
        checkedChildren="동시에 학생 등록"
        unCheckedChildren="학생 한명 등록"
        defaultChecked
      />
      {switchState ? (
        <Form form={form} onFinish={onFinishForm}>
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
            label="이름 입력"
            name="name"
            rules={[
              { required: true, message: "이름을 입력해주세요" },
              ({ getFieldValue }) => ({
                validator(_, value) {
                  if (
                    !value ||
                    !String(value).match(/[(a-z)||(A-Z)||(0-9)]/g)?.length
                  ) {
                    return Promise.resolve();
                  }
                  return Promise.reject(
                    new Error("영문 숫자가 포함되어 있습니다.")
                  );
                },
              }),
            ]}
          >
            <Input type="text" />
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit">
              입력
            </Button>
          </Form.Item>
        </Form>
      ) : (
        <ExcelParingComponent />
      )}
    </>
  );
};

export default StudentCreateAdmin;
