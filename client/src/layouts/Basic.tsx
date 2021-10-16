import React from "react";
import { Redirect, Link, BrowserRouter, Switch, Route } from "react-router-dom";
import { Layout } from "antd";
import { ContentCustom, HeaderCustom, ContentTemplateLayout } from "./style";
import Home from "../pages/Home";
import { useSelector } from "react-redux";
import { ROOTSTATE } from "../redux_folder/reducers/root";
import Header_Template from "./TemplateLayout/Header_Template";
import { Footer10DataSource, Nav00DataSource } from "./dataSoruce/data.source";
import Footer_Template from "./TemplateLayout/Footer_Template";
import { routes } from "./route";
const { Header, Content, Footer } = Layout;

const Basic = () => {
  const { prevHistory } = useSelector((state: ROOTSTATE) => state.student);
  return (
    <>
      <Header_Template id="Nav0_0" key="Nav0_0" dataSource={Nav00DataSource} />,
      <ContentCustom>
        <Switch>
          <Route path="/" component={Home} exact={true} />
          <div className="home-page-wrapper content0-wrapper">
            {routes.map((route, i) => {
              console.log(route);
              return <Route path={route.path} component={route.component} />;
            })}
          </div>
          <Redirect path="*" to="/" />
        </Switch>
      </ContentCustom>
      <Footer_Template
        id="Footer1_0"
        key="Footer1_0"
        dataSource={Footer10DataSource}
      />
      ,
    </>
  );
};

export default Basic;
