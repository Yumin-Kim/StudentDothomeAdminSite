import React, { FC, useCallback, useEffect, useState } from "react";
import { Table, Button, Modal, Form, Input, Select, Switch } from "antd";
import {
  I_AllStudentInfoPaging_Admin,
  I_AllStudentInfo_Adamin,
} from "../types/storeType";
import { useDispatch } from "react-redux";
import { concurrentModifiedAdminStudentInfoAction } from "../redux_folder/actions/admin/index";
import FormItemComponent from "./FormItemComponent";

const { Option } = Select;
interface CommonTableProps {
  value: null | I_AllStudentInfoPaging_Admin;
}
type columnName =
  | "name"
  | "studentCode"
  | "email"
  | "phoneNumber"
  | "domainName"
  | "databaseName"
  | "inSchool"
  | "isDeleted";
interface I_TableColumnEl {
  title: string;
  dataIndex: columnName;
}
interface I_TableDataFormat {
  id: number;
  name: string;
  studentCode: number;
  email: string;
  phoneNumber: string;
  domainName: string;
  databaseName: string;
  inSchool: string;
  isDeleted: string;
}
const tableIndexingData: I_TableColumnEl[] = [
  {
    title: "이름",
    dataIndex: "name",
  },
  {
    title: "학번",
    dataIndex: "studentCode",
  },
  {
    title: "이메일",
    dataIndex: "email",
  },
  {
    title: "전화번호",
    dataIndex: "phoneNumber",
  },
  {
    title: "도메인 명",
    dataIndex: "domainName",
  },
  {
    title: "데이터 베이스 명",
    dataIndex: "databaseName",
  },
  {
    title: "재학 여부",
    dataIndex: "inSchool",
  },
  {
    title: "활성화",
    dataIndex: "isDeleted",
  },
];

