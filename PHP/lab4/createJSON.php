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
$sql = "select * from users";
$response = array();
$data = array();
$result = $conn->query($sql);
              while($row = mysqli_fetch_array($result))  
                 {
                   $name= $row["name"];
	    	       $email=$row["email"] ;
	  	           $data[ ] = array('name'=> $name, 'email'=> $email);
                   }
$response['posts'] = $data;
$fp = fopen('results.json', 'w'); // create a file on hard disk in write mode
fwrite($fp, json_encode($response)); // write array data in json format
fclose($fp); 
?>
