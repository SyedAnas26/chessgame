package WebServerletLogic.Servlets;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class LoginServlet extends HttpServlet
{

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html>");
        out.println("<script>");
        out.println("javascript:window.history.forward(1)");
        out.println("</script>");
        out.println("<body>");
        HttpSession session = request.getSession(false);
        try
        {
            session.invalidate();
            out.println("<h3 align=\"center\"><font family=\"Times New Roman\" color=\"purple\">Successfully Logout</font></h3>");
            out.println("<form align=\"center\" action=\"/loginpage\" method=\"post\">");
            out.println("<br />");
            out.println("<input type=\"submit\" alt=\"submit\" value=\"Login Again!\"/>");
            out.println("</form>");

            out.println("</body>");
            out.println("</html>");
        }

        catch (Exception ee)
        {
            out.println(ee);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {

        response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<body>");


        ResultSet res = null;
        String name=null;

        boolean success=false;


        HttpSession session = request.getSession(true);

        try
        {


            DBclass db=new DBclass();
            db.callDB();
            String thisname=request.getParameter("username");
            String thispwd=request.getParameter("password");
            String q = "SELECT * FROM login WHERE username='" + thisname + "' and password='" + thispwd +"'";
            System.out.println(q);
            res = db.stmt.executeQuery(q);

            if(res.next()) {
                name = res.getString("fullname");
                success = true;
            }
        }

        catch (SQLException e)
        {
            throw new ServletException("Servlet Could not display records.", e);
        }


        catch (Exception e)
        {
            throw new ServletException("Exception.", e);
        }

        if(success==true)
        {

            session.setAttribute("username", request.getParameter("username"));
            session.setAttribute("sessname", name);
            response.sendRedirect("/homepage");
        }

        else if(success==false)
        {
            out.println("<br /><h3 align=\"center\"><font color=\"red\"> Seems you Don't have a account <br>Please Sign up here</font></h3>");
            out.println("<form action=\"/Register\" align= \"center\" method=\"post\"><br>");
            out.println("<input type=\"submit\" value=\"Sign Up\"><br></form>");
            out.println("<br>");
            out.println("<form action=\"/loginpage\" align= \"center\" method=\"post\">");
            out.println("<input type=\"submit\" value=\"Try Login Again\"></form>");
            session.invalidate();
        }

        out.println("</body>");
        out.println("</html>");
    }

}