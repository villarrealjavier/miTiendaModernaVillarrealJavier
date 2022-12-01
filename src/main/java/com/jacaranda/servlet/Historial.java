package com.jacaranda.servlet;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jacaranda.control.ConnectionDBException;
import com.jacaranda.control.SaleControl;
import com.jacaranda.model.Sale;
import com.jacaranda.model.ShoppingCart;
import com.jacaranda.model.User;

/**
 * Servlet implementation class Historial
 */
@WebServlet("/Historial")
public class Historial extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String HTML_SUCCESS1 = "<!DOCTYPE html>\r\n" + "<html lang=\"en\">\r\n" + "<head>\r\n"
			+ "    <meta charset=\"UTF-8\">\r\n" + "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
			+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
			+ "    <title>Historial de compra</title>\r\n"
			+ "    <link rel=\"stylesheet\" type=\"text/css\" href=\"CSS/productsList.css\">\r\n" + "</head>\r\n"
			+ "<body>\r\n" + "\r\n" + "    <div class=\"site_wrap\">\r\n" + "        <div class=\"title\">\r\n"
			+ "        <h1>Comestibles Correa</h1>\r\n" + "        </div>\r\n" + "        <div class=\"session\">";

	private static final String HTML_SUCCESS2 = "</div>\r\n" 
			+ "        <div class= \"footer\">\r\n" + "        <p>&copy; Comestibles Correa</p>\r\n"
			+ "        </div>\r\n" + "    </div>\r\n" + "</body>\r\n" + "</html>";
	
	private static final String HTML_ERROR1 = "<!DOCTYPE html>\r\n" + "<html lang=\"en\">\r\n" + "<head>\r\n"
			+ "    <meta charset=\"UTF-8\">\r\n" + "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
			+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
			+ "    <title>Error: Historial de compra</title>\r\n"
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
    public Historial() {
        super();
        // TODO Auto-generated constructor stub
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
			
			
			try {
				//get the list of sales by user order by date
				ArrayList<Sale> sales = SaleControl.getSalesByUser(loggedUser);
				
				if(sales.size()>0){
					String result = showHistory(sales);
					
					response.getWriter().append(HTML_SUCCESS1 + "<h1>Sesi&oacute;n: " + loggedUser.getName()
							+ "</h1>"
							+ "</div><div class=\"historybut\">"
							+ "<a href=\"LoginServlet\" class=\"button hist\">Volver</a>"
							+ "</div>"
							+ "<div class = table>"
							+ result + HTML_SUCCESS2);
					
				}else {
					response.getWriter().append(HTML_ERROR1 + "No has realizado ninguna compra a&uacute;n." + HTML_ERROR2);
				}
				
				
				
			} catch (ConnectionDBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else {
			response.getWriter().append(HTML_ERROR1 + "No te has autenticado." + HTML_ERROR3);
		}
		
	}

	private String showHistory(ArrayList<Sale> sales) {
		Iterator<Sale> iterator = sales.iterator();
		String result = "<table><tr><td>Fecha de compra </td><td>Producto</td><td>Cantidad</td><td>Precio</td>\r\n";
				
		while(iterator.hasNext()) {
			Sale iSale = iterator.next();
			result += "<tr><td>"
			+ DateTimeFormatter.ISO_LOCAL_DATE.format(iSale.getSalesDate())+ "  " + DateTimeFormatter.ISO_LOCAL_TIME.format(iSale.getSalesDate())
			+ "</td><td>" + iSale.getElement().getName()
			+ "</td><td>" + iSale.getQuantity()
			+ "</td><td>" + iSale.getPrice()*iSale.getQuantity()
			+ "</td></tr>\r\n";

		}
		
		result += "</table>";
		return result;
	}

}
