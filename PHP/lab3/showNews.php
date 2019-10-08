<?php
   // headlines indexed news. Create your own apiKey on https://newsapi.org and replace it with mine
   $string = file_get_contents('https://newsapi.org/v2/top-headlines?country=us&apiKey=1ab37f1caed24bf6b550bb8f4c8f075b');
 // show news from United States only.  You can change country=GB for Great Britain or others
   $data = json_decode($string, true);   
   foreach($data['articles'] as $article)
   {
          echo $article['title']; // display news title
          $url = $article['url'];
          echo "<br> <a href=$url>$url</a>"; // display news url
          if($article['urlToImage']){
          $img = $article['urlToImage'];
           echo " <br> <img src=\"$img\" width=\"300\" height=\"200\">";  // display news image
          }
      echo"<hr />";
   }
?>
