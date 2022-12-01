package com.jacaranda.servlet;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jacaranda.control.ConnectionDBException;
import com.jacaranda.control.ElementControl;
import com.jacaranda.model.CartItem;
import com.jacaranda.model.Element;
import com.jacaranda.model.ShoppingCart;
import com.jacaranda.model.User;

/**
 * Servlet implementation class Shopping
 */
@WebServlet("/Shopping")
public class ShoppingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String HTML_SUCCESS1 = "<!DOCTYPE html>\r\n" + "<html lang=\"en\">\r\n" + "<head>\r\n"
			+ "    <meta charset=\"UTF-8\">\r\n" + "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
			+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
			+ "    <title>Carrito de la compra</title>\r\n"
			+ "    <link rel=\"stylesheet\" type=\"text/css\" href=\"CSS/shopping.css\">\r\n" + "</head>\r\n"
			+ "<body>\r\n" + "\r\n" + "    <div class=\"site_wrap\">\r\n" + "        <div class=\"title\">\r\n"
			+ "        <h1>Comestibles Correa</h1>\r\n" + "        </div>\r\n" + "        <div class=\"session\">";

	private static final String HTML_SUCCESS2 = "</div>\r\n" 
			+ "        <div class= \"footer\">\r\n" + "        <p>&copy; Comestibles Correa</p>\r\n"
			+ "        </div>\r\n" + "    </div>\r\n" + "</body>\r\n" + "</html>";
	
	private static final String HTML_ERROR1 = "<!DOCTYPE html>\r\n" + "<html lang=\"en\">\r\n" + "<head>\r\n"
			+ "    <meta charset=\"UTF-8\">\r\n" + "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
			+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
			+ "    <title>Error: Carrito de la compra </title>\r\n"
			+ "    <link rel=\"stylesheet\" type=\"text/css\" href=\"CSS/error.css\">\r\n" + "</head>\r\n"
			+ "<body>\r\n" + "\r\n" + "    <div class=\"site_wrap\">\r\n" + "        <div class=\"title\">\r\n"
			+ "        <h1>Comestibles Correa</h1>\r\n" + "        </div>\r\n" + "        <div class=\"error\">";
	
	private static final String HTML_ERROR2 = "</div>\r\n" + "        <div class=\"back\">\r\n"
			+ "            <a href=\"LoginServlet\" class=\"button\">Volver</a>\r\n" + "        </div>\r\n"
			+ "        <div class= \"footer\">\r\n" + "        <p>&copy; Comestibles Correa</p>\r\n"
			+ "        </div>\r\n" + "    </div>\r\n" + "</body>\r\n" + "</html>";
	
	private static final String HTML_ERROR3 = "</div>\r\n" + "        <div class=\"back\">\r\n"
			+ "            <a href=\"index.jsp\" class=\"button\">Volver</a>\r\n" + "        </div>\r\n"
			+ "        <div class= \"footer\">\r\n" + "        <p>&copy; Comestibles Correa</p>\r\n"
			+ "        </div>\r\n" + "    </div>\r\n" + "</body>\r\n" + "</html>";

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShoppingServlet() {
        super();
    }
    

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//If user is logged in correctly get user and shopping cart
				HttpSession se = request.getSession();
				User loggedUser = (User) se.getAttribute("user");
				ShoppingCart cart = (ShoppingCart) se.getAttribute("shoppingCart");
				if (loggedUser != null) {
					
					if(cart.getRequestedItems().size() > 0) {
						// Show list of products added - update an delete option (optional) 
						try {
							listCartItems(response, loggedUser, cart);
						} catch (ConnectionDBException | IOException e) {
							response.getWriter()
							.append(HTML_ERROR1 + "<h3>" + e.getMessage() +"</h3>" + HTML_ERROR2);
						}
						
						System.out.println(cart);
					}else {
						//Error - There are no items added
						response.getWriter().append(HTML_ERROR1 + "No se han a&ntilde;adido productos." + HTML_ERROR2);
						
					}
					

				} else {
					response.getWriter().append(HTML_ERROR1 + "No te has autenticado." + HTML_ERROR3);
				}
	}

	//Iterates the shopping cart and prints the list of products added to the cart
	private void listCartItems(HttpServletResponse response, User user, ShoppingCart cart) throws ConnectionDBException, IOException {
		String htmlResult = "<table><tr><td>Nombre de producto</td><td>Cantidad</td><td>Precio</td><td>Total</td><td colspan=\"2\">Acciones</td></tr>";
		Iterator<CartItem> iterator = cart.getRequestedItems().iterator();
		double priceToPay = 0;
		
		while(iterator.hasNext()) {
			CartItem iItem = iterator.next();
			Element iElement = ElementControl.getElement(iItem.getElementId());
			double totalPerProduct = iItem.getPrice() * iItem.getQuantity();
			priceToPay += totalPerProduct;
			
			htmlResult += "<tr id="+ iItem.getElementId() +">"
					+ "<td>"+ iElement.getName() +"</td>"
					+ "<td>"+ iItem.getQuantity() +"</td>"
					+ "<td>"+ iItem.getPrice() +"</td>"
					+ "<td>"+ totalPerProduct +"</td>"
					
					+ "<td><form action=\"UpdateCartItemServlet\" method=\"post\">"
					+ "<input type=\"number\" name=\"itemQuantity\" id=\"itemQuantity\" step=\"1\" min=\"1\" max=\""
					+ iElement.getStock()
					+ "\" required>"
					+ "<input name=\"elementId\" type=\"hidden\" value=\"" + iItem.getElementId() +"\">"
					+ "<button type=\"submit\">Modificar cantidad</button>"
					+ "</form></td>"
					
					+ "<td><form action=\"DeleteCartItemServlet\" method=\"post\">"
					+ "<input name=\"elementId\" type=\"hidden\" value=\"" + iItem.getElementId() +"\">"
					+ "<button type=\"submit\">Eliminar</button>"
					+ "</form></td>"
					+ "</tr>";
		}
		
		htmlResult += "<tr>"
				+ "<td>Total a Pagar</td>"
				+ "<td>"+ priceToPay +"</td>"
				+ "</tr>";
		response.getWriter().append(HTML_SUCCESS1 + "<h1>Sesi&oacute;n: " + user.getName()
		+ "</h1>"
		+ "</div><div class=\"delbut\">"
		+ "<a href=\"index.jsp\" class=\"button close\">Cerrar sesi&oacute;n</a>"
		+ "</div>"
		+ "<div class = table>"
		+ htmlResult 
		+ "</table>"
		+ "</div><div class=\"pay\">"
		+ "<a href=\"ContinuePayment\" class=\"button\">Pagar</a>"
		+ "</div><div class=\"cancel\">"
		+ "<a href=\"LoginServlet\" class=\"button\">Cancelar</a>"
		+ "</div>"
		+ HTML_SUCCESS2);
	
	}
	
	
	
	
}
