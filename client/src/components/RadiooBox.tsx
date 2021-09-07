import React, { FC } from "react";
import { Form, Input, Button, Space, Select, Switch } from "antd";

export const BasicSortingBox: FC<any> = ({ restField, name, fieldKey }) => {
  return (
    <Form.Item
      {...restField}
      name={[name, "sortCond"]}
      fieldKey={[fieldKey, "sortCond"]}
    >
      <Switch checkedChildren="ASC" unCheckedChildren="DESC" defaultChecked />
    </Form.Item>
  );
};

export const EqualsSortingBox: FC<any> = ({ restField, name, fieldKey }) => {
  return (
    <Form.Item
      {...restField}
      name={[name, "sortCond"]}
      fieldKey={[fieldKey, "sortCond"]}
      rules={[{ required: true, message: "검색할 조건을 확인해주세요" }]}
    >
      <Input />
    </Form.Item>
  );
};
