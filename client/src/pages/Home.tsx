import React, { useCallback, useEffect } from "react";
import { Button, Row, Col } from "antd";
import { Link } from "react-router-dom";
import { useDispatch } from "react-redux";
import { getCookieInfo } from "../redux_folder/actions/admin/index";
import { getStudentCookieInfo } from "../redux_folder/actions/student/index";

const Home = () => {
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(getStudentCookieInfo(null));
    dispatch(getCookieInfo(null));
  }, []);

  return (
    <>
      <div
        style={{
          margin: "0 auto",
          textAlign: "center",
        }}
      >
        {/* <Button block>
          <Link to="/make">계정 생성</Link>
        </Button> */}

        <Row gutter={[8, 16]}>
          <Col span={8} />
          <Col span={8}>
            <Button block>
              <Link to="/find">학교 서버 계정 확인</Link>
            </Button>
            <Button block>
              <Link to="/admin">서버 관리자 페이지</Link>
            </Button>
            <Button type="primary">
              <Link to="/portfolio/valid">서버 관리자 페이지</Link>
            </Button>
          </Col>
          <Col span={8} />
        </Row>
      </div>
    </>
  );
};

export default Home;
