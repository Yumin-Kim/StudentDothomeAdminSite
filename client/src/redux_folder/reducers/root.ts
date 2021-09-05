import { combineReducers } from "redux";
import adminReducer from "./admin";
import studentReducer from "./student";

const RootReducer = combineReducers({
  admin: adminReducer,
  student: studentReducer,
});

export type ROOTSTATE = ReturnType<typeof RootReducer>;
export default RootReducer;
