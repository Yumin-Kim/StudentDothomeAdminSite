import React, { FC, useCallback, useEffect, useState } from "react";
import {
  Table,
  Button,
  Modal,
  Form,
  Input,
  Select,
  Switch,
  message,
} from "antd";
import {
  I_AllStudentInfoPaging_Admin,
  I_AllStudentInfo_Adamin,
} from "../types/storeType";
import { useDispatch, useSelector } from "react-redux";
import {
  concurrentModifiedAdminStudentInfoAction,
  resetIntegrataionMessage,
} from "../redux_folder/actions/admin/index";
import FormItemComponent from "./FormItemComponent";
import { ROOTSTATE } from "../redux_folder/reducers/root";
import Description_ModalList from "./Description_ModalList";

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
export interface I_TableDataFormat {
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
  const [selectedRowKeys, setSelectedRowKeys] = useState<
    Partial<I_TableDataFormat>[]
  >([]);
  const [columnData, setColumnData] = useState<I_TableDataFormat[] | null>(
    null
  );
  const [openModal, setOpenModal] = useState(false);

  const [form] = Form.useForm();
  const [changeSelected, setChangeSelected] = useState(0);
  const [modifiedFormList, setModifiedFormList] = useState<
    Omit<I_AllStudentInfo_Adamin, "siteInfo" | "adminName">[] | []
  >([]);
  const dispatch = useDispatch();
  const { integrationErrorMessage, integrationSucessMessage } = useSelector(
    (state: ROOTSTATE) => state.admin
  );
  //Modal State
  const [visible, setVisible] = useState(false);
  const [confirmLoading, setConfirmLoading] = useState(false);
  ///
  //Modal 창 오픈
  const onOpenModifiedModal = useCallback(() => {
    setOpenModal(true);
    setVisible(true);
  }, [openModal]);

  // 수정 완료 이벤트 발생
  const handleOk = () => {
    setConfirmLoading(true);
    if (modifiedFormList.length !== 0) {
      message.info(
        `${modifiedFormList.length}개의 데이터를 수정 요청 중입니다..`
      );
      setTimeout(() => {
        setVisible(false);
        setConfirmLoading(false);
        dispatch(
          concurrentModifiedAdminStudentInfoAction.ACTION.REQUEST(
            modifiedFormList
          )
        );
        setModifiedFormList([]);
      }, 1500);
    } else {
      setVisible(false);
      setConfirmLoading(false);
      message.warning("수정할 정보가 없습니다.");
    }
  };
  //수정된 정보를 저장했을때 발생하며 undefined , string일때 부가적으로 처리
  const onFinishForm = useCallback(
    (value: any) => {
      value.studentCode = Number(value.studentCode);
      const duplicateValidation = modifiedFormList.filter(
        (filterData, index) =>
          filterData.id === selectedRowKeys[changeSelected].id
      );
      if (typeof value.isDeleted === "string")
        value.isDeleted = value.isDeleted === "활성화" ? false : true;
      else {
        value.isDeleted = !value.isDeleted;
      }
      if (typeof value.inSchool === "string") {
        value.inSchool = value.inSchool === "재학중" ? true : false;
      }
      value["id"] = selectedRowKeys[changeSelected].id;
      setModifiedFormList(prev => {
        const duplicateData = prev.filter(
          filterData => filterData.id === selectedRowKeys[changeSelected].id
        );
        if (duplicateData.length > 0) {
          prev.forEach((val, index) => {
            if (val.id === selectedRowKeys[changeSelected].id) {
              prev.splice(index, 1, value);
            }
          });
          return [...prev];
        } else {
          return [...prev, value];
        }
      });
      message.success("수정할 정보를 저장했습니다.");
    },
    [changeSelected, selectedRowKeys, modifiedFormList]
  );
  //Modal 창에서 다른 학생을 선택 했을때 발생
  const onChangeSelect = useCallback(
    (value: any) => {
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
  //close Modal
  const handleCancel = () => {
    setVisible(false);
    setChangeSelected(0);
    setModifiedFormList([]);
  };
  //선택 항목 setState
  const rowSelection = {
    onChange: (
      selectedRowKeys: I_TableDataFormat[] | [],
      selectedRows: I_TableDataFormat[]
    ) => {
      setSelectedRowKeys(selectedRows);
    },
  };

  //Modal 창에서 사용자 변경시 데이터 자동으로 삽입
  useEffect(() => {
    if (selectedRowKeys.length > 0 && openModal) {
      console.log(selectedRowKeys[changeSelected]);

      form.setFieldsValue({
        name: selectedRowKeys[changeSelected].name,
        domainName: selectedRowKeys[changeSelected].domainName,
        studentCode: selectedRowKeys[changeSelected].studentCode,
        databaseName: selectedRowKeys[changeSelected].databaseName,
      });
    }
  }, [selectedRowKeys, changeSelected, openModal]);
  //정보 수정에 따른 메세지 제공
  useEffect(() => {
    if (integrationErrorMessage) {
      message.error(integrationErrorMessage);
    } else if (integrationSucessMessage) {
      message.success(integrationSucessMessage);
    }
    dispatch(resetIntegrataionMessage());
  }, [integrationSucessMessage, integrationErrorMessage]);
  // Table Data Source 삽입
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
  //
  return (
    <>
      {openModal && (
        <Modal
          title="학생 정보 수정"
          visible={visible}
          onOk={handleOk}
          confirmLoading={confirmLoading}
          onCancel={handleCancel}
          width={1000}
        >
          <Form form={form} onFinish={onFinishForm}>
            <Select name="selected" onChange={onChangeSelect} defaultValue={0}>
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
          {modifiedFormList.length > 0 && (
            <Description_ModalList modifiedList={modifiedFormList} />
          )}
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
