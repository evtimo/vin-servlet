<%--
  Created by IntelliJ IDEA.
  User: evgeny
  Date: 09.02.18
  Time: 14:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>VinForm</title>
    <link rel="stylesheet" type="text/css" href="style.css"/>
    <style> ${requestScope.utilStyle} </style>
</head>
<body>
<div class="wrapper">

        <form action="vinform" method="post" name="checkForm">
            <div class="form">
            <input name="vinnumber" type="text" value="JHMCM56557C404453" placeholder="Enter your VIN here"/> </br>
            <input type="submit" value="CHECK" value="Send"/>
            </div>
        </form>

    <div class="result">

        ${requestScope.utilOutput}

    </div>
</div>
</body>
</html>
