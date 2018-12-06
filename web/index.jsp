<%@ page import="com.skov.ImdbKino" %>
<%@ page import="com.skov.ImdbKinoAalborgToday" %><%--
  Created by IntelliJ IDEA.
  User: ALSK
  Date: 06-12-2018
  Time: 12:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Imdb-Kino</title>
  </head>
  <body>
  <h1>Welcome to IMDB-KINO</h1>
  <%=new ImdbKino().execute()%>
  <h2>Og for Aalborg i dag:</h2>

  <%=new ImdbKinoAalborgToday().execute()%>
  </body>
</html>
