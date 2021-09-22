import React, { FC, useEffect, useRef, useState } from "react";
import { Form, Input, Button, Space, Select, Switch } from "antd";
const { Option } = Select;
import { MinusCircleOutlined, PlusOutlined } from "@ant-design/icons";
import { useDispatch, useSelector } from "react-redux";
import {
  getStudentInfoPagingAction,
  searchEqualsConditionAction,
} from "../redux_folder/actions/admin/index";
import { sortinfCondRadio } from "../pages/AdminPage/MainAdminPage";
import { BasicSortingBox, EqualsSortingBox } from "./RadiooBox";
import { I_SearchCondition_Admin } from "../types/storeType";
import {
  searchSimliarV1CondAction,
  eqaulConditionAction,
} from "../redux_folder/actions/admin/index";
import { ROOTSTATE } from "../redux_folder/reducers/root";
import { similarConditionAction } from "../redux_folder/actions/admin/index";

interface SortingFormProps {
  getSoringCond: any;
  mode: typeof sortinfCondRadio[number];
}

const SortingForm: FC<SortingFormProps> = ({ getSoringCond, mode }) => {
  const dispatch = useDispatch();
  const [form] = Form.useForm();
  const [text, setText] = useState([]);
  const [text1, setText1] = useState<string[] | []>([]);
  const [searchLiskCondValid, setSearchLiskCondValid] = useState("");
  const { sortingEqualCond, sortingSimilarCond } = useSelector(
    (state: ROOTSTATE) => state.admin
  );

  const handleChange = (value: any, name: any) => {
    console.log("handleChange", value, name);

    if (value.includes("_")) value = value.split("_")[1];
    setText(prev => {
      prev[name] = value;
      return [...prev];
    });
  };
  const onFinish = (values: any) => {
    console.log("onFinish");
    let sortingCondField = null;
    if (mode === "sort") {
      sortingCondField = (values.sorting as any[]).reduce(
        (prev, cur, index) => {
          const duplicateData = prev.map((value: any) => {
            if (value[cur.cond] === cur.cond) return true;
          });
          if (duplicateData[0]) return prev;
          const merge = {} as any;
          merge[cur.cond] = cur.cond;

          if (cur.sortCond === undefined || cur.sortCond)
            merge.sortCondition = "ASC";
          else merge.sortCondition = "DESC";
          cur.sortCond = "";
          prev.push(merge);
          return prev;
        },
        [] as any
      );
      dispatch(
        getStudentInfoPagingAction.ACTION.REQUEST({
          page: 0,
          size: 10,
          sort: sortingCondField,
        })
      );
    }
    const { sorting } = values;
    let searchData = {} as Partial<I_SearchCondition_Admin>;
    if (mode === "equal") {
      sorting.map((value: any) => {
        if (value.cond.includes("_")) {
          value.cond = value.cond.split("_")[1];
        }
        searchData[value.cond as keyof I_SearchCondition_Admin] =
          value.sortCond;
      });
      dispatch(eqaulConditionAction(searchData));

      dispatch(
        searchEqualsConditionAction.ACTION.REQUEST({
          paggable: {
            page: 0,
            size: 10,
          },
          search: searchData,
        })
      );
    }
    getSoringCond(sortingCondField);
  };

  const onSelectChange = (value: any) => {
    console.log("onSelectChange", value);
    if (value === 0) {
      setText(prev => {
        prev.shift();
        return prev;
      });
      setText1(prev => {
        prev.shift();
        return prev;
      });
    } else {
      console.log("splice");
      setText(prev => {
        prev.splice(value, 1);
        return prev;
      });
      setText1(prev => {
        prev.splice(value, 1);
        return prev;
      });
    }
  };
  useEffect(() => {
    const custom = text.reduce((prev, cur, index) => {
      prev[cur] = text1[index];
      return prev;
    }, {} as any);

    dispatch(similarConditionAction(custom));
    dispatch(
      searchSimliarV1CondAction.ACTION.REQUEST({
        paggable: { page: 0, size: 10 },
        search: custom,
      })
    );
  }, [searchLiskCondValid]);
  // 유사 조건으로 비교하는 로직 작성 onChange
  const onChangeSearchLike = (value: any) => {
    setSearchLiskCondValid(value);
    const indexing = Number(value.target.id.match(/[0-9]/g)[0]);
    if (value.target.value !== "") {
      console.log("indexing", indexing);
      if (text1[indexing])
        setText1(prev => {
          prev[indexing] = value.target.value;
          return prev;
        });
      else {
        setText1(prev => [...prev, value.target.value]);
      }
    }
  };

  useEffect(() => {
    form.setFieldsValue({
      sorting: [],
    });
    form.setFieldsValue({ sortCond: undefined });
    setText1([]);
    setText([]);
  }, [mode]);

  return (
    <Form
      name="dynamic_form_nest_item"
      onFinish={onFinish}
      autoComplete="off"
      form={form}
    >
      <Form.List name="sorting">
        {(fields, { add, remove }) => (
          <>
            {fields.map(({ key, name, fieldKey, ...restField }) => (
              <Space
                key={key}
                style={{ display: "flex", marginBottom: 8 }}
                align="baseline"
              >
                <Form.Item
                  {...restField}
                  name={[name, "cond"]}
                  fieldKey={[fieldKey, "cond"]}
                  rules={[
                    { required: true, message: "정렬 조건을 선택해주세요" },
                  ]}
                >
                  <Select
                    style={{ width: 160 }}
                    onChange={value => handleChange(value, name)}
                  >
                    <Option value="name">이름</Option>
                    <Option value="studentCode">학번</Option>
                    <Option value="phoneNumber">전화 번호</Option>
                    <Option value="siteInfo_domainName">도메인 명</Option>
                    <Option value="siteInfo_databaseName">
                      데이터 베이스 명
                    </Option>
                  </Select>
                </Form.Item>
                {mode === "sort" && (
                  <BasicSortingBox
                    restField={restField}
                    name={name}
                    fieldKey={fieldKey}
                  />
                )}
                {mode === "equal" && (
                  <EqualsSortingBox
                    restField={restField}
                    name={name}
                    fieldKey={fieldKey}
                  />
                )}
                {mode === "like" && (
                  <Form.Item
                    {...restField}
                    name={[name, "sortCond"]}
                    fieldKey={[fieldKey, "sortCond"]}
                    rules={[
                      { required: true, message: "검색할 조건을 확인해주세요" },
                    ]}
                  >
                    <Input onChange={onChangeSearchLike} />
                  </Form.Item>
                )}
                <MinusCircleOutlined
                  onClick={() => {
                    remove(name);
                    onSelectChange(name);
                  }}
                />
              </Space>
            ))}
            <Form.Item>
              <Button
                type="dashed"
                onClick={() => add()}
                icon={<PlusOutlined />}
              >
                정렬 조건을 추가해주세요
              </Button>
            </Form.Item>
          </>
        )}
      </Form.List>
      <Form.Item>
        <Button type="primary" htmlType="submit">
          정렬
        </Button>
      </Form.Item>
    </Form>
  );
};

export default SortingForm;
