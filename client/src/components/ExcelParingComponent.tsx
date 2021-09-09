import { Button, Input, message, Table, Typography } from "antd";
import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import XLSX from "xlsx";
import { ROOTSTATE } from "../redux_folder/reducers/root";
import {
  concurrentCreateAdminToStudentAction,
  resetIntegrataionMessage,
} from "../redux_folder/actions/admin/index";

const { Title } = Typography;

const SheetJSFT = [
  "xlsx",
  "xlsb",
  "xlsm",
  "xls",
  "xml",
  "csv",
  "txt",
  "ods",
  "fods",
  "uos",
  "sylk",
  "dif",
  "dbf",
  "prn",
  "qpw",
  "123",
  "wb*",
  "wq*",
  "html",
  "htm",
]
  .map(function (x) {
    return "." + x;
  })
  .join(",");
export const make_cols = (refstr: any) => {
  let o = [],
    C = XLSX.utils.decode_range(refstr).e.c + 1;
  for (var i = 0; i < C; ++i) o[i] = { name: XLSX.utils.encode_col(i), key: i };
  return o;
};
const tableCol = [
  {
    title: "이름",
    dataIndex: "name",
  },
  {
    title: "학번",
    dataIndex: "studentCode",
  },
];
const ExcelParingComponent = () => {
  const [file, setFile] = useState({});
  const [fileData, setFileData] = useState([]);
  const [cols, setCols] = useState([]);
  const [parsingToJSON, setParsingToJSON] = useState(false);
  const dispatch = useDispatch();
  const {
    defaultAdminInfo,
    integrationSucessMessage,
    integrationErrorMessage,
    integrationRequestMessage,
  } = useSelector((state: ROOTSTATE) => state.admin);

  const handleChange = (e: any) => {
    setParsingToJSON(false);
    setFileData([]);
    setFile({});
    setCols([]);
    const files = e.target.files;
    if (files && files[0]) setFile(files[0]);
  };

  const handleFile = () => {
    /* Boilerplate to set up FileReader */
    console.log("handleFile");

    const reader = new FileReader();
    const rABS = !!reader.readAsBinaryString;

    reader.onload = (e: any) => {
      /* Parse data */
      const bstr = e.target.result;
      const wb = XLSX.read(bstr, {
        type: rABS ? "binary" : "array",
        bookVBA: true,
      });
      /* Get first worksheet */
      const wsname = wb.SheetNames[0];
      const ws = wb.Sheets[wsname];
      /* Convert array of arrays */
      const data = XLSX.utils.sheet_to_json(ws) as any;
      /* Update state */
      setFileData(data);
      setCols(make_cols(ws["!ref"]));
      message.success(`${data.length}의 데이터가 변환 되었습니다.`);
      setParsingToJSON(true);
    };

    if (rABS) {
      reader.readAsBinaryString(file);
    } else {
      reader.readAsArrayBuffer(file);
    }
    if (parsingToJSON) {
      if (defaultAdminInfo) {
        dispatch(
          concurrentCreateAdminToStudentAction.ACTION.REQUEST({
            adminId: defaultAdminInfo.id,
            studentInfos: fileData,
          })
        );
      } else {
        message.error("관리자 로그인을 해주세요");
      }
      return;
    }
  };

  useEffect(() => {
    console.log("fileData", fileData);
    if (integrationSucessMessage) {
      message.success(integrationSucessMessage);
      setFileData([]);
      setFile({});
      setCols([]);
      setParsingToJSON(false);
      dispatch(resetIntegrataionMessage());
    }
    if (integrationErrorMessage) {
      setParsingToJSON(false);
      setFileData([]);
      setFile({});
      setCols([]);
      message.error(integrationErrorMessage);
      dispatch(resetIntegrataionMessage());
    }
  }, [
    integrationSucessMessage,
    integrationErrorMessage,
    integrationRequestMessage,
  ]);

  return (
    <>
      <div>
        <label htmlFor="file">
          <Title level={2}> 엑셀을 넣어 학생을 일괄 입력하세요 </Title>
        </label>
        <br />
        <input
          type="file"
          className="form-control"
          id="file"
          accept={SheetJSFT}
          onChange={handleChange}
        />
        <br />
        <Button type="primary" htmlType="submit" onClick={handleFile}>
          {parsingToJSON ? "데이터 저장!" : "변환"}
        </Button>

        {fileData && fileData.length > 0 && (
          <Table
            columns={tableCol}
            dataSource={fileData}
            pagination={{ position: ["none"], pageSize: fileData.length }}
          />
        )}
      </div>
    </>
  );
};

export default ExcelParingComponent;
