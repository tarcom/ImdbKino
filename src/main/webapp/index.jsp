<%@ page import="com.skov.ImdbKino" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Imdb-Kino</title>
  </head>
  <body>

  <h1>Velkommen til IMDB-KINO</h1>



  <h2>Og for alle kino.dk film:</h2>
  <%=new ImdbKino().execute()%>

  <br><br>
  (C)2018-2025 Allan Skov allan@aogj.com
  <br><br>

  </body>
</html>
