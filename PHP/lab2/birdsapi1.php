<?php


// birdsapi1.php

$string = file_get_contents("birds.json");

// returns object, notice the parameter “true” is not used now
$items = json_decode($string, true); 

foreach($items->birds   as   $bird)
{
    echo $bird->name. " ". $bird->color. "<br/>"; // valid    
   
}


?>
