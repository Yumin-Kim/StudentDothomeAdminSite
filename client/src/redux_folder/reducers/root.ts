import { combineReducers } from "redux";
import adminReducer from "./admin";
import studentReducer from "./student";
import utilReducer from "./utils";

const RootReducer = combineReducers({
  admin: adminReducer,
  student: studentReducer,
  util: utilReducer,
});

export type ROOTSTATE = ReturnType<typeof RootReducer>;
export default RootReducer;
