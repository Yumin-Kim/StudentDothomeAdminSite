import React from "react";
import { render } from "react-dom";
import { Provider } from "react-redux";
import App from "./pages/App";
import configure from "./redux_folder/store";

const store = configure();

render(
  <Provider store={store}>
    <App />
  </Provider>,
  document.getElementById("root")
);
