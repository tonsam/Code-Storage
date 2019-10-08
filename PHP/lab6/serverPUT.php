<?php


if($_SERVER['REQUEST_METHOD'] == "PUT") {
    $data = [];
    $incoming = file_get_contents("php://input");
    parse_str($incoming, $data);

    $model = filter_var($data["model"]);
    $price = filter_var($data["price"]);

    //Data access
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

    
    $sql = "update cars set price = $price WHERE model='$model'";
 
    $result = $conn->query($sql);

    if($result)// successfully inserted
    {
      response(200,"Product updated",$result);
    }else{
      response(400,"Product not updated",$result);
    }

} else {
    response(400,"Bad Request",$result);
}


function response($status,$status_message,$data)
{
    header("HTTP/1.1 ".$status);
    
    $response['status']=$status;
    $response['status_message']=$status_message;
    $response['data']=$data;
    
    $json_response = json_encode($response);
    echo $json_response;
}

?>