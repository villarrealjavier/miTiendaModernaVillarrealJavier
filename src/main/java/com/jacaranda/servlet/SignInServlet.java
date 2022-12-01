package com.jacaranda.servlet;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;

import com.jacaranda.control.UserControl;
import com.jacaranda.model.User;

/**
 * Servlet implementation class SignInServlet
 */
@WebServlet(description = "servlet for signning in", urlPatterns = { "/SignInServlet" })
public class SignInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L; 
	
	private static final String HTML_SUCCESS1 = "<!DOCTYPE html>\r\n"
			+ "<html lang=\"en\">\r\n"
			+ "<head>\r\n"
			+ "    <meta charset=\"UTF-8\">\r\n"
			+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
			+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
			+ "    <title>Confirmación de acceso</title>\r\n"
			+ "    <link rel=\"stylesheet\" type=\"text/css\" href=\"CSS/success.css\">\r\n"
			+ "</head>\r\n"
			+ "<body>\r\n"
			+ "\r\n"
			+ "    <div class=\"site_wrap\">\r\n"
			+ "        <div class=\"title\">\r\n"
			+ "        <h1>Comestibles Correa</h1>\r\n"
			+ "        </div>\r\n"
			+ "        <div class=\"success\">";
	private static final String HTML_SUCCESS2 = "</div>\r\n"
			+ "        <div class=\"back\">\r\n"
			+ "            <a href=\"index.jsp\">Volver al inicio de sessión</a>\r\n"
			+ "        </div>\r\n"
			+ "        <div class= \"footer\">\r\n"
			+ "        <p>&copy; Comestibles Correa</p>\r\n"
			+ "        </div>\r\n"
			+ "    </div>\r\n"
			+ "</body>\r\n"
			+ "</html>";
	private static final String HTML_ERROR1 = "<!DOCTYPE html>\r\n"
			+ "<html lang=\"en\">\r\n"
			+ "<head>\r\n"
			+ "    <meta charset=\"UTF-8\">\r\n"
			+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
			+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
			+ "    <title>Confirmación de acceso</title>\r\n"
			+ "    <link rel=\"stylesheet\" type=\"text/css\" href=\"CSS/error.css\">\r\n"
			+ "</head>\r\n"
			+ "<body>\r\n"
			+ "\r\n"
			+ "    <div class=\"site_wrap\">\r\n"
			+ "        <div class=\"title\">\r\n"
			+ "        <h1>Comestibles Correa</h1>\r\n"
			+ "        </div>\r\n"
			+ "        <div class=\"error\">";
       
	private static final String HTML_ERROR2 = "</div>\r\n"
			+ "        <div class=\"back\">\r\n"
			+ "            <a href=\"index.jsp\">Volver al inicio</a>\r\n"
			+ "        </div>\r\n"
			+ "        <div class= \"footer\">\r\n"
			+ "        <p>&copy; Comestibles Correa</p>\r\n"
			+ "        </div>\r\n"
			+ "    </div>\r\n"
			+ "</body>\r\n"
			+ "</html>";
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignInServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String lastname = request.getParameter("lastname");
//		Time is added as the date of birth only returns a date
		String dobString = request.getParameter("dob");
		String sexString = request.getParameter("sex");
		boolean admin = false;
		User newUser=null;
		
		response.setContentType("text/html");
		
		try {
			if (userName != null && !userName.isBlank() && password != null && !password.isBlank() && name != null && !name.isBlank() && lastname != null && !lastname.isBlank() && dobString != null && !dobString.isBlank() && sexString != null && !sexString.isBlank() ) {
				LocalDateTime dob = LocalDateTime.parse(dobString + "T00:00:00");
				char sex = sexString.charAt(0);
				String encriptedPassword = DigestUtils.md5Hex(password);
				if (dob.isBefore(LocalDateTime.now().minusYears(18))) {
					newUser = UserControl.addUser(userName, encriptedPassword, name, lastname, dob, sex, admin);
					
				}else {
					response.getWriter().append(HTML_ERROR1 + "<h3>El usuario debe ser mayor de edad.</h3>" + HTML_ERROR2);
				}
				
				if (newUser != null) {
					//user is created
					response.getWriter().append(HTML_SUCCESS1 + "<h3>¡Enhorabuena! Tu cuenta ha sido creada con éxito.</h3>" + HTML_SUCCESS2);
				} else {
					//error 
					response.getWriter().append(HTML_ERROR1 + "<h3>El usuario ya existe.</h3>" + HTML_ERROR2);
				}
			} else {
				response.getWriter().append(HTML_ERROR1 + "<h3>No se puden dejar campos vacíos o nulos.</h3>" + HTML_ERROR2);
			}
	
		} catch (Exception e) {
			response.getWriter().append(HTML_ERROR1 + "<h3>Ha ocurrido un error inesperado. "+ e.getMessage() +" Contacte con el administrador.</h3>" + HTML_ERROR2);
		}
	}

}
