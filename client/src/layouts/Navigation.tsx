// 관리자 페이지 NavigationBar
import React, { useEffect, useState } from "react";
import { Menu } from "antd";
import {
  MailOutlined,
  AppstoreOutlined,
  SettingOutlined,
} from "@ant-design/icons";
import { Link } from "react-router-dom";
import { useHistory } from "react-router";

const { SubMenu } = Menu;

const Navigation = () => {
  const [current, setCurrent] = useState<string>();
  const history = useHistory();
  //클릭시 클릭한 메뉴 하아라이트
  useEffect(() => {
    if (history.location.pathname.split("/")) {
      setCurrent(history.location.pathname.split("/")[2]);
    }
  }, [current]);

  return (
    <Menu selectedKeys={current} mode="horizontal">
      <Menu.Item key="main" icon={<MailOutlined />}>
        <Link to="/admin/main">학생 정보 확인</Link>
      </Menu.Item>
      <Menu.Item key="create" icon={<AppstoreOutlined />}>
        <Link to="/admin/create">학생 정보 입력</Link>
      </Menu.Item>
      <Menu.Item key="modify" icon={<AppstoreOutlined />}>
        <Link to="/admin/modify">관리자 정보 수정</Link>
      </Menu.Item>
      <Menu.Item key="delete" icon={<AppstoreOutlined />}>
        <Link to="/admin/delete">데이터 베이스 & SSH[도메인] 생성 및 삭제</Link>
      </Menu.Item>
    </Menu>
  );
};

export default Navigation;
