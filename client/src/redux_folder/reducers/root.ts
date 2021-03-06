import { combineReducers } from "redux";
import adminReducer from "./admin";
import studentReducer from "./student";
import utilReducer from "./utils";
import studentPortfolioReducer from "./studentPortfolie";

const RootReducer = combineReducers({
  admin: adminReducer,
  student: studentReducer,
  util: utilReducer,
  studentPortfolio: studentPortfolioReducer,
});

export type ROOTSTATE = ReturnType<typeof RootReducer>;
export default RootReducer;
