import React, { useCallback, useEffect } from "react";
import { Button, Row, Col } from "antd";
import { Link } from "react-router-dom";
import { useDispatch } from "react-redux";
import { getCookieInfo } from "../redux_folder/actions/admin/index";
import { getStudentCookieInfo } from "../redux_folder/actions/student/index";
import Banner_Template from "../layouts/TemplateLayout/Banner_Template";
import {
  Banner01DataSource,
  Content00DataSource,
  Content30DataSource,
  Content50DataSource,
} from "../layouts/dataSoruce/data.source";
import Content_Template01 from "../layouts/TemplateLayout/Content_Template01";
import Content_Template02 from "../layouts/TemplateLayout/Content_Template02";
import Content_Template03 from "../layouts/TemplateLayout/Content_Template03";
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
        <Banner_Template
          id="Banner0_1"
          key="Banner0_1"
          dataSource={Banner01DataSource}
        />
        <Content_Template01
          id="Content0_0"
          key="Content0_0"
          dataSource={Content00DataSource}
        />
        <Content_Template02
          id="Content3_0"
          key="Content3_0"
          dataSource={Content30DataSource}
        />
        <Content_Template03
          id="Content5_0"
          key="Content5_0"
          dataSource={Content50DataSource}
        />
        ,
      </div>
    </>
  );
};

export default Home;
