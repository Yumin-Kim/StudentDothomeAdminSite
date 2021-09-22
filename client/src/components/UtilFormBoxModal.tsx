import React, { FC, useCallback, useEffect, useState } from "react";
import { Modal, Form, Input, Button } from "antd";
import { useDispatch } from "react-redux";
import { resetToUtilMessageAction } from "../redux_folder/actions/admin/utils/index";
import {
  createOriginTableConnectionAction,
  createOriginDomainAction,
} from "../redux_folder/actions/admin/utils/index";

type T_ValidationName = "database" | "ssh";

interface I_UtilFormBoxModalProps {
  isModalVisible: boolean;
  setIsModalVisible: (state: boolean) => void;
  validationData: T_ValidationName;
  utilTitle: string;
  setRefreshDataProp: (state: boolean) => void;
}

const UtilFormBoxModal: FC<I_UtilFormBoxModalProps> = ({
  isModalVisible,
  setIsModalVisible,
  utilTitle,
  validationData,
  setRefreshDataProp,
}) => {
  const [form] = Form.useForm();
  const [confirmLoading, setConfirmLoading] = useState(false);
  const dispatch = useDispatch();

  //validationData를 통해 ssh , 데이터 베이스 생성 dispatch 진행
  const handleOk = () => {
    setConfirmLoading(true);
    if (validationData === "database") {
      dispatch(
        createOriginTableConnectionAction.ACTION.REQUEST({
          database: form.getFieldValue("database"),
          password: form.getFieldValue("password"),
        })
      );
    } else {
      dispatch(
        createOriginDomainAction.ACTION.REQUEST({
          domain: form.getFieldValue("ssh"),
          password: form.getFieldValue("password"),
        })
      );
    }
    setTimeout(() => {
      setIsModalVisible(false);
      setConfirmLoading(false);
      setRefreshDataProp(true);
    }, 3000);
  };

  const handleCancel = () => {
    setIsModalVisible(false);
  };

  useEffect(() => {
    form.setFieldsValue({
      password: "",
      database: "",
      ssh: "",
    });
    dispatch(resetToUtilMessageAction());
  }, []);

  return (
    <Modal
      title={utilTitle}
      visible={isModalVisible}
      onOk={handleOk}
      okText="생성"
      cancelText="취소"
      onCancel={handleCancel}
      confirmLoading={confirmLoading}
    >
      <Form form={form}>
        <Form.Item
          label={validationData === "database" ? "데이터 베이스 명" : "ssh 명"}
          name={validationData}
          rules={[
            {
              required: true,
              message: "이름을 입력해주세요",
            },
            ({ getFieldValue }) => ({
              validator(_, value) {
                if (validationData === "database") {
                  if (String(value).indexOf("s") === 0) {
                    const regData = String(value)
                      .slice(1, -1)
                      .match(/[0-9]*/g);
                    if (regData?.length === 2 && regData[0].length === 8) {
                      return Promise.resolve();
                    }
                  }
                  return Promise.reject(
                    new Error(
                      "데이터 베이스는 첫번째 s 와 학번만 입력가능합니다 사용해야합니다"
                    )
                  );
                } else {
                  if (!value || value.match(/[a-z]*/g).length === 2) {
                    return Promise.resolve();
                  }
                  return Promise.reject(
                    new Error("domain 영소문자만 가능합니다")
                  );
                }
              },
            }),
          ]}
        >
          <Input type="text" />
        </Form.Item>
        <Form.Item label="비밀번호" name="password">
          <Input.Password />
        </Form.Item>
      </Form>
    </Modal>
  );
};

export default UtilFormBoxModal;
