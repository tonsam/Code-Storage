<?php

$string = file_get_contents("products.xml");

$response = new SimpleXMLElement($string, true);

//echo $response->book; // displays price of 1 element

foreach($response as $key => $value)
{
    echo "$key = $value <br/>";
}

//print_r($response); // print array

?>