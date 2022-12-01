package com.jacaranda.servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jacaranda.control.ElementControl;
import com.jacaranda.control.SaleControl;
import com.jacaranda.model.CartItem;
import com.jacaranda.model.Element;
import com.jacaranda.model.ShoppingCart;
import com.jacaranda.model.User;

/**
 * Servlet implementation class ContinuePayment
 */
@WebServlet("/ContinuePayment")
public class ContinuePayment extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String HTML_SUCCESS1 = "<!DOCTYPE html>\r\n" + "<html lang=\"en\">\r\n" + "<head>\r\n"
			+ "    <meta charset=\"UTF-8\">\r\n" + "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
			+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
			+ "    <title>Confirmaci&oacute;n de compra</title>\r\n"
			+ "    <link rel=\"stylesheet\" type=\"text/css\" href=\"CSS/shoppingDone.css\">\r\n" + "</head>\r\n"
			+ "<body>\r\n" + "\r\n" + "    <div class=\"site_wrap\">\r\n" + "        <div class=\"title\">\r\n"
			+ "        <h1>Comestibles Correa</h1>\r\n" + "        </div>\r\n" + "        <div class=\"session\">";

	private static final String HTML_SUCCESS2 = "</div>\r\n" + "        <div class=\"back\">\r\n"
			+ "            <a href=\"LoginServlet\">Volver</a>\r\n" + "        </div>\r\n"
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
    public ContinuePayment() {
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
	
		//If user is logged in correctly get user and shopping cart
		HttpSession se = request.getSession();
		User loggedUser = (User) se.getAttribute("user");
		ShoppingCart cart = (ShoppingCart) se.getAttribute("shoppingCart");
		
		if (loggedUser != null) {
			
			//making sure shopping cart is not empty
			if(cart.getRequestedItems().size() > 0) {
				
				try {
					//iterate shopping cart to add products to sale table in DB
					SaleControl.addSale(cart.getRequestedItems());
					se.setAttribute("shoppingCart", new ShoppingCart());
					response.getWriter().append(HTML_SUCCESS1 + "<h1>Sesi&oacute;n: " + loggedUser.getName()
					+ "</h1>"
					+ "</div><div class=\"delbut\"><a href=\"index.jsp\" class=\"button close\">Cerrar sesi&oacute;n</a></div>"
					+ "<div class=\"message\"><h3>Gracias por su compra.</h3>" + HTML_SUCCESS2);
					
					
				} catch (Exception e) {
					// TODO: handle exception
					response.getWriter().append(HTML_ERROR1 + "<h3>"+ e.getMessage() +"</h3>"  + HTML_ERROR2);
				}
				
			} else {
				//shopping card is empty
				response.getWriter().append(HTML_ERROR1 + "No hay productos en el carro." + HTML_ERROR2);
			}
			
		} else {
			//user is not logged in
			response.getWriter().append(HTML_ERROR1 + "No te has autenticado." + HTML_ERROR3);
		}
		
	}
	
}
