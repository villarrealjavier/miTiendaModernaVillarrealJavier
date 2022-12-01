package com.jacaranda.servlet;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jacaranda.control.ElementControl;
import com.jacaranda.model.CartItem;
import com.jacaranda.model.Element;
import com.jacaranda.model.ShoppingCart;
import com.jacaranda.model.User;

/**
 * Servlet implementation class addingItem
 */
@WebServlet("/addingItem")
public class addingItem extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
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
    public addingItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/** When an item is added a form is sent to this servlet in order to be added to the shopping cart 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//If user is logged in correctly get user and shopping cart
		HttpSession se = request.getSession();
		User loggedUser = (User) se.getAttribute("user");
		ShoppingCart cart = (ShoppingCart) se.getAttribute("shoppingCart");
		if (loggedUser != null) {
			
			String quantityS = request.getParameter("itemQuantity");
			String elementIdS = request.getParameter("elementId");
			
			//if parameter are correct adds item to shopping cart
			if (quantityS != null && !quantityS.isBlank() && elementIdS != null && !elementIdS.isBlank()) {
				
				try {
					int quantity = Integer.parseInt(quantityS);
					int eleId = Integer.parseInt(elementIdS);
					
					Element ele = ElementControl.getElement(eleId);
					
					if (ele.getStock() >= quantity) {
				
						// if item already exists it only changes the quantity
						CartItem itemExist = cart.getItemByElementId(eleId);
						if(itemExist != null) {
							itemExist.setQuantity(quantity);
						} else {
							//adds the products to the shopping cart if it doesn't exist
							CartItem newItem = new CartItem(loggedUser.getId(), ele.getEleId(), quantity, ele.getPrice() , LocalDateTime.now()); 
							cart.getRequestedItems().add(newItem);
						}
					
//						se.setAttribute("shoppingCart", cart);
						//total items in the shopping cart
						System.out.println(cart.getRequestedItems().size());
						
						//when added redirect to list of products
						response.sendRedirect("LoginServlet");
						
					} else {
						//Error: quantity cannot be greater than stock 
						response.getWriter()
						.append(HTML_ERROR1 + "<h3>La cantidad del producto no puede superar al stock existente.</h3>" + HTML_ERROR2);
					}
					
				} catch (Exception e) {
					//Error: in case there an Exception in parsing or getting the element or item 
					response.getWriter()
					.append(HTML_ERROR1 + "<h3>"+ e.getMessage() +"</h3>" + HTML_ERROR2);
				}
				
			} else {
				//ERROR: the parameters are wrong
				response.getWriter()
				.append(HTML_ERROR1 + "<h3>Los datos ingresados no son correctos.</h3>" + HTML_ERROR2);
			}
		
			
		} else {
			response.getWriter()
			.append(HTML_ERROR1 + "<h3>No estás autenticado</h3>" + HTML_ERROR3);
		}
	
	}

}
