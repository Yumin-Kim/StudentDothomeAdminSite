import { Col, Descriptions, Row, Form, Badge, Button, Checkbox } from "antd";
import React, { useCallback, useEffect, useState } from "react";
import Navigation from "../../layouts/Navigation";
import UtilFormBoxModal from "../../components/UtilFormBoxModal";
import { useDispatch, useSelector } from "react-redux";
import { ROOTSTATE } from "../../redux_folder/reducers/root";
import { resetToUtilMessageAction } from "../../redux_folder/actions/admin/utils/index";
import {
  deleteOriginTableConnectionAction,
  deleteOriginDomainAction,
} from "../../redux_folder/actions/admin/utils/index";
import {
  getOriginTableConnectionAction,
  getOriginDomainAction,
} from "../../redux_folder/actions/admin/utils/index";
const CheckboxGroup = Checkbox.Group;

const plainOptions = ["Apple", "Pear", "Orange"];

const DeleteUtilInfoPage = () => {
  const [databaseCheckedList, setDatabaseCheckedList] = useState<
    string[] | [] | null
  >(null);
  const [domainCheckedList, setDomainCheckedList] = useState<
    string[] | [] | null
  >(null);
  const [refreshData, setRefreshData] = useState(false);
  const [opencreateDBModal, setOpencreateDBModal] = useState(false);
  const [opencreateSSHModal, setOpencreateSSHModal] = useState(false);
  const [databaseLists, setDatabaseLists] = useState<string[] | null>(null);
  const [domainLists, setDomainLists] = useState<string[] | null>(null);
  const dispatch = useDispatch();
  const {
    originConnectionDatabases,
    originConnectionDomains,
    originDomainConnectionSucessMessage,
    originDBConnectionSucessMessage,
  } = useSelector((state: ROOTSTATE) => state.util);
  //database 삭제 항목 선택
  const onChangeDeleteDBCheckedList = useCallback(
    list => {
      setDatabaseCheckedList(list);
    },
    [databaseCheckedList]
  );
  //domain 삭제 항목 선택
  const onChangeDeleteDomainCheckedList = useCallback(
    list => {
      setDomainCheckedList(list);
      // setIndeterminate(!!list.length && list.length < plainOptions.length);
    },
    [domainCheckedList]
  );
  // db 정보 삭제 onChange
  const onClickDB = useCallback(() => {
    dispatch(resetToUtilMessageAction());
    if (databaseCheckedList) {
      dispatch(
        deleteOriginTableConnectionAction.ACTION.REQUEST(
          databaseCheckedList.join()
        )
      );
    }
    setDatabaseCheckedList([]);
    setRefreshData(true);
  }, [databaseCheckedList]);
  // domain 정보 삭제 onChange
  const onClickDomain = useCallback(() => {
    dispatch(resetToUtilMessageAction());
    if (domainCheckedList) {
      dispatch(
        deleteOriginDomainAction.ACTION.REQUEST(domainCheckedList.join())
      );
    }
    setDomainCheckedList([]);
    setRefreshData(true);
  }, [domainCheckedList]);

  //DB 생성 모달
  const onClickCreateToDatabase = useCallback(() => {
    setOpencreateDBModal(true);
  }, []);

  //DB 생성 모달
  const onClickCreateToSSH = useCallback(() => {
    setOpencreateSSHModal(true);
  }, []);

  //생성후 랜더링
  useEffect(() => {
    console.log("refreshData", refreshData);
    console.log(
      "originDomainConnectionSucessMessage",
      originDomainConnectionSucessMessage
    );
    console.log(
      "originDBConnectionSucessMessage",
      originDBConnectionSucessMessage
    );

    if (refreshData && originDBConnectionSucessMessage) {
      dispatch(getOriginTableConnectionAction.ACTION.REQUEST());
      setRefreshData(false);
    }
    if (refreshData && originDomainConnectionSucessMessage) {
      dispatch(getOriginDomainAction.ACTION.REQUEST());
      setRefreshData(false);
      dispatch(resetToUtilMessageAction());
    }
  }, [
    refreshData,
    originDomainConnectionSucessMessage,
    originDBConnectionSucessMessage,
  ]);
  //초기 화면 데이터 제공
  useEffect(() => {
    dispatch(getOriginTableConnectionAction.ACTION.REQUEST());
    dispatch(getOriginDomainAction.ACTION.REQUEST());
  }, []);
  //redux 데이터 state 삽입
  useEffect(() => {
    if (originConnectionDomains) {
    }
    if (originConnectionDatabases) {
      const parseData = originConnectionDatabases.reduce((prev, cur) => {
        prev.push(cur.user);
        return prev;
      }, [] as string[]);
      setDatabaseLists(parseData);
    }
  }, [originConnectionDomains, originConnectionDatabases]);

  return (
    <>
      <Navigation />
      <Row>
        <Col span={12}>
          {opencreateDBModal && (
            <UtilFormBoxModal
              setRefreshDataProp={setRefreshData}
              setIsModalVisible={setOpencreateDBModal}
              isModalVisible={opencreateDBModal}
              validationData="database"
              utilTitle="데이터 베이스 생성"
            />
          )}
          <Button onClick={onClickCreateToDatabase}>DB 생성</Button>
          <Descriptions title="데이터 베이스 정보" bordered>
            <Descriptions.Item label="데이터 베이스 정보">
              {databaseLists && (
                <CheckboxGroup
                  options={databaseLists}
                  value={databaseCheckedList}
                  onChange={onChangeDeleteDBCheckedList}
                  style={{ display: "block" }}
                />
              )}

              <Button onClick={onClickDB}>삭제</Button>
            </Descriptions.Item>
          </Descriptions>
        </Col>
        <Col span={12}>
          {opencreateSSHModal && (
            <UtilFormBoxModal
              setRefreshDataProp={setRefreshData}
              setIsModalVisible={setOpencreateSSHModal}
              isModalVisible={opencreateSSHModal}
              validationData="ssh"
              utilTitle="SSH 유저 , 도메인 생성"
            />
          )}
          <Button onClick={onClickCreateToSSH}>사용자 생성</Button>
          <Descriptions title="존재 하는 SSH , 도메인 정보" bordered>
            <Descriptions.Item label="Status" span={3}>
              <Badge status="processing" text="Running" />
            </Descriptions.Item>

            <Descriptions.Item label="Config Info">
              {originConnectionDomains !== undefined &&
                originConnectionDomains && (
                  <CheckboxGroup
                    options={originConnectionDomains}
                    value={domainCheckedList}
                    onChange={onChangeDeleteDomainCheckedList}
                    style={{ display: "block" }}
                  />
                )}
              <Button onClick={onClickDomain}>삭제</Button>
            </Descriptions.Item>
          </Descriptions>
        </Col>
      </Row>
    </>
  );
};

export default DeleteUtilInfoPage;
