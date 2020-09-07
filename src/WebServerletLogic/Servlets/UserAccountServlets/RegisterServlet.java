package WebServerletLogic.Servlets.UserAccountServlets;

import GameLogic.Managers.DbConnector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class RegisterServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<body>");

        boolean success = false;
        HttpSession session = request.getSession(true);

        try {
            String username = request.getParameter("username");
            String fullname = request.getParameter("name");
            String password = request.getParameter("password");
            String gender = request.getParameter("gender");
            String email_id = request.getParameter("email");
            String q = "SELECT * FROM login WHERE username='" + username + "'";
            success = (Boolean) DbConnector.get(q, res -> {
                if (!res.next()) {
                    DbConnector.update("insert into login(username,fullname,password,email_id,gender) values('" + username + "','" + fullname + "','" + password + "','" + email_id + "','" + gender + "')");
                    return true;
                }
                return false;
            });

        } catch (SQLException e) {

            out.println("<script type=\"text/javascript\">");
            out.println("alert('User Name Already Exists');");
            out.println("location='/Register';");
            out.println("</script>");
            System.out.println("User Name Already Exists " + e);

        } catch (Exception e) {
            throw new ServletException("Register Servlet Exception ", e);
        }

        if (success) {

            session.setAttribute("username", request.getParameter("username"));
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Sign up Successful, Log in Now !');");
            out.println("location='/loginpage';");
        } else {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('User Name Already Exists');");
            out.println("location='/Register';");
        }
        out.println("</script>");
        out.println("</body>");
        out.println("</html>");
        session.invalidate();
    }

}
    


