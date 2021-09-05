import React from "react";

const Member = () => {
  return (
    <>
      <h2>멤버 등록</h2>
      <form action="" method="POST">
        <p>
          <label> 이름</label>
          <input type="text" placeholder="이름" name="name" required />
        </p>
        <p>
          <label> 도시</label>
          <input type="text" placeholder="도시" name="city" />
        </p>
        <p>
          <label> 우편 번호</label>
          <input type="text" placeholder="우편 번호" name="cityCode" />
        </p>
        <p>
          <label> 자세한 도시 정보</label>
          <input type="text" placeholder="자세한 도시 정보" name="detailcity" />
        </p>
        <p>
          <label> 나이</label>
          <input type="number" placeholder="나이" name="age" />
        </p>
        <p>
          <label> 이메일</label>
          <input type="text" placeholder="이메일" name="email" />
        </p>
        <p>
          <input type="submit" />
        </p>
      </form>
    </>
  );
};

export default Member;
