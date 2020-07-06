package WebServerletLogic.Servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterServlet extends HttpServlet {

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
                out.println("<h3 align=\"center\" ><font family=\"Times New Roman\">Successfully Logout</font></h3>");

                out.println("<form align=\"center\" action=\"/loginpage\" method=\"post\">");
                out.println("<br />");
                out.println("<input type=\"submit\" value=\"Login Again!\"/>");
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

            boolean success=false;

            HttpSession session = request.getSession(true);

            try
            {

                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chessgame_database", "root", "admin123");
                stmt = con.createStatement();
                String username=request.getParameter("user_id");
                String fullname=request.getParameter("name");
                String password=request.getParameter("password");
                String gender = request.getParameter("gender");
                String email_id=request.getParameter("email");
                stmt.executeUpdate("insert into login values('"+username+"','"+fullname+"','"+password+"','"+email_id+"','"+gender+"')");
                stmt = con.createStatement();
                out.println("<br /><br /><h2 align=\"center\"><font family=\"Times New Roman\">Registered Successfully!</font></h2>");
                success = true;

            }

            catch (SQLException e) {

                out.print("<br /><h3 align=\"center\"><font color=\"red\">User Name Already Exist!</font></h3>");
                out.println("<form action=\"/Register\" align= \"center\" method=\"post\">");
                out.println("<input type=\"submit\" value=\"Try again\">");                         

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

                session.setAttribute("username", request.getParameter("username"));
                out.println("<h3 align=\"center\">Now Log in with ur registered userid and password</h3>");
                out.println("<form action=\"/loginpage\" align= \"center\" method=\"post\">");
                out.println("<input type=\"submit\" value=\"Login\">");
                out.println("</form>");
            }

            else if(success==false)
            {
                session.invalidate();
            }

            out.println("</body>");
            out.println("</html>");
        }

    }
    

