import React from "react";
import { Redirect, Link, BrowserRouter, Switch, Route } from "react-router-dom";
import { Layout } from "antd";
import { ContentCustom, HeaderCustom } from "./style";
import Home from "../pages/Home";
import MainInputPage from "../pages/MainInputPage";
import CreateStudentPage from "../pages/StudentPage/CreateStudentPage";
import MainStudentPage from "../pages/StudentPage/MainStudentPage";
import EditStudentPage from "../pages/StudentPage/EditStudentPage";
import { useSelector } from "react-redux";
import { ROOTSTATE } from "../redux_folder/reducers/root";
import DefaultStudentPage from "../pages/StudentPage/DefaultStudentPage";
import MainAdminPage from "../pages/AdminPage/MainAdminPage";
import ModifyAdminPage from "../pages/AdminPage/ModifyAdminPage";
import StudentCreateAdmin from "../pages/AdminPage/StudentCreateAdmin";
import DeleteUtilInfoPage from "../pages/AdminPage/DeleteUtilInfoPage";
const { Header, Content, Footer } = Layout;

const Basic = () => {
  const { prevHistory } = useSelector((state: ROOTSTATE) => state.student);
  return (
    <Layout>
      <HeaderCustom>
        <Link to="/">Home</Link>
        {"      "}
        {prevHistory && <Link to={`${prevHistory}`}>이전으로 돌아가기</Link>}
      </HeaderCustom>
      <ContentCustom>
        <Switch>
          <Route path="/" component={Home} exact={true} />
          <Route path="/student/create" component={CreateStudentPage} />
          <Route path="/student/main" component={MainStudentPage} />
          <Route path="/student/edit" component={EditStudentPage} />
          <Route path="/student/default" component={DefaultStudentPage} />
          <Route path="/admin/main" component={MainAdminPage} />
          <Route path="/admin/create" component={StudentCreateAdmin} />
          <Route path="/admin/modify" component={ModifyAdminPage} />
          <Route path="/admin/delete" component={DeleteUtilInfoPage} />
          <Route path="/:stubing" component={MainInputPage} />
          <Redirect path="*" to="/" />
        </Switch>
      </ContentCustom>
      <Footer>Footer</Footer>
    </Layout>
  );
};

export default Basic;
