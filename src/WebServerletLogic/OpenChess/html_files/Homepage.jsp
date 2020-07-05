<html>
<head>
    <title>
        Home Page
    </title>
<h1 align="center">Home</h1>
    <br>
</head>
<body>
<%
    String  userId = (String)session.getAttribute("username");
    if(userId == null) {
        out.println("<script type=\"text/javascript\">");
        out.println("alert('Login first');");
        out.println("location='/loginpage';");
        out.println("</script>");
    }
String name=(String)session.getAttribute("sessname");
out.print("<h2 align=\"center\">Welcome Mr."+name+"</h2>");
%>
<form action="load" align="center" method="post">
    <input  type="submit" value="Click Here to Play Chess Game"><br></form>
<form action="load" align="center" method="post">
    <input  type="submit" value="Click Here to Play a Chess Pgn File"><br></form>
<form action="/logout" method="post" align="center">
    <input type="submit" value="Logout">

</form>
</body>
</html>

