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
import { getCookieInfo } from "../../redux_folder/actions/admin/index";

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
  const [switchState, setSwitchState] = useState(true);
  const [parseModeState, setParseModeState] = useState(false);
  const dispatch = useDispatch();
  const { defaultAdminInfo } = useSelector((state: ROOTSTATE) => state.admin);
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
    console.log(value);
  };
  useEffect(() => {
    dispatch(resetIntegrataionMessage());
    if (!defaultAdminInfo) {
      if (cookies.adminInfo) {
        dispatch(getCookieInfo(cookies.adminInfo));
      }
    }
  }, [defaultAdminInfo]);

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
        <Form>
          <Form.Item label="학번 입력">
            <Input name="studentCode" type="number" />
          </Form.Item>
          <Form.Item label="이름 입력">
            <Input name="name" type="text" />
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
