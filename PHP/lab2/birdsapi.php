<?php

// birdsapi.php
// xampp/htdocs/webservices

$string = file_get_contents("birds.json");// URL to access JSON file

$items = json_decode($string, true); // returns array

foreach($items['birds']  as   $value)
{
    echo $value['name']." ". $value['color']. "<br/>";
}


?>
