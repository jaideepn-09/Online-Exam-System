package project;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "logout", urlPatterns = { "/logout" })
public class logout extends HttpServlet {
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet logout</title>");
			out.println("</head>");
			out.println("<body>");

			HttpSession hs = request.getSession(false);
			hs.removeAttribute("examsubject");
			hs.removeAttribute("email");
			hs.removeAttribute("username");
			hs.invalidate();
			response.sendRedirect("index.jsp");

			out.println("</body>");
			out.println("</html>");
		}
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}
	@Override
	public String getServletInfo() {
		return "Short description";
	}
}