const CommonTable: FC<CommonTableProps> = ({ value }) => {
  const [selectedRowKeys, setSelectedRowKeys] = useState<any>([]);
  const [columnData, setColumnData] = useState<I_TableDataFormat[] | null>(
    null
  );
  const [openModal, setOpenModal] = useState(false);
  const onOpenModifiedModal = useCallback(() => {
    setOpenModal(true);
    setVisible(true);
  }, [openModal]);
  const [form] = Form.useForm();
  const [changeSelected, setChangeSelected] = useState(0);
  const [modifiedFormList, setModifiedFormList] = useState<
    Omit<I_AllStudentInfo_Adamin, "siteInfo" | "adminName">[] | []
  >([]);

  //Modal State
  const [visible, setVisible] = useState(false);
  const [confirmLoading, setConfirmLoading] = useState(false);
  const [modalText, setModalText] = useState("Content of the modal");
  ///

  const dispatch = useDispatch();

  // 수정 완료 이벤트 발생
  const handleOk = () => {
    setModalText("The modal will be closed after two seconds");
    setConfirmLoading(true);
    setTimeout(() => {
      setVisible(false);
      setConfirmLoading(false);
      console.log(modifiedFormList);
      dispatch(
        concurrentModifiedAdminStudentInfoAction.ACTION.REQUEST(
          modifiedFormList
        )
      );
      setModifiedFormList([]);
    }, 2000);
  };
  const onFinishForm = useCallback(
    (value: any) => {
      value.studentCode = Number(value.studentCode);
      if (typeof value.isDeleted === "string")
        value.isDeleted = value.isDeleted === "활성화" ? false : true;
      else {
        value.isDeleted = !value.isDeleted;
      }
      if (typeof value.inSchool === "string") {
        value.inSchool = value.inSchool === "재학중" ? true : false;
      }
      console.log(changeSelected);
      console.log(selectedRowKeys);

      value["id"] = selectedRowKeys[changeSelected].id;
      console.log("parseValue", value);
      setModifiedFormList(prev => [...prev, value]);
    },
    [changeSelected, selectedRowKeys]
  );

  const onChangeSelect = useCallback(
    (value: any) => {
      console.log(value);

      console.log(form.getFieldValue("name"));
      console.log(form.getFieldValue("domainName"));
      console.log(form.getFieldValue("databaseName"));
      console.log(form.getFieldValue("studentCode"));

      setChangeSelected(value);
      form.setFieldsValue({
        name: "",
        domainName: "",
        studentCode: "",
        databaseName: "",
      });
    },
    [changeSelected]
  );
  const handleCancel = () => {
    console.log("Clicked cancel button");
    setVisible(false);
  };
  const rowSelection = {
    onChange: (selectedRowKeys, selectedRows) => {
      console.log(
        `selectedRowKeys: ${selectedRowKeys}`,
        "selectedRows: ",
        selectedRows
      );
      setSelectedRowKeys(selectedRows);
    },
  };

  useEffect(() => {
    console.log("selectedRowKeys.length", selectedRowKeys.length);

    if (selectedRowKeys.length > 0 && openModal) {
      console.log("useEffect", selectedRowKeys.length);

      form.setFieldsValue({
        name: selectedRowKeys[changeSelected].name,
        domainName: selectedRowKeys[changeSelected].domainName,
        studentCode: selectedRowKeys[changeSelected].studentCode,
        databaseName: selectedRowKeys[changeSelected].databaseName,
        isDeleted: selectedRowKeys[changeSelected].isDeleted,
        inSchool: selectedRowKeys[changeSelected].inSchool,
      });
    }
  }, [selectedRowKeys, changeSelected, openModal]);

  useEffect(() => {
    if (value) {
      if (value.infos) {
        setColumnData(
          (value.infos as I_AllStudentInfo_Adamin[]).reduce(
            (prev, cur, index) => {
              const nullData = "존재 하지 않음";
              const merge = {} as I_TableDataFormat;
              cur.email ? (merge.email = cur.email) : (merge.email = nullData);
              cur.phoneNumber
                ? (merge.phoneNumber = cur.phoneNumber)
                : (merge.phoneNumber = nullData);
              cur.siteInfo?.databaseName
                ? (merge.databaseName = cur.siteInfo?.databaseName)
                : (merge.databaseName = nullData);
              cur.siteInfo?.domainName
                ? (merge.domainName = cur.siteInfo?.domainName)
                : (merge.domainName = nullData);
              merge.id = cur.id;
              merge.name = cur.name;
              merge.studentCode = cur.studentCode;
              merge.isDeleted = cur.isDeleted ? "비활성화" : "활성화";
              merge.inSchool = cur.inSchool ? "재학중" : "휴학 또는 졸업";
              merge.key = index;
              prev.push(merge);
              return prev;
            },
            [] as I_TableDataFormat[]
          )
        );
      }
    }
  }, [value?.infos]);

  return (
    <>
      {openModal && (
        <Modal
          title="학생 정보 수정"
          visible={visible}
          onOk={handleOk}
          confirmLoading={confirmLoading}
          onCancel={handleCancel}
        >
          <Form form={form} onFinish={onFinishForm}>
            <Select name="selected" defaultValue={0} onChange={onChangeSelect}>
              {selectedRowKeys !== undefined &&
                selectedRowKeys.length !== 0 &&
                selectedRowKeys.map((value, index) => (
                  <Option value={index}>{value.studentCode} </Option>
                ))}
            </Select>
            {selectedRowKeys !== undefined &&
              selectedRowKeys.length !== 0 &&
              Object.entries(selectedRowKeys[changeSelected]).map(
                (value, index) => {
                  const obj_keys = Object.keys(value);
                  const containArr = [
                    "name",
                    "studentCode",
                    "domainName",
                    "databaseName",
                    "inSchool",
                    "isDeleted",
                  ];
                  if (
                    value[1] !== "존재 하지 않음" &&
                    containArr.includes(value[0])
                  ) {
                    if (value[0] === "isDeleted") {
                      return (
                        <FormItemComponent
                          label={"isDeleted"}
                          name={"isDeleted"}
                          validName={"활성화"}
                          checkedChild={"활성화"}
                          unCheckChild={"비활성화"}
                          value={value}
                        />
                      );
                    }
                    if (value[0] === "inSchool") {
                      return (
                        <FormItemComponent
                          label={"inSchool"}
                          name={"inSchool"}
                          validName={"재학중"}
                          checkedChild={"재학중"}
                          unCheckChild={"휴학 또는 졸업"}
                          value={value}
                        />
                      );
                    } else {
                      return (
                        <Form.Item label={value[0]} name={value[0]}>
                          {value[1] !== undefined && <Input />}
                        </Form.Item>
                      );
                    }
                  }
                }
              )}

            <Button type="primary" htmlType="submit">
              저장
            </Button>
          </Form>
        </Modal>
      )}
      {selectedRowKeys.length > 0 && (
        <Button type="primary" onClick={onOpenModifiedModal}>
          수정
        </Button>
      )}
      <div>
        <Table
          rowSelection={{
            ...rowSelection,
          }}
          columns={tableIndexingData}
          dataSource={columnData}
          pagination={{ position: ["none"], pageSize: value?.currentCount }}
        />
      </div>
    </>
  );
};

export default CommonTable;
