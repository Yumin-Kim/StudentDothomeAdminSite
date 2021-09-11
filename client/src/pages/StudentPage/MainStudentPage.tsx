import { message, Button } from "antd";
import { Descriptions, Badge } from "antd";
import React, { useCallback, useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Redirect, useLocation } from "react-router";
import { ROOTSTATE } from "../../redux_folder/reducers/root";
import {
  prevHistoryMappging,
  changeIntegrationSuccessMessage,
} from "../../redux_folder/actions/student/index";

const baseReomteURL =
  process.env.NODE_ENV !== "production"
    ? "http://localhost"
    : "http://media.seowon.ac.kr";
const MainStudentPage = () => {
  const {
    integrationSucessMessage,
    prevHistory,
    integrationRequestMessage,
    studentInfo,
  } = useSelector((state: ROOTSTATE) => state.student);
  const [editPageState, setEditPageState] = useState(false);
  const dispatch = useDispatch();
  const location = useLocation();

  useEffect(() => {
    if (prevHistory) {
      dispatch(prevHistoryMappging(""));
    }

    if (integrationSucessMessage && !integrationRequestMessage) {
      message.success(integrationSucessMessage);
      dispatch(changeIntegrationSuccessMessage());
    }
  }, [integrationSucessMessage]);

  const onClickEdit = useCallback(() => {
    setEditPageState(true);
    dispatch(prevHistoryMappging(location.pathname));
  }, []);
  if (editPageState) {
    return <Redirect to="/student/edit" />;
  }
  return (
    <Descriptions
      bordered
      title="생성된 계정 정보"
      extra={
        <Button type="primary" onClick={onClickEdit}>
          Edit
        </Button>
      }
    >
      <Descriptions.Item label="이름">{studentInfo?.name}</Descriptions.Item>
      <Descriptions.Item label="전화 번호">
        {studentInfo?.phoneNumber}
      </Descriptions.Item>
      {studentInfo?.inSchool ? (
        <Descriptions.Item label="재학 여부">YES</Descriptions.Item>
      ) : null}
      <Descriptions.Item label="이메일">{studentInfo?.email}</Descriptions.Item>
      <Descriptions.Item label="학번">
        {studentInfo?.studentCode}
      </Descriptions.Item>
      <Descriptions.Item label="비밀번호">
        {studentInfo?.password}
      </Descriptions.Item>
      <Descriptions.Item label="활설화 여부">
        <Badge status="processing" text="Running" />
      </Descriptions.Item>
      <Descriptions.Item label="도메인 및 데이터 베이스 정보">
        도메인 :{" "}
        <a
          href={`${baseReomteURL}/${studentInfo?.siteInfo.domainName}`}
        >{`${baseReomteURL}/${studentInfo?.siteInfo.domainName}`}</a>
        <br />
        데이터 베이스 : {studentInfo?.siteInfo.databaseName}{" "}
        <a href="http://media.seowon.ac.kr/phpmyadmin">
          phpMyAdmin으로 확인하기
        </a>
        <br />
      </Descriptions.Item>
    </Descriptions>
  );
};

export default MainStudentPage;
