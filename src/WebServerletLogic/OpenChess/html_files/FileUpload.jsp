<%@ page import="java.io.PrintWriter" %>
<html>
<head>
    <script>
        history.forward();
    </script>
</head>
<body>
<%
    PrintWriter out1 = response.getWriter();
    String  userId = (String)session.getAttribute("username");
if(userId == null) {
    out1.println("<script type=\"text/javascript\">");
    out1.println("alert('Login First');");
    out1.println("location='/loginpage';");
    out1.println("</script>");
}
%>
<h1 align="center"style="font-family:verdana;">Upload and Watch the Game By a click of Next</h1>
<form align="center" action="upload" method="post" enctype="multipart/form-data">
    Select File:<input type="file" name="file"/><br>
     <br><input type="submit" value="Upload"/>
</form>
<br>
<form align="center" action="/logout">
<input type="submit" value="Logout">
</form>
</body>
</html>
