<?php
       
   $name = $_POST['name']; // the variable name is the product name, we want to know its price
   $url = "http://localhost/webservices/lab5/api.php?name=".$name; 
  
   $client = curl_init($url); // CURL session starts
   curl_setopt($client,CURLOPT_RETURNTRANSFER,false);
   $response = curl_exec($client);
		
   curl_close($client); // CURL session closes
   $result = json_decode($response);
  
  // access json through data.php
   //echo "Price of $name = $". $result->data; // shows price
  
  //access json through dataDB.php
   print_r($result);
  //echo "<br/> Price = ".  $result->data->price;
  
  
  echo "<br/> Price = ".  $result->data->price;
  echo "<br/> Year = ".   $result->data->year;
  echo "<br/> Make = ".   $result->data->make;
  echo "<br/> Model = ".  $result->data->model;
 

?>
