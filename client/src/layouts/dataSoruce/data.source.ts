import React from "react";
export const Nav00DataSource = {
  wrapper: { className: "header0 home-page-wrapper" },
  page: { className: "home-page" },
  logo: {
    className: "header0-logo",
    children: "http://media.seowon.ac.kr/2021/studentImages/logo.png",
  },
  Menu: {
    className: "header0-menu",
    children: [
      {
        name: "item0",
        className: "header0-item",
        children: {
          href: "/find",
          children: [{ children: "개인 홈페이지 관리", name: "text" }],
        },
      },
      {
        name: "item1",
        className: "header0-item",
        children: {
          href: "/admin",
          children: [{ children: "통합 페이지 관리", name: "text" }],
        },
      },
      {
        name: "item2",
        className: "header0-item",
        children: {
          href: "/portfolio/valid",
          children: [{ children: "졸업 작품 업로드", name: "text" }],
        },
      },
      {
        name: "item3",
        className: "header0-item",
        children: {
          href: "#",
          children: [{ children: "WAS 관리", name: "text" }],
        },
      },
    ],
  },
  mobileMenu: { className: "header0-mobile-menu" },
};
export const Banner01DataSource = {
  wrapper: { className: "banner0 kusjt1jbbee-editor_css" },
  textWrapper: { className: "banner0-text-wrapper" },
  title: {
    className: "banner0-title",
    children: "http://media.seowon.ac.kr/2021/studentImages/bannerLogo.png",
  },
  content: {
    className: "banner0-content",
    children: "학부 서버 이용 사이트",
  },
};
export const Content00DataSource = {
  wrapper: { className: "home-page-wrapper content0-wrapper" },
  page: { className: "home-page content0" },
  OverPack: { playScale: 0.3, className: "" },
  titleWrapper: {
    className: "title-wrapper",
    children: [{ name: "title", children: "제공 되는 서비스" }],
  },
  childWrapper: {
    className: "content0-block-wrapper",
    children: [
      {
        name: "block0",
        className: "content0-block",
        md: 8,
        xs: 24,
        children: {
          className: "content0-block-item",
          children: [
            {
              name: "image",
              className: "content0-block-icon",
              children: "https://cdn-icons-png.flaticon.com/512/841/841466.png",
            },
            {
              name: "title",
              className: "content0-block-title",
              children: "개인 홈페이지 개설",
            },
            {
              name: "content",
              className: "banner0-content",
              children: "PHP & MySQL를 활용한 홈페이지 개설 ",
            },
            {
              name: "button",
              children: {
                className: "banner0-button",
                children: "Learn More",
                to: "/find",
              },
            },
          ],
        },
      },
      {
        name: "block1",
        className: "content0-block",
        md: 8,
        xs: 24,
        children: {
          className: "content0-block-item",
          children: [
            {
              name: "image",
              className: "content0-block-icon",
              children:
                "http://media.seowon.ac.kr/2021/studentImages/upload-file.png",
            },
            {
              name: "title",
              className: "content0-block-title",
              children: "년도별 졸업 작품 업로드",
            },
            {
              name: "content",
              children: "이미지 영상링크 정보를 입력",
            },
            {
              name: "button",
              children: {
                className: "banner0-button",
                children: "Learn More",
                to: "/portfolio/valid",
              },
            },
          ],
        },
      },
      {
        name: "block2",
        className: "content0-block",
        md: 8,
        xs: 24,
        children: {
          className: "content0-block-item",
          children: [
            {
              name: "image",
              className: "content0-block-icon",
              children:
                "http://media.seowon.ac.kr/2021/studentImages/application.png",
            },
            {
              name: "title",
              className: "content0-block-title",
              children: "Web application Server 관리",
            },
            {
              name: "content",
              children: "NodeJS , Spring , Django 서버 연동 ",
            },
            {
              name: "content",
              className: "content0-block-title",
              children: "준비중...",
            },
          ],
        },
      },
    ],
  },
};
export const Content50DataSource = {
  wrapper: { className: "home-page-wrapper content5-wrapper" },
  page: { className: "home-page content5" },
  OverPack: { playScale: 0.3, className: "" },
  titleWrapper: {
    className: "title-wrapper",
    children: [
      {
        name: "title",
        children: "어떤 카테고리를 배치할지 생각하기?",
        className: "title-h1",
      },
      {
        name: "content",
        className: "title-content",
        children: "在这里用一段话介绍服务的案例情况",
      },
    ],
  },
  block: {
    className: "content5-img-wrapper",
    gutter: 16,
    children: [
      {
        name: "block0",
        className: "block",
        md: 6,
        xs: 24,
        children: {
          wrapper: { className: "content5-block-content" },
          img: {
            children:
              "https://t.alipayobjects.com/images/rmsweb/T11aVgXc4eXXXXXXXX.svg",
          },
          content: { children: "Ant Design" },
        },
      },
      {
        name: "block1",
        className: "block",
        md: 6,
        xs: 24,
        children: {
          wrapper: { className: "content5-block-content" },
          img: {
            children:
              "https://zos.alipayobjects.com/rmsportal/faKjZtrmIbwJvVR.svg",
          },
          content: { children: "Ant Motion" },
        },
      },
      {
        name: "block2",
        className: "block",
        md: 6,
        xs: 24,
        children: {
          wrapper: { className: "content5-block-content" },
          img: {
            children:
              "https://t.alipayobjects.com/images/rmsweb/T11aVgXc4eXXXXXXXX.svg",
          },
          content: { children: "Ant Design" },
        },
      },
      {
        name: "block3",
        className: "block",
        md: 6,
        xs: 24,
        children: {
          wrapper: { className: "content5-block-content" },
          img: {
            children:
              "https://zos.alipayobjects.com/rmsportal/faKjZtrmIbwJvVR.svg",
          },
          content: { children: "Ant Motion" },
        },
      },
      {
        name: "block4",
        className: "block",
        md: 6,
        xs: 24,
        children: {
          wrapper: { className: "content5-block-content" },
          img: {
            children:
              "https://t.alipayobjects.com/images/rmsweb/T11aVgXc4eXXXXXXXX.svg",
          },
          content: { children: "Ant Design" },
        },
      },
      {
        name: "block5",
        className: "block",
        md: 6,
        xs: 24,
        children: {
          wrapper: { className: "content5-block-content" },
          img: {
            children:
              "https://zos.alipayobjects.com/rmsportal/faKjZtrmIbwJvVR.svg",
          },
          content: { children: "Ant Motion" },
        },
      },
      {
        name: "block6",
        className: "block",
        md: 6,
        xs: 24,
        children: {
          wrapper: { className: "content5-block-content" },
          img: {
            children:
              "https://t.alipayobjects.com/images/rmsweb/T11aVgXc4eXXXXXXXX.svg",
          },
          content: { children: "Ant Design" },
        },
      },
      {
        name: "block7",
        className: "block",
        md: 6,
        xs: 24,
        children: {
          wrapper: { className: "content5-block-content" },
          img: {
            children:
              "https://zos.alipayobjects.com/rmsportal/faKjZtrmIbwJvVR.svg",
          },
          content: { children: "Ant Motion" },
        },
      },
    ],
  },
};
export const Content30DataSource = {
  wrapper: { className: "home-page-wrapper content3-wrapper" },
  page: { className: "home-page content3" },
  OverPack: { playScale: 0.3 },
  titleWrapper: {
    className: "title-wrapper",
    children: [
      {
        name: "title",
        children: "이용서비스 소개",
        className: "title-h1",
      },
      {
        name: "content",
        className: "title-content",
        children: "학생들이 편리하게 사용할 수 있는 기능 제공",
      },
    ],
  },
  block: {
    className: "content3-block-wrapper",
    children: [
      {
        name: "block0",
        className: "content3-block",
        md: 8,
        xs: 24,
        children: {
          icon: {
            className: "content3-icon",
            children:
              "http://media.seowon.ac.kr/2021/studentImages/uploadFile.png",
          },
          textWrapper: { className: "content3-text" },
          title: { className: "content3-title", children: "정적 사이트 제공" },
          content: {
            className: "content3-content",
            children: "닷홈을 사용하지 않고 학부 서버를 활용",
          },
        },
      },
      {
        name: "block1",
        className: "content3-block",
        md: 8,
        xs: 24,
        children: {
          icon: {
            className: "content3-icon",
            children:
              "http://media.seowon.ac.kr/2021/studentImages/setting.png",
          },
          textWrapper: { className: "content3-text" },
          title: { className: "content3-title", children: "PHP&MySQL 사용" },
          content: {
            className: "content3-content",
            children: "학부 수업시 사용하는 PHP 와 MySQL를 ..",
          },
        },
      },
      {
        name: "block2",
        className: "content3-block",
        md: 8,
        xs: 24,
        children: {
          icon: {
            className: "content3-icon",
            children:
              "http://media.seowon.ac.kr/2021/studentImages/webSite.png",
          },
          textWrapper: { className: "content3-text" },
          title: {
            className: "content3-title",
            children: "학생 서버 개설간 관리 기능",
          },
          content: {
            className: "content3-content",
            children:
              "SSH를 활용하여 학생별 디렉토리 , MySQL 사용자 관리 기능 제공 및 졸업 휴학 여부에 따른 활성화",
          },
        },
      },
      {
        name: "block3",
        className: "content3-block",
        md: 8,
        xs: 24,
        children: {
          icon: {
            className: "content3-icon",
            children:
              "http://media.seowon.ac.kr/2021/studentImages/wasImage.png",
          },
          textWrapper: { className: "content3-text" },
          title: {
            className: "content3-title",
            children: "WAS 배포",
          },
          content: {
            className: "content3-content",
            children:
              "PHP가 아닌 Spring , NodeJS , Django 배포 및 모니터링 기능 제공 ",
          },
        },
      },
      {
        name: "block4",
        className: "content3-block",
        md: 8,
        xs: 24,
        children: {
          icon: {
            className: "content3-icon",
            children:
              "http://media.seowon.ac.kr/2021/studentImages/uploadapi.png",
          },
          textWrapper: { className: "content3-text" },
          title: {
            className: "content3-title",
            children: "업로드 및 업로드 정보 api 제공",
          },
          content: {
            className: "content3-content",
            children: "년도 별 졸업 작품 업로드 및 업로드 된 정보 API 제공",
          },
        },
      },
      {
        name: "block5",
        className: "content3-block",
        md: 8,
        xs: 24,
        children: {
          icon: {
            className: "content3-icon",
            children:
              "http://media.seowon.ac.kr/2021/studentImages/database.png",
          },
          textWrapper: { className: "content3-text" },
          title: {
            className: "content3-title",
            children: "데이터 베이스 관리",
          },
          content: {
            className: "content3-content",
            children:
              "phpmyadmin의 복잡한 기능을 제거하여 사용하는 기능만 사용하자",
          },
        },
      },
    ],
  },
};
export const Footer10DataSource = {
  wrapper: { className: "home-page-wrapper footer1-wrapper" },
  OverPack: { className: "footer1", playScale: 0.2 },
  block: {
    className: "home-page",
    gutter: 0,
    children: [
      {
        name: "block0",
        xs: 24,
        md: 6,
        className: "block",
        title: {
          className: "logo",
          children: "http://media.seowon.ac.kr/2021/studentImages/logo.png",
        },
        childWrapper: {
          className: "slogan",
          children: [
            {
              name: "content0",
              children: "",
            },
          ],
        },
      },
      {
        name: "block1",
        xs: 24,
        md: 6,
        className: "block",
        title: { children: "학생 카테고리" },
        childWrapper: {
          children: [
            { name: "link0", href: "#", children: "개인 홈페이지 관리" },
            { name: "link1", href: "#", children: "졸업 작품 업로드" },
            { name: "link2", href: "#", children: "WAS 관리" },
          ],
        },
      },
      {
        name: "block2",
        xs: 24,
        md: 6,
        className: "block",
        title: { children: "관리자 카테고리" },
        childWrapper: {
          children: [
            { href: "/admin", name: "link0", children: "통합 관리자 페이지" },
          ],
        },
      },
    ],
  },
  copyrightWrapper: { className: "copyright-wrapper" },
  copyrightPage: { className: "home-page" },
  copyright: {
    className: "copyright",
  },
};
