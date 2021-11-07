//요구 조건 졸전위들 먼저 보이고
// id
const createDivTabBox = ({
  jobName,
  studentCode,
  mainImage,
  mainDescription,
}) => ` <div class="section-grid__item">
                    <div class="figure-image section-image">
                      <a
                        class="hover-zoom figure-image__link"
                        data-arts-cursor="data-arts-cursor"
                        data-arts-cursor-scale="1.6"
                        data-arts-cursor-icon="keyboard_arrow_right"
                        data-arts-cursor-hide-native="true"
                        href="./next.php?studentCode=${studentCode}"
                        target="_self"
                      >
                        <div class="hover-zoom__inner">
                          <div class="hover-zoom__zoom">
                            <div class="figure-image__wrapper-img">
                              <div class="lazy">
                                <img
                                  data-src="./studentImages/${studentCode}_${mainImage}"
                                  src="#"
                                  alt=""
                                  width="840"
                                  height="525"
                                />
                              </div>
                            </div>
                          </div>
                        </div>
                        <div
                          class="
                            figure-image__caption
                            section-image__caption
                            hover-zoom__caption
                            text-left
                            section-image__caption-horizontal
                          "
                        >
                          <div class="figure-image__wrapper-caption paragraph">
                            ${mainDescription}
                          </div></a>
                        </div>
                    </div></div>`;

//       <div
//     class="
//       grid__item
//       grid__item_desktop-4
//       grid__item_tablet-6
//       grid__item_mobile-12
//       grid__item_fluid-3
//       grid__item_fluid-3-fancy
//       js-grid__item
//     "
//   >
//     <div class="section-grid__item">
//       <div class="figure-image section-image">
//         <a
//           class="hover-zoom figure-image__link"
//           data-arts-cursor="data-arts-cursor"
//           data-arts-cursor-scale="1.6"
//           data-arts-cursor-icon="keyboard_arrow_right"
//           data-arts-cursor-hide-native="true"
//           href="page-inner-work.html"
//           target="_self"
//         >
//           <!-- zoom effect container -->
//           <div class="hover-zoom__inner">
//             <div class="hover-zoom__zoom">
//               <!-- image -->
//               <div class="figure-image__wrapper-img">
//                 <div class="lazy">
//                   <img
//                     data-src="img/assets/sectionDemo/portfolio-slider-screen-3.jpg"
//                     src="#"
//                     alt=""
//                     width="840"
//                     height="525"
//                   />
//                 </div>
//               </div>
//               <!-- - image -->
//             </div>
//           </div>
//           <!-- - zoom effect container -->
//           <!-- caption -->
//           <div
//             class="
//               figure-image__caption
//               section-image__caption
//               hover-zoom__caption
//               text-left
//               section-image__caption-horizontal
//             "
//           >
//             <div class="figure-image__wrapper-caption paragraph">
//               Fullscreen / Parallax / Horizontal
//             </div>
//           </div></a>
//       </div>
//     </div>
//   </div>
//tab 동적 생성
(function appendJobListTabBox() {
  //ajax통신
  //php sql 후 배열저장
  const mainGridDummyData = [
    {
      name: "최규민",
      //2021StudentImages/201610309_main.jpg
      mainImage: "main.jpg",
      mainDescription: "최규민1 / 기획자 / NH콕뱅크",
      studentCode: 201611600,
      jobName: "productManager",
    },
    {
      name: "최규민",
      //2021StudentImages/201610309_main.jpg
      mainImage: "main.jpg",
      mainDescription: "최규민2 / 기획자 / NH콕뱅크",
      studentCode: 201611600,
      jobName: "productManager",
    },
    {
      name: "최규민",
      //2021StudentImages/201610309_main.jpg
      mainImage: "main.jpg",
      mainDescription: "최규민2 / 기획자 / NH콕뱅크",
      studentCode: 201611600,
      jobName: "productManager",
    },
    {
      name: "최규민",
      //2021StudentImages/201610309_main.jpg
      mainImage: "main.jpg",
      mainDescription: "최규민2 / 기획자 / NH콕뱅크",
      studentCode: 201611600,
      jobName: "productManager",
    },
    {
      name: "최규민",
      //2021StudentImages/201610309_main.jpg
      mainImage: "main.jpg",
      mainDescription: "최규민2 / 기획자 / NH콕뱅크",
      studentCode: 201611600,
      jobName: "productManager",
    },
    {
      name: "최규민",
      //2021StudentImages/201610309_main.jpg
      mainImage: "main.jpg",
      mainDescription: "최규민2 / 기획자 / NH콕뱅크",
      studentCode: 201611600,
      jobName: "productManager",
    },
    {
      name: "최규민",
      //2021StudentImages/201610309_main.jpg
      mainImage: "main.jpg",
      mainDescription: "최규민2 / 기획자 / NH콕뱅크",
      studentCode: 201611600,
      jobName: "productManager",
    },
    {
      name: "최규민",
      //2021StudentImages/201610309_main.jpg
      mainImage: "main.jpg",
      mainDescription: "최규민2 / 기획자 / NH콕뱅크",
      studentCode: 201611600,
      jobName: "productManager",
    },
    {
      name: "최규민",
      //2021StudentImages/201610309_main.jpg
      mainImage: "main.jpg",
      mainDescription: "최규민2 / 기획자 / NH콕뱅크",
      studentCode: 201611600,
      jobName: "productManager",
    },
  ];
  // grid__item
  // grid__item_desktop-4
  // grid__item_tablet-6
  // grid__item_mobile-12
  // grid__item_fluid-3
  // grid__item_fluid-3-fancy
  // js-grid__item
  const classList = [
    "grid__item",
    "grid__item_desktop-4",
    "grid__item_tablet-6",
    "grid__item_mobile-12",
    "grid__item_fluid-3",
    "grid__item_fluid-3-fancy",
    "js-grid__item",
  ];
  mainGridDummyData.forEach((val, idx) => {
    const duummyDiv = document.createElement("div");
    // console.log(classList.join().replace(",", "  "));
    duummyDiv.setAttribute(
      "class",
      `category-${val.jobName} ${classList.join().replaceAll(",", " ")}`
    );
    duummyDiv.innerHTML = createDivTabBox(val);
    document.getElementById("mainTabCategory").appendChild(duummyDiv);
  });
  const innerData = `<div class="section-masthead__wrapper-button mt-medium">
        <a
          class="button button_bordered button_white"
          data-hover="MORE"
          href="javascript:;"
          target="_self"
        >
          <span class="button__label-hover">MORE</span>
        </a>
      </div>`;
  const moreButtonDiv = document.createElement("div");
  moreButtonDiv.setAttribute("class", "moreBtn w-100");
  moreButtonDiv.innerHTML = innerData;
  document.getElementById("mainTabCategory").appendChild(moreButtonDiv);
})();

