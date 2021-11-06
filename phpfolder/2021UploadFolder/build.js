window.onload = () => {
  const ulTag = document.getElementById("studentList");
  nameArray.forEach((element, index) => {
    const liTag = document.createElement("li");
    const aTag = document.createElement("a");
    aTag.innerHTML = element;
    aTag.setAttribute(
      "href",
      `./next.php?studentcode=${studentCodeArray[index]}`
    );
    liTag.appendChild(aTag);
    ulTag.appendChild(liTag);
  });
};
