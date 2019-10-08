<?php

// check if name was pulled correctly
if (!empty($_POST['name'])) {
    $query = $_POST['name'];
}
else {
    echo 'Name not set, setting to Harry Potter and the Chamber of Secrets<br>';
    $query = "Harry Potter and the Chamber of Secrets";  
}

// check if maxprice was pulled correctly
if (!empty($_POST['maxprice'])) {
    $maxprice=$_POST['maxprice'];
}
else {
    echo 'Max price not set, setting to 9999<br>';
    $maxprice=99;   
}

$safequery = urlencode($query);

// call the first search engine
$url = 'http://localhost/webservices/assignment3/ebaySearch.php?query='.$safequery;

$client = curl_init(); // CURL session starts
curl_setopt($client,CURLOPT_URL, $url);
curl_setopt($client,CURLOPT_POST, true);
curl_setopt($client,CURLOPT_RETURNTRANSFER, true);
curl_exec($client);
curl_close($client); // CURL session closes

// call the first search engine
$url2 = 'http://localhost/webservices/assignment3/googleSearch.php?query='.$safequery;

$client2 = curl_init(); // CURL session starts
curl_setopt($client2,CURLOPT_URL, $url2);
curl_setopt($client2,CURLOPT_POST, true);
curl_setopt($client2,CURLOPT_RETURNTRANSFER, true);
curl_exec($client2);
curl_close($client2); // CURL session closes


echo "<hr>Ebay Search Results:<br><hr>";
// Get the results of the Ebay search from the json file

$string = file_get_contents("ebayResult.json");
$items = json_decode($string, true);
foreach($items['books']  as  $books) // fetch all arrays, including nested arrays
{
    
    if ($books['price'] <= $maxprice) {
        echo $books['title'] ." $";
        echo $books['price'] ."<br/>";
    }

}

echo "<hr>Google Search Results:<br><hr>";
// Get the results of the Google search from the json file

$string = file_get_contents("googleResult.json");
$items = json_decode($string, true);
foreach($items['books']  as  $books) // fetch all arrays, including nested arrays
{
    
    if ($books['price'] <= $maxprice) {
        echo $books['title'] ." $";
        echo $books['price'] ."<br/>";
    }

}

?>
