package WebServerletLogic.Servlets.UserAccountServlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
public class LogoutServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        session.setAttribute("userId", null);
        session.invalidate();
        out.println("<script type=\"text/javascript\">");
        out.println("alert('Logout Successful');");
        out.println("location='/';");
        out.println("</script>");

    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        HttpSession session = request.getSession(true);
        session.setAttribute("userId", null);
        session.invalidate();
        out.println("<script type=\"text/javascript\">");
        out.println("alert('Logout Successful');");
        out.println("location='/';");
        out.println("</script>");
    }
}
