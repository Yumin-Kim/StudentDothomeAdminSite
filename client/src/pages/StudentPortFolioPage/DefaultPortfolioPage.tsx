import { message } from "antd";
import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { ROOTSTATE } from "../../redux_folder/reducers/root";
import { resetStudentPortfolioMessageAcion } from "../../redux_folder/actions/studentPortfolio/index";

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
      <h1>수정 할 수 있는 기능</h1>
    </>
  );
};

export default DefaultPortfolioPage;
