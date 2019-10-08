<?php

//error_reporting(E_ALL);  // Turn on all errors, warnings and notices for easier debugging
error_reporting(0);  // Turn off all errors, warnings and notices for easier debugging

// API request variables
$endpoint = 'http://svcs.ebay.com/services/search/FindingService/v1';  // URL to call
$version = '1.0.0';  // API version supported by your application
$appid = 'DavidKor-BookSear-PRD-ddfb0df12-a850d4e8';  // Replace with your own AppID - Production API
$globalid = 'EBAY-US';  // Global ID of the eBay site you want to search (e.g., EBAY-DE)
$query = $_GET['query'];  // You may want to supply your own query
$safequery = urlencode($query);  // Make the query URL-friendly
// $maxprice = $_GET['maxprice'];

// Construct the findItemsByKeywords HTTP GET call
$apicall = "$endpoint?";
$apicall .= "OPERATION-NAME=findItemsByKeywords";
$apicall .= "&SERVICE-VERSION=$version";
$apicall .= "&SECURITY-APPNAME=$appid";
$apicall .= "&GLOBAL-ID=$globalid";
$apicall .= "&keywords=$safequery";
$apicall .= "&paginationInput.entriesPerPage=25";

// example valid api call
// https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsByKeywords&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=DavidKor-BookSear-PRD-ddfb0df12-a850d4e8&GLOBAL-ID=EBAY-US&keywords=bible&paginationInput.entriesPerPage=3

// Load the call and capture the document returned by eBay API
$resp = simplexml_load_file($apicall);

// convert to json because its better
$json = json_encode($resp);
$json = json_decode($json);

$towrite = array();
$entries = array();

foreach($json->searchResult->item as $item) {
  $title = $item->title;
  $price = $item->sellingStatus->currentPrice;

  echo "$title costs \$$price<br>";
  $entries[ ] = array('title' => $title, 'price' => $price);
  

}

$towrite['books'] = $entries;
$fp = fopen('ebayResult.json', 'w'); // create a file on hard disk in write mode
fwrite($fp, json_encode($towrite,true)); // write array data in json format
fclose($fp); 

?>