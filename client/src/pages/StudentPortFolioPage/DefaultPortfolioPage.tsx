import { message, Typography, Image, Button, Descriptions } from "antd";
import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { ROOTSTATE } from "../../redux_folder/reducers/root";
import { resetStudentPortfolioMessageAcion } from "../../redux_folder/actions/studentPortfolio/index";
import { Redirect } from "react-router";
import TextArea from "antd/lib/input/TextArea";
import Text from "antd/lib/typography/Text";
import { Link } from "react-router-dom";

const { Title } = Typography;

const DefaultPortfolioPage = () => {
  const dispatch = useDispatch();
  const {
    integrationErrorMessage,
    integrationRequestMessage,
    integrationSucessMessage,
    resultStudentPortFolio,
  } = useSelector((state: ROOTSTATE) => state.studentPortfolio);

  useEffect(() => {
    if (integrationSucessMessage) {
      message.success(integrationSucessMessage);
    }
    dispatch(resetStudentPortfolioMessageAcion(null));
  }, []);
  if (!resultStudentPortFolio) {
    return <Redirect to="/portfolio/valid" />;
  }
  return (
    <>
      <Title level={2}>현재 등록 정보 </Title>
      <Button type="primary">
        <Link to="/portfolio/modify">수정 하기</Link>
      </Button>
      {resultStudentPortFolio.email && (
        <div>
          <Descriptions bordered>
            <Descriptions.Item label="이름 / 학번">
              {resultStudentPortFolio.name} /{" "}
              {resultStudentPortFolio.studentCode}
            </Descriptions.Item>
            <Descriptions.Item label="전화 번호">
              {resultStudentPortFolio.phoneNumber}
            </Descriptions.Item>

            <Descriptions.Item label="직업">
              {resultStudentPortFolio.job}
            </Descriptions.Item>
            <Descriptions.Item label="이메일">
              {resultStudentPortFolio.email}
            </Descriptions.Item>
            <Descriptions.Item label="슬로건">
              {resultStudentPortFolio.slogan}
            </Descriptions.Item>
            <Descriptions.Item label="소개글 이미지">
              <pre>{resultStudentPortFolio.description}</pre>
            </Descriptions.Item>
            <Descriptions.Item label="유튜브 링크">
              <iframe
                width="300"
                height="300"
                src={resultStudentPortFolio.youtubeLink}
              ></iframe>
            </Descriptions.Item>
            <Descriptions.Item label="프로필 이미지">
              <Image
                width={200}
                src={`http://media.seowon.ac.kr/2021${resultStudentPortFolio?.profileImageSrc}`}
              />
            </Descriptions.Item>
            <Descriptions.Item label="브로셔 이미지">
              <Image
                width={200}
                src={`http://media.seowon.ac.kr/2021${resultStudentPortFolio?.brochureImageSrc}`}
              />
            </Descriptions.Item>
          </Descriptions>
        </div>
      )}
    </>
  );
};

export default DefaultPortfolioPage;
