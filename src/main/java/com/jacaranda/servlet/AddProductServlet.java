package com.jacaranda.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jacaranda.control.CategoryControl;
import com.jacaranda.control.ElementControl;
import com.jacaranda.model.Category;
import com.jacaranda.model.Element;
import com.jacaranda.model.User;

/**
 * Servlet implementation class AddProductServlet
 */
@WebServlet("/AddProductServlet")
public class AddProductServlet extends HttpServlet {
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
	public AddProductServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// verify that session is opened and user is an admin
		HttpSession se = request.getSession();
		User loggedUser = (User) se.getAttribute("user");
		if (loggedUser != null && loggedUser.isAdmin()) {
			// get parameters
		
			String name = request.getParameter("name");
			String description = request.getParameter("description");
			String priceString = request.getParameter("price");
			String stockString = request.getParameter("stock");
			String categoryString = request.getParameter("category");
			
			try {
			
				// if fields aren't null or empty 
				if (name != null && !name.isBlank() && description != null && !description.isBlank() && priceString != null && !priceString.isBlank() && stockString != null && !stockString.isBlank() && categoryString != null && !categoryString.isBlank()) {
					double price = Double.parseDouble(priceString);
					int stock = Integer.parseInt(stockString);
					int id = Integer.parseInt(categoryString);
					
					// get category to add new product
					if (id != -1) {
						Category cat = CategoryControl.getCategory(id);
						Element newEle = ElementControl.addElement(name, description, price, stock, cat);
						// if added show success message
						response.sendRedirect("LoginServlet");
					}else {
						response.getWriter().append(HTML_ERROR1 + "La categor&iacute;a no puede quedar vac&iacute;a." + HTML_ERROR2);
					}
				} else {
					//error the fields aren't correct
					response.getWriter().append(HTML_ERROR1 + "Los campos introducidos no son correctos." + HTML_ERROR2);
				}
			
			} catch (Exception e) {
				// if no added show error message
				response.getWriter().append(HTML_ERROR1 + e.getMessage() + HTML_ERROR2);

			}
			
		} else {
			response.getWriter().append(HTML_ERROR1 + "No te has autenticado." + HTML_ERROR3);
		}

	}

}
