import { message, Typography, Image, Button } from "antd";
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
      <Title level={2}>현재 등록 정보</Title>
      <Button type="primary">
        <Link to="/portfolio/modify">수정</Link>
      </Button>
      {resultStudentPortFolio.name && (
        <div>
          <Title level={4}>이름 / 학번</Title>
          <Text>
            {resultStudentPortFolio.name} / {resultStudentPortFolio.studentCode}
          </Text>
        </div>
      )}
      {resultStudentPortFolio.description && (
        <div>
          <Title level={4}>소개글 이미지</Title>
          <pre>{resultStudentPortFolio.description}</pre>
        </div>
      )}
      {resultStudentPortFolio.youtubeLink && (
        <div>
          <Title level={4}>유튜브 링크 이미지</Title>
          <div>
            <Text>링크 주소 : {resultStudentPortFolio.youtubeLink}</Text>
          </div>
          <div>
            <iframe
              width="700"
              height="300"
              src={resultStudentPortFolio.youtubeLink}
            ></iframe>
          </div>
        </div>
      )}
      {resultStudentPortFolio?.profileImageSrc && (
        <div>
          <Title level={4}>프로필 이미지</Title>
          <Image
            width={700}
            src={`http://media.seowon.ac.kr/2021${resultStudentPortFolio?.profileImageSrc}`}
          />
        </div>
      )}
      {resultStudentPortFolio?.brochureImageSrc && (
        <div>
          <Title level={4}>브로셔 이미지</Title>
          <Image
            width={700}
            src={`http://media.seowon.ac.kr/2021${resultStudentPortFolio?.brochureImageSrc}`}
          />
        </div>
      )}
    </>
  );
};

export default DefaultPortfolioPage;
