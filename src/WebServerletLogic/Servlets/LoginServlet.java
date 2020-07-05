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

        Connection con = null;
        Statement stmt = null;
        ResultSet res = null;
        String name=null;

        boolean success=false;


        HttpSession session = request.getSession(true);

        try
        {


            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chessgame_database", "root", "admin123");
            stmt = con.createStatement();
            String q ="SELECT username,fullname,password,email_id FROM login";
            res = stmt.executeQuery(q);
            String thisname=request.getParameter("user_id");
            String thispwd=request.getParameter("password");

            while(res.next())
            {

                if ((thisname.equals(res.getString("username"))) && (thispwd.equals(res.getString("password"))))
                {
                    name=res.getString("fullname");
                    success = true;
                }
                else if((thisname.equals(res.getString("username"))) && (!thispwd.equals(res.getString("password")))){
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Incorrect User id or Password');");
                    out.println("location='/loginpage';");
                    out.println("</script>");
                }
            }

        }

        catch (SQLException e)
        {
            throw new ServletException("Servlet Could not display records.", e);
        }

        catch (ClassNotFoundException e)
        {
            throw new ServletException("JDBC Driver not found.", e);
        }

        catch (Exception e)
        {
            throw new ServletException("Exception.", e);
        }

        if(success==true)
        {

            session.setAttribute("username", request.getParameter("user_id"));
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