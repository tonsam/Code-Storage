<?php
$string = file_get_contents("nestedArrayDealer.json");
$items = json_decode($string, true);
echo $items['dealer'] . "<br>"; // value of first key
foreach($items['cars']  as  $cars) // fetch all arrays, including nested arrays
{
    $arraySize = count($cars['models']); // get size of each nested array
	
    for($i=0; $i<$arraySize; $i++) { 
          echo $cars['models'][$i] ."<br/>";  // display each model value one by one    
                                                                         //  from each nested array
     }		
      echo "<hr>"; // print a horizontal line
}
?>