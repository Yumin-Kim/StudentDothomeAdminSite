import React from "react";
import { Form, Input, Button, Select } from "antd";
import { EyeInvisibleOutlined, EyeTwoTone } from "@ant-design/icons";
const { Option } = Select;

export const layout = {
  labelCol: { span: 2, offset: 1 },
  wrapperCol: { span: 10 },
};
export const tailLayout = {
  wrapperCol: { offset: 2, span: 16 },
};
const inputArrayData = [
  "Your Key 값",
  "학번",
  "이름",
  "password",
  "confirm password",
  "Phone Number",
  "Email",
] as const;

const FormComponent = () => {
  const [form] = Form.useForm();

  const onFinish = (values: any) => {
    console.log(values);
  };

  const onReset = () => {
    form.resetFields();
  };

  const onFill = () => {
    form.setFieldsValue({
      multiCode: "123123123123asdasdas",
      name: "홍길동",
      studentCode: 201610000,
      confirmPassword: 970417,
      password: 970417,
      phoneNumber: "010-8939-8932",
      email: "seowon@email.com",
    });
  };

  return (
    <Form {...layout} form={form} name="control-hooks" onFinish={onFinish}>
      <Form.Item
        name="multiCode"
        label="Your Key 값"
        rules={[{ required: true }]}
      >
        <Input />
      </Form.Item>
      <Form.Item name="studentCode" label="학번" rules={[{ required: true }]}>
        <Input type="number" />
      </Form.Item>
      <Form.Item name="name" label="이름" rules={[{ required: true }]}>
        <Input />
      </Form.Item>
      <Form.Item name="password" label="password" rules={[{ required: true }]}>
        <Input.Password
          placeholder="생년월일을 입력해주세요"
          iconRender={visible =>
            visible ? <EyeTwoTone /> : <EyeInvisibleOutlined />
          }
        />
      </Form.Item>
      <Form.Item
        name="confirmPassword"
        label="confirm password"
        rules={[{ required: true }]}
      >
        <Input.Password
          placeholder="동일한 비밀번호를 입력해주세요"
          iconRender={visible =>
            visible ? <EyeTwoTone /> : <EyeInvisibleOutlined />
          }
        />
      </Form.Item>
      <Form.Item
        name="phoneNumber"
        label="Phone Number"
        rules={[{ required: true }]}
      >
        <Input />
      </Form.Item>
      <Form.Item name="email" label="Email" rules={[{ required: true }]}>
        <Input type="email" />
      </Form.Item>
      {/* <Form.Item name="gender" label="Gender" rules={[{ required: true }]}>
        <Select
          placeholder="Select a option and change input text above"
          onChange={onGenderChange}
          allowClear
        >
          <Option value="male">male</Option>
          <Option value="female">female</Option>
          <Option value="other">other</Option>
        </Select>
      </Form.Item> */}
      {/* <Form.Item
        noStyle
        shouldUpdate={(prevValues, currentValues) =>
          prevValues.gender !== currentValues.gender
        }
      >
        {({ getFieldValue }) =>
          getFieldValue("gender") === "other" ? (
            <Form.Item
              name="customizeGender"
              label="Customize Gender"
              rules={[{ required: true }]}
            >
              <Input />
            </Form.Item>
          ) : null
        }
      </Form.Item> */}
      <Form.Item {...tailLayout}>
        <Button type="primary" htmlType="submit">
          가입
        </Button>
        <Button htmlType="button" onClick={onReset}>
          리셋
        </Button>
        <Button type="link" htmlType="button" onClick={onFill}>
          예시 확인 하기
        </Button>
      </Form.Item>
    </Form>
  );
};

export default FormComponent;
