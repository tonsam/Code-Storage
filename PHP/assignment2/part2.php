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
$sql = "select * from vegetables";
$response = array();
$data = array();
$result = $conn->query($sql);
              while($row = mysqli_fetch_array($result))  
                 {
                   $name= $row["name"];
                   $type=$row["type"] ;
                   $price=$row["price"] ;
	  	           $data[ ] = array('name'=> $name, 'type'=> $type, 'price'=> $price);
                   }
$response['posts'] = $data;
$fp = fopen('vegetables.json', 'w'); // create a file on hard disk in write mode
fwrite($fp, json_encode($response)); // write array data in json format
fclose($fp); 
?>
