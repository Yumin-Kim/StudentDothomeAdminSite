import Home from "../pages/Home";
import CheckStudentPage from "../pages/StudentPortFolioPage/CheckStudentPage";
import SignupStudentPage from "../pages/StudentPortFolioPage/SignupStudentPage";
import DefaultPortfolioPage from "../pages/StudentPortFolioPage/DefaultPortfolioPage";
import CreatePortFolioPage from "../pages/StudentPortFolioPage/CreatePortFolioPage";
import ModifiedPortFolioPage from "../pages/StudentPortFolioPage/ModifiedPortFolioPage";
import CreateStudentPage from "../pages/StudentPage/CreateStudentPage";
import MainStudentPage from "../pages/StudentPage/MainStudentPage";
import EditStudentPage from "../pages/StudentPage/EditStudentPage";
import MainInputPage from "../pages/MainInputPage";
import DeleteUtilInfoPage from "../pages/AdminPage/DeleteUtilInfoPage";
import ModifyAdminPage from "../pages/AdminPage/ModifyAdminPage";
import StudentCreateAdmin from "../pages/AdminPage/StudentCreateAdmin";

export const routes = [
  {
    path: "/portfolio/signup",
    component: SignupStudentPage,
  },
  {
    path: "/portfolio/valid",
    component: CheckStudentPage,
  },
  {
    path: "/portfolio/default",
    component: DefaultPortfolioPage,
  },
  {
    path: "/portfolio/create",
    component: CreatePortFolioPage,
  },
  {
    path: "/portfolio/modify",
    component: ModifiedPortFolioPage,
  },
  {
    path: "/student/create",
    component: CreateStudentPage,
  },
  {
    path: "/student/main",
    component: MainStudentPage,
  },
  {
    path: "/student/edit",
    component: EditStudentPage,
  },
  {
    path: "/student/default",
    component: CreateStudentPage,
  },
  {
    path: "/admin/main",
    component: MainStudentPage,
  },
  {
    path: "/admin/create",
    component: StudentCreateAdmin,
  },
  {
    path: "/admin/modify",
    component: ModifyAdminPage,
  },
  {
    path: "/admin/delete",
    component: DeleteUtilInfoPage,
  },
  {
    path: "/:stubing",
    component: MainInputPage,
  },
];
