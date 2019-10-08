<?php

//error_reporting(E_ALL);  // Turn on all errors, warnings and notices for easier debugging
error_reporting(0);  // Turn off all errors, warnings and notices for easier debugging

// API request variables
$endpoint = 'https://www.googleapis.com/books/v1/volumes?q=';  // URL to call
$query = $_GET['query'];  // You may want to supply your own query
$apikey = 'AIzaSyDY2XGzTjoAUKsIRDS25qUWXnkObI5yat4';
$safequery = urlencode($query);  // Make the query URL-friendly

// Construct the findItemsByKeywords HTTP GET call
$apicall = "$endpoint";
$apicall .= "$safequery";
$apicall .= "&filter=paid-ebooks"; // only books with a price (are for sale)
$apicall .= "&maxResults=25";
$apicall .= "&key=$apikey";

// example valid api call
// https://www.googleapis.com/books/v1/volumes?q=flowers+inauthor:keyes&key=AIzaSyDY2XGzTjoAUKsIRDS25qUWXnkObI5yat4

// Load the call and capture the document returned by eBay API
$resp = file_get_contents($apicall);
$json = json_decode($resp);

$towrite = '';
$entries = array();

foreach($json->items as $item) {
  $title = $item->volumeInfo->title;
  $price = $item->saleInfo->listPrice->amount;

  echo "$title costs \$$price<br>";
  $entries[ ] = array('title' => $title, 'price' => $price);

}

$towrite = array();
$towrite['books'] = $entries;

// write a new json file here
$fp = fopen('googleResult.json', 'w'); // create a file on hard disk in write mode
fwrite($fp, json_encode($towrite,true)); // write array data in json format
fclose($fp); 

?>
