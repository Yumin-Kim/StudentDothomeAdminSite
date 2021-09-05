import React, { FC } from "react";

interface InputBox {
  text: string;
  type: string;
  placeholder: string;
  name: string;
}

const InputBox: FC<InputBox> = ({ text, type, placeholder, name }) => {
  return (
    <p>
      <label> {text}</label>{" "}
      <input type={type} placeholder={placeholder} name={name} />
    </p>
  );
};

export default InputBox;
