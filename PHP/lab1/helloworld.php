

<?php

    echo  "<font size=+25>Hello World.</font> <br>";

// comment

/*
  also a comment
  multi line blyet
*/

$id=10;
$name="David";

echo $id . "<br>";
// echo "<br>";
echo $name. "<br><br><br>";


    // this function receives one parameter and displays it
    function showText($msg){
        echo $msg; // displays Hello World!
    }
	$msg="I am in a <font size=+7>php function!</font> <br>";
	showText($msg); // function call

// exit; to end it all



?>