import React, { FC, useCallback, useState } from "react";
import { Modal, Form, Input, Button } from "antd";

type T_ValidationName = "database" | "ssh";

interface I_UtilFormBoxModalProps {
  isModalVisible: boolean;
  setIsModalVisible: (state: boolean) => void;
  validationData: T_ValidationName;
  utilTitle: string;
}

const UtilFormBoxModal: FC<I_UtilFormBoxModalProps> = ({
  isModalVisible,
  setIsModalVisible,
  utilTitle,
  validationData,
}) => {
  console.log(isModalVisible);

  const [form] = Form.useForm();

  const handleOk = () => {
    setIsModalVisible(false);
  };

  const handleCancel = () => {
    setIsModalVisible(false);
  };
  //validationData를 통해 ssh , 데이터 베이스 생성 dispatch 진행
  const onFinshForm = useCallback(value => {
    if (validationData === "ssh") {
    }
  }, []);

  return (
    <Modal
      title={utilTitle}
      visible={isModalVisible}
      onOk={handleOk}
      onCancel={handleCancel}
    >
      <Form form={form} onFinish={onFinshForm}>
        <Form.Item
          label={validationData === "database" ? "데이터 베이스 명" : "ssh 명"}
          name={validationData}
        >
          <Input type="text" />
        </Form.Item>
        <Form.Item>
          <Button type="primary" htmlType="submit">
            Submit
          </Button>
        </Form.Item>{" "}
      </Form>
    </Modal>
  );
};

export default UtilFormBoxModal;
