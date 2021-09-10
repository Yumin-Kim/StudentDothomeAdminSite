import React, { FC } from "react";
import { Form, Switch } from "antd";

interface I_FormItemComponent {
  value: any[];
  label: string;
  name: string;
  checkedChild: string;
  unCheckChild: string;
  validName: string;
}

const FormItemComponent: FC<I_FormItemComponent> = ({
  value,
  label,
  name,
  checkedChild,
  unCheckChild,
  validName,
}) => {
  console.log(value, label, name, checkedChild, unCheckChild, validName);
  console.log(value[1]);
  console.log(validName);

  console.log(value[1] === validName);
  return (
    <Form.Item name={name} label={label}>
      {value[1] === validName ? (
        <Switch
          checkedChildren={checkedChild}
          unCheckedChildren={unCheckChild}
          defaultChecked
        />
      ) : (
        <Switch
          checkedChildren={checkedChild}
          unCheckedChildren={unCheckChild}
        />
      )}
    </Form.Item>
  );
};
export default FormItemComponent;
