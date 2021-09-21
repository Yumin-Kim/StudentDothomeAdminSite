import { Col, Descriptions, Row, Form, Badge, Button, Checkbox } from "antd";
import React, { useCallback, useEffect, useState } from "react";
import Navigation from "../../layouts/Navigation";
import UtilFormBoxModal from "../../components/UtilFormBoxModal";
const CheckboxGroup = Checkbox.Group;

const plainOptions = ["Apple", "Pear", "Orange"];

const DeleteUtilInfoPage = () => {
  const [checkedList, setCheckedList] = useState(null);
  const [indeterminate, setIndeterminate] = useState(true);
  const [opencreateDBModal, setOpencreateDBModal] = useState(false);
  const [opencreateSSHModal, setOpencreateSSHModal] = useState(false);

  const onChangeDeleteDBCheckedList = useCallback(
    list => {
      setCheckedList(list);
      setIndeterminate(!!list.length && list.length < plainOptions.length);
    },
    [checkedList]
  );
  // db 정보 삭제
  const onClickDB = useCallback(() => {
    console.log(checkedList);
  }, [checkedList]);

  //DB 생성 모달
  const onClickCreateToDatabase = useCallback(() => {
    setOpencreateDBModal(true);
  }, []);

  //DB 생성 모달
  const onClickCreateToSSH = useCallback(() => {
    setOpencreateSSHModal(true);
  }, []);

  //생성후 랜더링
  useEffect(() => {}, []);

  return (
    <>
      <Navigation />
      <Row>
        <Col span={12}>
          {opencreateDBModal && (
            <UtilFormBoxModal
              setIsModalVisible={setOpencreateDBModal}
              isModalVisible={opencreateDBModal}
              validationData="database"
              utilTitle="데이터 베이스 생성"
            />
          )}
          <Button onClick={onClickCreateToDatabase}>DB 생성</Button>
          <Descriptions title="존재 하는 데이터 베이스 정보" bordered>
            <Descriptions.Item label="Config Info">
              <CheckboxGroup
                options={plainOptions}
                value={checkedList}
                onChange={onChangeDeleteDBCheckedList}
                style={{ display: "block" }}
              />
              <Button onClick={onClickDB}>삭제</Button>
            </Descriptions.Item>
          </Descriptions>
        </Col>
        <Col span={12}>
          {opencreateSSHModal && (
            <UtilFormBoxModal
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
              Data disk type: MongoDB
              <br />
              Database version: 3.4
              <br />
              Package: dds.mongo.mid
              <br />
              Storage space: 10 GB
              <br />
              Replication factor: 3
              <br />
              Region: East China 1<br />
            </Descriptions.Item>
          </Descriptions>
        </Col>
      </Row>
    </>
  );
};

export default DeleteUtilInfoPage;
