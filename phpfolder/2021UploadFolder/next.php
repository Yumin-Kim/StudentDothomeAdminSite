<!-- 쿼리 스트링을 받아 db조회 후 이미지 , 글 , 이름 받아옴  -->
<?php
$connection =  mysqli_connect('localhost', 'root', 'multi2021', 'studentDothome');
if ($connection -> connect_errno) {
    return;
}
$student_code=(int)$_GET["studentcode"];
$query="select * from student_portfolio where student_code='$student_code'";
$selectQuery = mysqli_query($connection, $query);
$count = mysqli_num_rows($selectQuery);
if($count){
    $row = mysqli_fetch_array($selectQuery);
    $currentStudentCode=$row['student_code'];
    $profileFormat=$row['profile_image_format'];
    $brochureFormat = $row['brochure_image_format'];    
    $youtubeLink = $row['youtube_link'];
    $description=$row['description']; 
    $name = $row['name'];   
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>

    <style type="text/css">
.video-container { margin: 0;padding-bottom: 75%; max-width: 100%; height: 0; position: relative;overflow: hidden;} 
.video-container iframe, 
.video-container object, 
.video-container embed { margin: 0;padding: 0; width: 100%; height: 95%;position: absolute; top: 0;left: 0; }
</style>
    
</head>
<body>
    <div>
        <h2 id="student_name"></h2>
        <p>개인 글귀</p>
        <pre id="student_description"></pre>
    </div>
    <h3>프로필 이미지</h3>
    <img src="./studentImages/<?php echo $currentStudentCode ?>_profile<?php echo $profileFormat?>">
    <h3>브로슈어 이미지</h3>
    <img src="./studentImages/<?php echo $currentStudentCode ?>_brochure<?php echo $brochureFormat?>">
    <!-- iframe을 활용하여 유튜브 단 유튜브 링크는  -->
    <!-- <div class="video-container"> -->
    <h3>개인 영상 </h3>
    <iframe width="1280" height="720" src="<?php echo $youtubeLink?>">
<!-- </div> -->
</iframe>

<script type="text/javascript">
    const studentDescription = <?= json_encode($description) ?>;
    document.getElementById("student_name").innerHTML = <?= json_encode($name) ?>;
    document.getElementById("student_description").innerHTML= studentDescription;
</script>
</body>
</html>