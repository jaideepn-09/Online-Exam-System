package project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "register", urlPatterns = { "/register" })
public class register extends HttpServlet {
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ClassNotFoundException, SQLException {
		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("</head>");
			out.println("<body>");

			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String password = request.getParameter("pass");

			String url = "jdbc:mysql://localhost:3306/ejproj";
			String dbusername = "root";
			String dbpassword = "root";
			String query = "insert into login(username,email,password) values (?,?,?)";

			Connection con = null;
			PreparedStatement preparedStatement = null;

			Class.forName("com.mysql.cj.jdbc.Driver");

			try {

				con = DriverManager.getConnection(url, dbusername, dbpassword);

				preparedStatement = con.prepareStatement(query);
				preparedStatement.setString(1, name);
				preparedStatement.setString(2, email);
				preparedStatement.setString(3, password);

				int i = preparedStatement.executeUpdate();

				if (i != 0) {
					HttpSession hs = request.getSession();
					hs.setAttribute("name", name);
					response.sendRedirect("login.jsp");
				} else {

					HttpSession hs = request.getSession();
					hs.setAttribute("ermsg", "Error, Please Try Again");
				}
			} catch (SQLException ex) {

				Logger.getLogger(register.class.getName()).log(Level.SEVERE, null, ex);
			}

			out.println("</body>");
			out.println("</html>");
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(register.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(register.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(register.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(register.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public String getServletInfo() {
		return "Short description";
	}
}
