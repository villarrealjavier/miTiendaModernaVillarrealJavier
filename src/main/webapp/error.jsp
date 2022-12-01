<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error</title>
    <link rel="stylesheet" type="text/css" href="CSS/error.css">
</head>
<body>
<%session.invalidate(); %>

    <div class="site_wrap">
        <div class="title">
        <h1>Comestibles Correa</h1>
        </div>
        <div class="error">
         <p>No te has autenticado</p>
        </div>
        <div class="back">
            <a href="index.jsp">Volver</a>
        </div>
        <div class= "footer">
        <p>&copy; Comestibles Correa</p>
        </div>
    </div>
</body>
</html>
