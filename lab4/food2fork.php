<?php
//food2fork api  - Register to get your API-Key
//$searchStr = "shredded chicken";
$searchStr = "pepper";
$string = file_get_contents("http://food2fork.com/api/search?key=309679b62c48c02777f976f2b41b76a3&q=$searchStr");

$data = json_decode($string, true);

// displays array values one by one

 $publisher=  $data['recipes'][0]['publisher'];
 $f2fURL= $data['recipes'][0]['f2f_url'];
 $title= $data['recipes'][0] ['title'];

//  echo "$publisher - $f2fURL - $title";exit;
 //print_r($data); // print array

 // print_r($data['recipes']);

 // Dsiplay all values in a loop
 echo "<table border=1>";
foreach($data['recipes'] as $recipe)
{
    echo"<tr> <td>"; 
	 foreach($recipe as $key=> $val){
       echo "$key => $val";


       if($key=="image_url"){
        echo "<br><img src=\"$val\" width=100 height=100> ";
       }
   }
   echo"</td></tr><hr />";
}
echo "</table>";

?>