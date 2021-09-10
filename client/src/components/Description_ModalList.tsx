import { Descriptions, Typography } from "antd";
import React, { FC, useEffect, useState } from "react";
import { I_TableDataFormat } from "./CommonTable";
import { layout } from "../pages/StudentPage/CreateStudentPage";
const { Title } = Typography;
interface I_Description_ModalList {
  modifiedList: any;
}

const Description_ModalList: FC<I_Description_ModalList> = ({
  modifiedList,
}) => {
  const [data, setData] = useState([]);
  useEffect(() => {
    if (modifiedList)
      if (modifiedList.length > 0) {
        const da = modifiedList.map(value => {
          return Object.entries(value).map((obj_parseValue: any) => {
            // console.log(obj_parseValue[0]);
            if (obj_parseValue[0] === "inSchool") {
              return obj_parseValue[1] ? "재학중" : "휴학 또는 졸업";
            } else if (obj_parseValue[0] === "isDeleted") {
              return obj_parseValue[1] ? "비활성화" : "활성화";
            } else if (obj_parseValue[0] === "id") {
              return;
            } else {
              return obj_parseValue[1];
            }
          });
        });
        setData(da);
      }
  }, [modifiedList]);
  console.log("Description_ModalList", modifiedList);
  return (
    <>
      {data.length > 0 &&
        data.map((value, index) => (
          <Descriptions
            column={{ xxl: 4, xl: 4, lg: 4, md: 3, sm: 4, xs: 1 }}
            title={`${index + 1}번째`}
            bordered
            layout="horizontal"
          >
            {value.map(inner => (
              <>
                <Descriptions.Item>{inner}</Descriptions.Item>
              </>
            ))}
          </Descriptions>
        ))}
    </>
  );
};

export default Description_ModalList;
