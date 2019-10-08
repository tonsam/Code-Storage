<?php
header("Content-Type:application/json");
// require "data.php"; // this is the file we created above

require "dataDB.php"; // this is the file we created above

if(!empty($_GET['name']))
{
    $name=$_GET['name'];
    $price = getPrice($name); // search $name of product as a method argument    
    if(empty($price))  {
        response(404,"Product Not Found",NULL);
    } else  {
        response(200,"Product Found",$price);
    }   
}
else{
    response(400,"Invalid Request",NULL);
}


function response($status, $status_message, $data){
    header("HTTP/1.1 ".$status);
    
    $response['status']=$status;
    $response['status_message']=$status_message;
    $response['data']=$data;    
    $json_response = json_encode($response);
    echo $json_response;
}
?>