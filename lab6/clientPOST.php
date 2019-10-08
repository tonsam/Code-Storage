<?php

 $apiURL = "http://localhost/webservices/lab6/serverPOST.php";
 $curl = curl_init($apiURL);
 curl_setopt($curl, CURLOPT_RETURNTRANSFER, true); // GET method
 curl_setopt($curl, CURLOPT_POSTFIELDS, "price=63300&make='Nissan2019'&model='Versa'&year='201533'");  // POST method
 $response = curl_exec($curl);
 curl_close($curl);

 $result = json_decode($response);

 if($result->status==201){
    echo"$result->status. Data successfully Posted in the Server DB";
 }else{

    echo $result->status_message;
 }
?>