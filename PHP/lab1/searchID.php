<?php
$servername = "localhost";
 $dbname = "webservices";
$username = "root";
$password = "";
// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 
$ID = $_REQUEST['ID']; // or you can use this $email = $_POST['email'];
$sql = "SELECT ID, name, email FROM users where ID= '$ID' ";
//echo $sql;exit; // debugging 
$result = $conn->query($sql);
if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
         $ID = $row["ID"];
         $displayName = $row["name"];
         $email = $row["email"];
          echo " ID &nbsp;&nbsp; Name &nbsp;&nbsp; Email <br>";
          echo " $ID &nbsp;&nbsp; $displayName &nbsp;&nbsp; $email<br>";
    }
} else {
    echo "0 results found";
}
?>