<?php
// Producing and returning data for API Consumer

function getPrice($name) // return price of product
{
    $products = array();
    $products = [
        "phone"=>100,
        "laptop"=>1000,
        "book"=>5
    ];

    foreach($products as $product=>$price)
    {
        if($product==$name)
        {
            return $price;
            break;
        }
    }
}
?>
