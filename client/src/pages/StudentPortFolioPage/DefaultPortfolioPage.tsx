import { message, Typography } from "antd";
import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { ROOTSTATE } from "../../redux_folder/reducers/root";
import { resetStudentPortfolioMessageAcion } from "../../redux_folder/actions/studentPortfolio/index";

const { Title } = Typography;

const DefaultPortfolioPage = () => {
  const dispatch = useDispatch();
  const {
    integrationErrorMessage,
    integrationRequestMessage,
    integrationSucessMessage,
  } = useSelector((state: ROOTSTATE) => state.studentPortfolio);

  useEffect(() => {
    if (integrationErrorMessage) {
      message.success(integrationErrorMessage);
    }
    dispatch(resetStudentPortfolioMessageAcion(null));
  }, []);
  return (
    <>
      <Title level={3}>TODO LIST</Title>
      <ul>
        <li>
          <Title level={2}>수정 할 수 있는 기능</Title>
          <p>RESTAPI 설계 필요</p>
        </li>
        <li>
          <Title level={3}>생성 완료 문구 보여주기</Title>
        </li>
        <li>
          <Title level={3}>본인 사이트로 연결되도록</Title>
        </li>
      </ul>
    </>
  );
};

export default DefaultPortfolioPage;
