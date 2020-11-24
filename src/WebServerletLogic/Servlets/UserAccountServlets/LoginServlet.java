package WebServerletLogic.Servlets.UserAccountServlets;

import GameLogic.Managers.DbConnector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getServletPath().equals("/getUserId")) {

            HttpSession sess = request.getSession();
            String user = "{\"user_id\":\"" + sess.getAttribute("userId") + "\",\"user_name\":\"" + sess.getAttribute("userName") + "\",\"unique_id\":\"" + sess.getAttribute("uniqueId") + "\"}";
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(user);

        } else {
            response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            String name = null;
            int uniqueId = 0;
            boolean success = false;
            HttpSession session = request.getSession(true);
            String thisName = request.getParameter("user_id");
            String thisPwd = request.getParameter("password");
            String query = "SELECT * FROM login WHERE username='" + thisName + "' and password= SHA('" + thisPwd + "')";

            try {
                name = (String) DbConnector.get(query, res -> {
                    try {
                        if (res.next()) {
                            return res.getString("fullname");
                        }
                    } catch (Exception e) {
                        throw new ServletException("Login Servlet Exception.", e);
                    }
                    return null;
                });
                uniqueId = (int) DbConnector.get(query, res -> {
                    try {
                        if (res.next()) {
                            return res.getInt("UserId");
                        }
                    } catch (Exception e) {
                        throw new ServletException("Exception in Login Servlet ", e);
                    }
                    return null;
                });

            } catch (Exception e) {
                System.out.println("Exception in Login Servlet " + e);
            }

            if (name != null) {
                success = true;
            }

            if (success) {

                session.setAttribute("userId", request.getParameter("user_id"));
                session.setAttribute("userName", name);
                session.setAttribute("uniqueId", uniqueId);
                response.sendRedirect("/homepage/" + uniqueId);

            } else {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Incorrect UserId or Password');");
                out.println("location='/loginpage/signin';");
                out.println("</script>");
                session.invalidate();
            }
        }
    }
}