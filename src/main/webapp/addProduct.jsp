<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.jacaranda.control.CategoryControl"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.jacaranda.model.Category"%>
<%@page import="com.jacaranda.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Añadir Producto</title>
<link rel="stylesheet" type="text/css" href="CSS/addProduct.css">
</head>
<body>

<%
	HttpSession se = request.getSession();
	User loggedUser = (User) se.getAttribute("user");
	if(loggedUser != null && loggedUser.isAdmin()){%>

<div class="site_wrap">
	<div class="title">
	<h1>Comestibles Correa - Añadir nuevo Producto</h1>
	</div>
	<div class="form">
		<form action="AddProductServlet" method="post">
		
			<label for="name">Nombre del producto: </label><br>
			<input type="text" id="name" name="name" minlength="2" maxlength="100" required><br>
			
			<label for="description">Descripción: </label><br>
			<input type ="text" id="description" name="description" minlength="5" maxlength="200" required><br>

			<label for="price">Precio: </label><br>
			<input type="number" id="price" name="price" step="0.01" required><br>
			
			<label for="stock">Stock: </label><br> 
			<input type="number" id="stock" name="stock" step="1" required><br>
			
			<label for="category">Categoría: </label><br>
			<select name="category" id="category" required>			
			<%
				List<Category> categories = CategoryControl.getCategories();
				
				Iterator<Category> ite = categories.iterator();
				
				while(ite.hasNext()){
					Category iCat = ite.next(); %>
					
					<option value="<%=iCat.getCatId() %>"><%=iCat.getName() %></option>
					
					
				<% }%>
			
			</select><br>
			<button type="submit" id="addProduct">Añadir</button>
		</form>
	</div>
	<div class="back">
         <a href="LoginServlet" class="button">Volver</a>
     </div>
	<div class= "footer">
	<p>&copy; Comestibles Correa</p>
	</div>
</div>
		
		
		
	<%}else{%>
		<jsp:forward page="error.jsp"></jsp:forward>
	<%}
	%>
	


</body>
</html>