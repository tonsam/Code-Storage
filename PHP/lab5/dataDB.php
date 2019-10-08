<?php
// Retrieving data fron DB and returning it for API Consumer//
function getPrice($name)
{
    //Read data from DB table
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
    $sql = "SELECT price, make, model, year FROM cars where make ='$name' order by price asc limit 1"; // limit 1 means, we want to retrieve one record only.
    $result = $conn->query($sql);
    $info=mysqli_fetch_array($result,MYSQLI_ASSOC);
   
    echo $info;
    return $info;
}
?>
