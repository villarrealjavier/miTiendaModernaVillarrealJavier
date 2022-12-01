<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Inicio de sesión</title>
<link rel="stylesheet" type="text/css" href="CSS/index.css">
</head>
<body>
<% session.invalidate();  %>
<div class="site_wrap">
	<div class="title">
	<h1>Comestibles Correa</h1>
	</div>
	<div class="image">
        <img src="IMAGES/cesta.png" alt="Shopping basket">
    </div>
	
	<div class="form">
		<form action="LoginServlet" method="post">
			
			<label for="userName">Usuario: </label><br>
			<input type="text" id="userName" name="userName" minlength="2" maxlength="20" required><br>
			<label for="password">Contraseña: </label><br>
			<input type ="password" id="password" name="password" minlength="5" maxlength="15" required><br>
			<button type="submit" id="login">Iniciar Sesión</button>
		</form>
	</div>
	<div class="signIn">
		<h3>¿Aún no estás registrado?</h3>
		<p>Regístrate <a href="signIn.jsp"> aquí</a></p>
	</div>
	<div class= "footer">
	<p>&copy; Comestibles Correa</p>
	</div>
</div>
</body>
</html>