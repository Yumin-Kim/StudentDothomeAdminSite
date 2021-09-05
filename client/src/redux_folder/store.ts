import createSagaMiddleware from "@redux-saga/core";
import { applyMiddleware, createStore } from "redux";
import { composeWithDevTools } from "redux-devtools-extension";
import RootReducer from "./reducers/root";
import RootSaga from "./sagas/root";

const sagaMiddlewares = createSagaMiddleware();

const configure = () => {
  let store;
  const midlewares = [sagaMiddlewares];
  if (process.env.NODE_ENV !== "production") {
    store = createStore(
      RootReducer,
      composeWithDevTools(applyMiddleware(...midlewares))
    );
  } else {
    store = createStore(RootReducer, applyMiddleware(...midlewares));
  }
  sagaMiddlewares.run(RootSaga);
  return store;
};

export default configure;
