import React, { useCallback, useState } from "react";
import { Descriptions, Badge, Button } from "antd";
import { useDispatch, useSelector } from "react-redux";
import { ROOTSTATE } from "../../redux_folder/reducers/root";
import { Redirect } from "react-router-dom";
import { changeDefaultToCreatePage } from "../../redux_folder/actions/student";

const DefaultStudentPage = () => {
  const { studentInfo } = useSelector((state: ROOTSTATE) => state.student);
  const [changePage, setChangePage] = useState(false);
  const dispatch = useDispatch();

  const onClickCreateStudent = useCallback(() => {
    setChangePage(true);
  }, []);

  if (changePage) {
    dispatch(changeDefaultToCreatePage());
    return <Redirect to="/student/create" />;
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
