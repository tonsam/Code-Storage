<?php
if(isset($_POST['submit']))
{        
   $API_KEY = $_POST['API_KEY'];       
   $name = $_POST['name']; // the variable name is the product name, we want to know its price
   //$url = "http://localhost/webservices/api.php?name=".$name; 
   $url = "http://localhost/webservices/lab5/apiAPIKEY.php?name=".$name."&API_KEY=".$API_KEY;
  
   $client = curl_init($url); // CURL session starts
   curl_setopt($client,CURLOPT_RETURNTRANSFER,true);
   $response = curl_exec($client);
		
   curl_close($client); // CURL session closes
   $result = json_decode($response); 
  
  if($result->status==401){
	  echo "<br/> Invalid API KEY. Please Try Again.";
  }else{
  echo "<br/> Price = ".  $result->data->price;
  echo "<br/> Year = ".   $result->data->year;
  echo "<br/> Make = ".   $result->data->make;
  echo "<br/> Model = ".  $result->data->model;
  }
}
?>
