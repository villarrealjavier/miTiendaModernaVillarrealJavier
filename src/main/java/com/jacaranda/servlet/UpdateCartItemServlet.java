package com.jacaranda.servlet;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jacaranda.model.CartItem;
import com.jacaranda.model.ShoppingCart;
import com.jacaranda.model.User;

/**
 * Servlet implementation class UpdateCartItemServlet
 */
@WebServlet("/UpdateCartItemServlet")
public class UpdateCartItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
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
    public UpdateCartItemServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession se = request.getSession();
		User loggedUser = (User) se.getAttribute("user");
		ShoppingCart cart = (ShoppingCart) se.getAttribute("shoppingCart");
		if (loggedUser != null) {

			String eleIdString = request.getParameter("elementId");
			String quantityString = request.getParameter("itemQuantity"); 
			
			
			if(eleIdString != null && !eleIdString.isBlank() && quantityString != null && !quantityString.isBlank()) {
				
				int eleId = Integer.parseInt(eleIdString);
				int quantity = Integer.parseInt(quantityString);
				
				Iterator<CartItem> iterator = cart.getRequestedItems().iterator();
				boolean found = false;
				
				while(iterator.hasNext() && !found) {
					CartItem iItem = iterator.next();
					
					if(iItem.getElementId() == eleId) {
						iItem.setQuantity(quantity);
						found = true;
					}
					
				}
				//saving in session the modified cart 
				se.setAttribute("shoppingCart", cart);
				//sending to shopping servlet to show again the shopping cart
				response.sendRedirect("Shopping");
				
			}else {
				//error
				response.getWriter().append(HTML_ERROR1 + "Ha ocurrido un error. Los valores introducidos no v&aacute;lidos." + HTML_ERROR2);
				
			}
		
		} else {
			response.getWriter().append(HTML_ERROR1 + "No te has autenticado." + HTML_ERROR3);
		}
	}


}

