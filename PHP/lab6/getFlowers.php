<?php

$service_url = "http://localhost/webservices/lab6/flowers.json";
$curl = curl_init($service_url);
curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
$curl_response = curl_exec($curl);
curl_close($curl);
$decoded = json_decode($curl_response);

echo $decoded->Marigold;



?>