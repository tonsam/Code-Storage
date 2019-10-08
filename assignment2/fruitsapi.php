<?php
$string = file_get_contents("fruits.json");
$items = json_decode($string, true);
foreach($items["fruits"]  as  $fruits) // fetch all arrays, including nested arrays
{
    echo $fruits["type"] . "<br>";
    $arraySize = count($fruits["subtypes"]); // get size of each nested array
	
    for($i=0; $i<$arraySize; $i++) { 
          echo $fruits["subtypes"][$i]["name"] . " ";
          echo $fruits["subtypes"][$i]["price"] . "<br/>";  // display each model value one by one    
                                                                         //  from each nested array
     }		
      echo "<hr>"; // print a horizontal line
}
?>