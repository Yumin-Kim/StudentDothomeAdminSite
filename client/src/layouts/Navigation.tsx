// 관리자 페이지 NavigationBar
import React from "react";
import { Menu } from "antd";
import {
  MailOutlined,
  AppstoreOutlined,
  SettingOutlined,
} from "@ant-design/icons";
import { Link } from "react-router-dom";

const { SubMenu } = Menu;

class Navigation extends React.Component {
  state = {
    current: "mail",
  };

  handleClick = (e: any) => {
    console.log("click ", e);
    this.setState({ current: e.key });
  };

  render() {
    const { current } = this.state;
    return (
      <Menu onClick={this.handleClick} mode="horizontal">
        <Menu.Item key="mail" icon={<MailOutlined />}>
          <Link to="/admin/main">학생 정보 확인</Link>
        </Menu.Item>
        <Menu.Item key="create" icon={<AppstoreOutlined />}>
          <Link to="/admin/create">학생 정보 입력</Link>
        </Menu.Item>
        <Menu.Item key="modify" icon={<AppstoreOutlined />}>
          <Link to="/admin/modify">관리자 정보 수정</Link>
        </Menu.Item>
        <Menu.Item key="modify" icon={<AppstoreOutlined />}>
          <Link to="/admin/delete">도메인 , 데이터 베이스 확인 및 삭제 </Link>
        </Menu.Item>
      </Menu>
    );
  }
}

export default Navigation;
