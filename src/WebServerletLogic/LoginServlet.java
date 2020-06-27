package WebServerletLogic;


import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        String name=request.getParameter("name");
        String password=request.getParameter("password");
        if(password.equals("admin")){
            out.print("<h2 align=\"center\">You are successfully logged in!</h2>");
            out.printf("<br><h3 align=\"center\">Welcome,%s</h3> <br>",name);
            out.print("<br>");
            out.print("<form align=\"center\" action=\"load\" method=\"post\">\n    <input  type=\"submit\" value=\"Click Here to Play a Chess Png File\"/>");
            Cookie ck=new Cookie("name",name);
            response.addCookie(ck);
        }else{
            out.print("Incorrect Password");
            request.getRequestDispatcher("index.html").include(request, response);
        }

    }

}

