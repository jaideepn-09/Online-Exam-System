package project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "evaluate", urlPatterns = { "/evaluate" })
public class evaluate extends HttpServlet {
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/ejproj";
		String dbusername = "root";
		String dbpassword = "root";
		Statement statement = null;
		ResultSet resultset = null;
		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException ex) {
				Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
			}
			conn = DriverManager.getConnection(url, dbusername, dbpassword);
			Enumeration retrieve = request.getParameterNames();
			statement = conn.createStatement();
			HttpSession session = request.getSession();
			String selectedexam = (String) session.getAttribute("selectedexam");
			int marks = 0;

			while (retrieve.hasMoreElements()) {

				String var = (String) retrieve.nextElement();
				resultset = statement.executeQuery("select question,ans from " + selectedexam + " where qno=" + var + "");

				String[] useranswers = request.getParameterValues(var);

				for (String i : useranswers) {

					while (resultset.next()) {
						String rightans = resultset.getString("ans");

						if (i.equals(rightans)) {
							marks++;

						}

					}
				}
			}
			out.println("<h1 style=\"text-align:center;\">The Examination Has Ended</h1>");
			out.println(
					"<h4 style=\"text-align:center;\">Please Click on the 'Record Answers' Button below to Record them for Evaluation</h4>");
			out.println(" <center><form method=\"POST\" action=\"setmarks.jsp\">\n" + "            \n"
					+ "            <input type=\"hidden\" name=\"uscore\" value=" + marks + ">\n"
					+ "            <input type=\"submit\" value=\"Record Answers\">\n" + "        </form>\n"
					+ "    </center>");

//System.out.println(scorecounter);
		} catch (SQLException ex) {
			Logger.getLogger(evaluate.class.getName()).log(Level.SEVERE, null, ex);
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
