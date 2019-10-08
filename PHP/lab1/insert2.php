<?php
$servername = "localhost";
$dbname = "webservices";
$username = "root";
// default username to connect to DB is root
$password = "";
// default password to connect to DB is empty, or you can also use root as password also. I am using empty password in my PC.
// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
// Capture data sent from index.php
$name = $_GET['name'];
// get data sent from previous html form on index.php
$price = $_GET['price'];
// get data sent from previous html form on index.php
$stock = $_GET['stock'];
// get data sent from previous html form on index.php
$sql = "INSERT INTO products (Product, Price, Stock)
VALUES ('$name', '$price', '$stock')";
// notice single quotes around variables because data is string.
// For integer or numbers we will not use single quotes.
if ($conn->query($sql) === TRUE) {
    header("Location:main2.php?name=$name&email=$price&email=$stock");
} else {
    echo "Error: record couldn’t be inserted";
}
$conn->close(); // close DB connection
?>