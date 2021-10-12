<?php
$connection =  mysqli_connect('localhost', 's201610309', 'test1234', 's201610309');

if ($connection -> connect_errno) {
    echo  '{"Operation":"database connection error"}';
    return;
}
echo "입력 데이터 <br>";
$id = $_POST["id"];
$name = $_POST["name"];
$email = $_POST["email"];

$query = "insert into member(id,name,email) values('.$id.','.$name.','.$email.')";
$result = mysqli_query($connection, $query);
if(!$result){
    echo "입력 오류 발생 <br>";
}
echo "입력 성공<br>";

echo "테이블에 모든 정보를 읽어 옵니다 <br>";
$query_select = "select * from member";
$selectQuery = mysqli_query($connection, $query_select);
if($selectQuery){
    echo "<ul>";
    $num=1;
    while($row = mysqli_fetch_array($selectQuery))
    {
        echo "<li>";
        echo "$num . 아이디 : $row[id] 이름 :  $row[name] 이메일 : $row[email]</br>";
        echo "</li>";
        $num++;
    }
    echo "</ul>";

}
?>