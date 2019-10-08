<?php

// catsapi.php


$string = file_get_contents("cats.json"); // URL to access JSON file

$items = json_decode($string, true); // convert string to array


   foreach($items['cats']   as   $value) // loop
    {
    		echo "$value <br/>";
   }
?>
