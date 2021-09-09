import React, { useCallback, useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import CommonTable from "../../components/CommonTable";
import Navigation from "../../layouts/Navigation";
import { ROOTSTATE } from "../../redux_folder/reducers/root";
import {
  getStudentInfoPagingAction,
  I_StudentSorting,
} from "../../redux_folder/actions/admin/index";
import { Button, Input, Pagination } from "antd";
import { Switch, Radio, Form, Space } from "antd";
import SortingForm from "../../components/SortingForm";
import { resetIntegrataionMessage } from "../../redux_folder/actions/admin/index";
import { Redirect } from "react-router";

interface I_TableDataFormat {
  id: number;
  name: string;
  studentCode: number;
  email: string;
  phoneNumber: string;
  domainName: string;
  databaseName: string;
}

export const sortinfCondRadio = ["sort", "equal", "like"] as const;

const MainAdminPage = () => {
  const { allStudentInfo_paging } = useSelector(
    (state: ROOTSTATE) => state.admin
  );
  const dispatch = useDispatch();
  const [selectedRowKeys, setSelectedRowKeys] = useState<any>([]);
  const [loading, setLoading] = useState(false);
  const [sortingCond, setSortingCond] = useState<null>(null);
  const { defaultAdminInfo } = useSelector((state: ROOTSTATE) => state.admin);
  const [tableSortRadio, setTableSortRadio] =
    useState<typeof sortinfCondRadio[number]>();
  const start = useCallback(() => {
    setLoading(true);
    setTimeout(() => {
      setLoading(false);
      setSelectedRowKeys([]);
    }, 1000);
  }, []);
  const onChnagePagination = (value: any) => {
    console.log(value);
    console.log("sortingCond", sortingCond);

    dispatch(
      getStudentInfoPagingAction.ACTION.REQUEST({
        page: Number(value) - 1,
        size: 10,
        sort: sortingCond,
      })
    );
  };
  const onChangeRaddio = useCallback((value: any) => {
    console.log(value.target.value);
    setTableSortRadio(value.target.value);
  }, []);
  const getData = (value: any) => {
    setSortingCond(value);
  };
  useEffect(() => {
    dispatch(resetIntegrataionMessage());
    dispatch(
      getStudentInfoPagingAction.ACTION.REQUEST({
        page: 0,
        size: 10,
      })
    );
  }, []);
  if (!defaultAdminInfo) {
    return <Redirect to="/" />;
  }
  return (
    <>
      <Navigation />
      <Radio.Group onChange={onChangeRaddio} value={tableSortRadio}>
        <Radio value={"sort"}>정렬</Radio>
        <Radio value={"equal"}>동일한 정보</Radio>
        <Radio value={"like"}>유사 조건</Radio>
      </Radio.Group>
      {tableSortRadio !== undefined && (
        <SortingForm getSoringCond={getData} mode={tableSortRadio} />
      )}
      <div style={{ marginBottom: 16 }}>
        {/* <Button
          type="primary"
          onClick={start}
          loading={loading}
        >
          Reload
        </Button> */}
        {/* <span style={{ marginLeft: 8 }}>
          {hasSelected ? `Selected ${selectedRowKeys.length} items` : ""}
        </span> */}
      </div>
      <CommonTable value={allStudentInfo_paging} />
      <Pagination
        defaultCurrent={allStudentInfo_paging?.currentPage}
        total={allStudentInfo_paging?.totalCount}
        onChange={onChnagePagination}
      />
    </>
  );
};

export default MainAdminPage;
