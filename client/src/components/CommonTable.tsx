import React, { FC, useCallback, useEffect, useState } from "react";
import { Table, Button } from "antd";
import {
  I_AllStudentInfoPaging_Admin,
  I_AllStudentInfo_Adamin,
} from "../types/storeType";
import { keys } from "ts-transformer-keys";

const columns = [
  {
    title: "Name",
    dataIndex: "name",
  },
  {
    title: "Age",
    dataIndex: "age",
  },
  {
    title: "Address",
    dataIndex: "address",
  },
];

const data: any = [];
for (let i = 0; i < 46; i++) {
  data.push({
    key: i,
    name: `Edward King ${i}`,
    age: 32,
    address: `London, Park Lane no. ${i}`,
  });
}

interface CommonTableProps {
  value: null | I_AllStudentInfoPaging_Admin;
}
type columnName =
  | "name"
  | "studentCode"
  | "email"
  | "phoneNumber"
  | "domainName"
  | "databaseName";
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
];
const CommonTable: FC<CommonTableProps> = ({ value }) => {
  const [selectedRowKeys, setSelectedRowKeys] = useState<any>([]);
  const [columnData, setColumnData] = useState<I_TableDataFormat[] | null>(
    null
  );

  const onSelectChange = (selectedRowKeys: any) => {
    console.log("selectedRowKeys changed: ", selectedRowKeys);
    setSelectedRowKeys(selectedRowKeys);
  };
  const rowSelection = {
    selectedRowKeys,
    onChange: onSelectChange,
  };

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
                : (merge.databaseName = nullData);
              merge.id = cur.id;
              merge.name = cur.name;
              merge.studentCode = cur.studentCode;
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
    <div>
      <Table
        rowSelection={rowSelection}
        columns={tableIndexingData}
        dataSource={columnData}
        pagination={{ position: ["none"] }}
      />
    </div>
  );
};

export default CommonTable;