// var a = [1, 2];
// var b = ["aaa", "bb"];
// b.reduce((prev, cur, indx) => {
//   const x = {};
//   x["aa"] = b[indx];
//   x["tt"] = a[indx];
//   prev.push(x);
//   return prev;
// }, []);
//  const mainGridDummyData = [
//    {
//      name: "최규민",
//      //2021StudentImages/201610309_main.jpg
//      mainImage: "main.jpg",
//      mainDescription: "최규민 / 기획자 / NH콕뱅크",
//      studentCode: 201611600,
//      jobName: "productManager",
//    },
//    {
//      name: "조현기",
//      //2021StudentImages/201610309_main.jpg
//      mainImage: "main.jpg",
//      mainDescription: "조현기 / 기획자 / NH콕뱅크",
//      studentCode: 201611563,
//      jobName: "productManager",
//    },
//    {
//      name: "전강민",
//      //2021StudentImages/201610309_main.jpg
//      mainImage: "main.jpg",
//      mainDescription: "전강민 / 기획자 / NH콕뱅크",
//      studentCode: 201611600,
//      jobName: "productManager",
//    },
//    {
//      name: "김서희",
//      //2021StudentImages/201610309_main.jpg
//      mainImage: "main.jpg",
//      mainDescription: "김서희 / 디자이너 / NH콕뱅크",
//      studentCode: 201810225,
//      jobName: "design",
//    },
//    {
//      name: "박은별",
//      //2021StudentImages/201610309_main.jpg
//      mainImage: "main.jpg",
//      mainDescription: "박은별 / 디자이너 / NH콕뱅크",
//      studentCode: 201810600,
//      jobName: "design",
//    },
//    {
//      name: "김유민",
//      //2021StudentImages/201610309_main.jpg
//      mainImage: "main.jpg",
//      mainDescription: "김유민 / 개발자 / NH콕뱅크",
//      studentCode: 201610309,
//      jobName: "developer",
//    },
//    {
//      name: "홍민경",
//      //2021StudentImages/201610309_main.jpg
//      mainImage: "main.jpg",
//      mainDescription: "홍민경 / 개발자 / NH콕뱅크",
//      studentCode: 201811745,
//      jobName: "developer",
//    },
//    {
//      name: "영상 크리에이터",
//      //2021StudentImages/201610309_main.jpg
//      mainImage: "main.jpg",
//      mainDescription: "영상 크리에이터 / 기획자 / NH콕뱅크",
//      studentCode: 201510476,
//      jobName: "video",
//    },
//  ];
