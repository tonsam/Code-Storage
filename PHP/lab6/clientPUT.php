<?php

//$get_string = $_SERVER['QUERY_STRING'];
//$data=parse_str($get_string, $get_array);

 $json_data = ["model" => "Versa", "price" => "10000"];

 $apiURL = "http://localhost/webservices/lab6/serverPUT.php";
 $curl = curl_init($apiURL);
 curl_setopt($curl, CURLOPT_RETURNTRANSFER, true); // GET method
 curl_setopt($curl, CURLOPT_CUSTOMREQUEST, "PUT");
 curl_setopt($curl, CURLOPT_POSTFIELDS,http_build_query($json_data));

  //echo curl_exec($curl); //exit; //debugging


  $response = curl_exec($curl);
  curl_close($curl);

 $result = json_decode($response);

 if($result->status==200){
    echo"$result->status. Data successfully Updated in the Server DB";
 }else{

    echo $result->status_message;
 }

?>