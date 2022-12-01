package com.jacaranda.servlet;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;

import com.jacaranda.control.ConnectionDBException;
import com.jacaranda.control.ElementControl;
import com.jacaranda.control.UserControl;
import com.jacaranda.control.UserControlException;
import com.jacaranda.model.Element;
import com.jacaranda.model.ShoppingCart;
import com.jacaranda.model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(description = "Servlet for login", urlPatterns = { "/LoginServlet" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String HTML_SUCCESS1 = "<!DOCTYPE html>\r\n" + "<html lang=\"en\">\r\n" + "<head>\r\n"
			+ "    <meta charset=\"UTF-8\">\r\n" + "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
			+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
			+ "    <title>Confirmaci&oacute;n de acceso</title>\r\n"
			+ "    <link rel=\"stylesheet\" type=\"text/css\" href=\"CSS/productsList.css\">\r\n" + "</head>\r\n"
			+ "<body>\r\n" + "\r\n" + "    <div class=\"site_wrap\">\r\n" + "        <div class=\"title\">\r\n"
			+ "        <h1>Comestibles Correa</h1>\r\n" + "        </div>\r\n" + "        <div class=\"session\">";

	private static final String HTML_SUCCESS2 = "</div>\r\n" 
			+ "        <div class= \"footer\">\r\n" + "        <p>&copy; Comestibles Correa</p>\r\n"
			+ "        </div>\r\n" + "    </div>\r\n" + "</body>\r\n" + "</html>";

	private static final String HTML_ERROR1 = "<!DOCTYPE html>\r\n" + "<html lang=\"en\">\r\n" + "<head>\r\n"
			+ "    <meta charset=\"UTF-8\">\r\n" + "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
			+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
			+ "    <title>Confirmación de acceso</title>\r\n"
			+ "    <link rel=\"stylesheet\" type=\"text/css\" href=\"CSS/error.css\">\r\n" + "</head>\r\n"
			+ "<body>\r\n" + "\r\n" + "    <div class=\"site_wrap\">\r\n" + "        <div class=\"title\">\r\n"
			+ "        <h1>Comestibles Correa</h1>\r\n" + "        </div>\r\n" + "        <div class=\"error\">";

	private static final String HTML_ERROR2 = "</div>\r\n" + "        <div class=\"back\">\r\n"
			+ "            <a href=\"index.jsp\" class=\"button\">Volver al inicio</a>\r\n" + "        </div>\r\n"
			+ "        <div class= \"footer\">\r\n" + "        <p>&copy; Comestibles Correa</p>\r\n"
			+ "        </div>\r\n" + "    </div>\r\n" + "</body>\r\n" + "</html>";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();

	}
	
	
	//to be able to send the form 
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//If session is opened
		HttpSession se = request.getSession();
		User loggedUser = (User) se.getAttribute("user");
		if (loggedUser != null) {
			
			try {
				//show list of products
				listElements(response, loggedUser);
			} catch (ConnectionDBException | IOException e) {
				
				response.getWriter()
				.append(HTML_ERROR1 + "<h3>" + e.getMessage() +"</h3>" + HTML_ERROR2);
			} 
			
		} else {
			//It gets the parameters(userName and password) sent on the form
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");

			try {
				//if the parameters aren't empty the password is encrypted
				if (password != null && !password.isBlank() && userName != null && !userName.isBlank()) {
					String encryptedPassword = DigestUtils.md5Hex(password);
					response.setContentType("text/html");
					
					//It check if the user exist
					User user = UserControl.checkUser(userName, encryptedPassword);
					
					//If user exist then creates the session and shows the list of products depending on the role
					if (user != null) {
						HttpSession session = request.getSession(true);
						session.setAttribute("user", user);
						session.setAttribute("shoppingCart", new ShoppingCart());
						//show list of products
						listElements(response, user);
						
					} else {
						response.getWriter()
								.append(HTML_ERROR1 + "<h3>El usuario o la contrase&ntilde;a no son válidos.</h3>" + HTML_ERROR2);
					}

				} else {
					response.getWriter()
					.append(HTML_ERROR1 + "<h3>Ni el usuario ni la contrase&ntilde;a pueden estar vac&iacute;os.</h3>" + HTML_ERROR2);
				}

			} catch (UserControlException | ConnectionDBException e) {

				response.getWriter()
						.append(HTML_ERROR1 + "<h3>" + e.getMessage() +"</h3>" + HTML_ERROR2);
			}

		}

		}

	private void listElements(HttpServletResponse response, User user) throws ConnectionDBException, IOException {
		List<Element> elements = ElementControl.getElements(); 
		String htmlResult = "<table><tr><td>Nombre de producto</td><td>Descripci&oacute;n</td><td>Precio</td><td>Stock</td><td>Categor&iacute;a</td><td>Elementos a comprar</td></tr>";
		
		Iterator<Element> iterator = elements.iterator();
		
		while(iterator.hasNext()) {
			Element iElement= iterator.next();
			
			htmlResult += "<tr id="+ iElement.getEleId() +">"
					+ "<td>"+ iElement.getName() +"</td>"
					+ "<td>"+ iElement.getDescription()+"</td>"
					+ "<td>"+ iElement.getPrice()+"</td>"
					+ "<td>"+ iElement.getStock()+"</td>"
					+ "<td>"+ iElement.getCategory().getName()+"</td>";
			if(iElement.getStock()>0) {
				htmlResult+= "<td><form action=\"addingItem\" method=\"post\">"
								+ "Cantidad: <input type=\"number\" name=\"itemQuantity\" id=\"itemQuantity\" step=\"1\" min=\"1\" max=\""
								+ iElement.getStock()
								+ "\" required>"
								+ "<input id=\"elementId\" name=\"elementId\" type=\"hidden\" value=\""
								+ iElement.getEleId()
								+ "\">"
								+ "<button type=\"submit\">A&ntilde;adir</button>"
								+ "</form></td>"
								+ "</tr>";
			}else {
				htmlResult+= "<td>"
						+ "Fuera de stock"
						+ "</td>"
						+ "</tr>";
			}
				
		}
		
		response.getWriter().append(HTML_SUCCESS1 + "<h1>Sesi&oacute;n: " + user.getName()
				+ "</h1>"
				+ "</div> <div class=\"addbut\">" 
				+ getAddButton(user.isAdmin()) //it only shows this option if the user has an admin role
				+ "</div><div class=\"delbut\">"
				+ "<a href=\"index.jsp\" class=\"button close\">Cerrar sesi&oacute;n</a>"
				+ "</div><div class=\"shopbut\">"
				+ "<a href=\"Shopping\" class=\"button shop\">Comprar</a>"
				+ "</div>"
				+ "<div class=\"historybut\">"
				+ "<a href=\"Historial\" class=\"button hist\">Historial</a>"
				+ "</div>"
				+ "<div class = table>"
				+ htmlResult 
				+ "</table>"
				+ HTML_SUCCESS2);
	}
		
	private String getAddButton(boolean admin) {
		String result = "";
		if (admin) {
			result = "<a href='addProduct.jsp' class=\"button add\">A&ntilde;adir producto</a>";
		}
		
		return result;
	}

}
