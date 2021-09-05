import React from "react";
import { Redirect, Link, BrowserRouter, Switch, Route } from "react-router-dom";
import { Layout } from "antd";
import { ContentCustom, HeaderCustom } from "./style";
import Home from "../pages/Home";
import MainInputPage from "../pages/MainInputPage";
const { Header, Content, Footer } = Layout;

const Basic = () => {
  return (
    <Layout>
      <HeaderCustom>
        <Link to="/">Home</Link>
      </HeaderCustom>
      <ContentCustom>
        <Switch>
          <Route path="/" component={Home} exact={true} />
          <Route path="/:stubing" component={MainInputPage} />
          <Redirect path="*" to="/" />
        </Switch>
      </ContentCustom>
      <Footer>Footer</Footer>
    </Layout>
  );
};

export default Basic;
