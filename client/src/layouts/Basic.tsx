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
import SignupStudentPage from "../pages/StudentPortFolioPage/SignupStudentPage";
import DefaultPortfolioPage from "../pages/StudentPortFolioPage/DefaultPortfolioPage";
import CreatePortFolioPage from "../pages/StudentPortFolioPage/CreatePortFolioPage";
import ModifiedPortFolioPage from "../pages/StudentPortFolioPage/ModifiedPortFolioPage";
import CreateStudentPage from "../pages/StudentPage/CreateStudentPage";
import EditStudentPage from "../pages/StudentPage/EditStudentPage";
import DefaultStudentPage from "../pages/StudentPage/DefaultStudentPage";
import MainStudentPage from "../pages/StudentPage/MainStudentPage";
import MainInputPage from "../pages/MainInputPage";
import DeleteUtilInfoPage from "../pages/AdminPage/DeleteUtilInfoPage";
import ModifyAdminPage from "../pages/AdminPage/ModifyAdminPage";
import StudentCreateAdmin from "../pages/AdminPage/StudentCreateAdmin";
import MainAdminPage from "../pages/AdminPage/MainAdminPage";
const { Header, Content, Footer } = Layout;

const Basic = () => {
  const { prevHistory } = useSelector((state: ROOTSTATE) => state.student);
  return (
    <>
      <Header_Template id="Nav0_0" key="Nav0_0" dataSource={Nav00DataSource} />,
      <ContentCustom>
        <div className="home-page-wrapper content0-wrapper">
          <Switch>
            <Route path="/" component={Home} exact={true} />
            {routes.map((route, i) => {
              console.log(route);
              return <Route path={route.path} component={route.component} />;
            })}
            <Redirect path="*" to="/" />
          </Switch>
        </div>
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
