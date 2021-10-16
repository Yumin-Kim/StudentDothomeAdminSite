import { Content, Header } from "antd/lib/layout/layout";
import styled from "styled-components";

export const ContentCustom = styled(Content)`
  background-color: #fff;
`;

export const HeaderCustom = styled(Header)`
  background-color: #f0f2f5;
`;

export const ContentTemplateLayout = styled.div`
  max-width: 1200px;
  position: relative;
  margin: auto;
  will-change: transform;
`;
