import React, { useCallback, useEffect } from "react";
import { Button } from "antd";
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
      <h1>Hello</h1>
      <div
        style={{
          margin: "0 auto",
          textAlign: "center",
        }}
      >
        <Button block>
          <Link to="/make">계정 생성</Link>
        </Button>
        <Button block>
          <Link to="/find">계정 찾기</Link>
        </Button>
        <Button block>
          <Link to="/admin">관리자 페이지</Link>
        </Button>
      </div>
    </>
  );
};

export default Home;
