
<html>
<head> <title>Best Book Search Ever</title> <meta charset="utf-8"></head>

<body>
<h2>Wonderful Great Book Search</h2>

  <form action="api.php" method="POST">
      Title of Book to Search: <input type="text" name="name" >
      <br>
      Maximum Price:
      <select name="maxprice">
        <option value=9999 selected>Select</option>
        <option value=5>$5</option>
        <option value=10>$10</option>
        <option value=50>$50</option>
      </select>
      <button type="submit" name="submit" >Search</button>
  </form>

</body>

</html>
