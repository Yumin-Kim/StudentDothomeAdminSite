import React, { useCallback, useEffect, useState } from "react";
import { Descriptions, Badge, Button, message } from "antd";
import { useDispatch, useSelector } from "react-redux";
import { ROOTSTATE } from "../../redux_folder/reducers/root";
import { Redirect } from "react-router-dom";
import { changeDefaultToCreatePage } from "../../redux_folder/actions/student";
import { useCookies } from "react-cookie";
import { getStudentCookieInfo } from "../../redux_folder/actions/student/index";

const DefaultStudentPage = () => {
  const { studentInfo, integrationSucessMessage } = useSelector(
    (state: ROOTSTATE) => state.student
  );
  const [changePage, setChangePage] = useState(false);
  const [cookies, setCookie, removeCookie] = useCookies(["studentInfo"]);
  const dispatch = useDispatch();

  const onClickCreateStudent = useCallback(() => {
    setChangePage(true);
  }, [changePage]);
  const now = new Date();
  useEffect(() => {
    if (integrationSucessMessage) {
      message.success(integrationSucessMessage);
    }
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
  }, []);

  if (changePage) {
    return <Redirect to="/student/create" />;
  }

  if (!studentInfo && !cookies.studentInfo) {
    return <Redirect to="/" />;
  }

  return (
    <Descriptions
      bordered
      title="기본 계정 정보"
      extra={
        <Button type="primary" onClick={onClickCreateStudent}>
          생성하러가기
        </Button>
      }
    >
      <Descriptions.Item label="이름">{studentInfo?.name}</Descriptions.Item>
      {studentInfo?.inSchool ? (
        <Descriptions.Item label="재학 여부">YES</Descriptions.Item>
      ) : null}
      <Descriptions.Item label="학번">
        {studentInfo?.studentCode}
      </Descriptions.Item>
      <Descriptions.Item label="재학 여부">
        {studentInfo?.inSchool ? "YES" : "NO"}
      </Descriptions.Item>
      <Descriptions.Item label="활설화 여부">
        <Badge status="processing" text="Running" />
      </Descriptions.Item>
    </Descriptions>
  );
};

export default DefaultStudentPage;
