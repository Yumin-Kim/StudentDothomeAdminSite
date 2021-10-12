<!-- 학번 별 src 제공 http://media.seowon.ac.kr/2021/next.php?studentCode=123123123 -->
<!-- db조회 -->
<?php
$connection =  mysqli_connect('localhost', 'root', 'multi2021', 'studentDothome');
if ($connection -> connect_errno) {
    return;
}
$query_select = "select * from student_portfolio";
$selectQuery = mysqli_query($connection, $query_select);
$name_arr=array();
$student_code_arr=array();
if($selectQuery){
    $num=1;
    while($row = mysqli_fetch_array($selectQuery))
    {
        array_push($name_arr,$row[name]);
        array_push($student_code_arr,$row[student_code]);
        $num++;
    }
}
?>
<script>
    const nameArray = <?= json_encode($name_arr) ?>;
    const studentCodeArray = <?= json_encode($student_code_arr) ?>;
    console.log(nameArray)
    console.log(studentCodeArray);
</script>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <script src="./build.js"></script>
    <title>Document</title>
  </head>
  <body>
    <div>학생 정보</div>
    <h2>학생 리스트</h2>
    <ul id="studentList"></ul>
    <a href=""></a>
    <script type="text/javascript"></script>
  </body>
</html>
