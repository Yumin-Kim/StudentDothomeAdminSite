import React, { useCallback } from "react";
import { Button } from "antd";
import { Link } from "react-router-dom";

const Home = () => {
  const test = useCallback(() => {
    console.log("asdasd");
  }, []);

  return (
    <>
      <h1>Hello Home</h1>
      <div
        style={{
          margin: "0 auto",
          textAlign: "center",
        }}
      >
        <Button onClick={test}> axios</Button>
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
