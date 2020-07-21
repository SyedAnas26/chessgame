<!DOCTYPE html>
<html>
<head>
    <title>Difficulty</title>
<style type="text/css">
#button1{
 background-color: #1DE3E0;
 border: 1px;
 color: #082C2B;
    padding: 16px 32px;
}
#button2{
    background-color: #E7C718;
    border: 1px;
    color: #082C2B;
    padding: 16px 32px;
}
#button3{
    background-color: #E1351C;
    border: 1px;
    color: #082C2B;
    padding: 16px 32px;
;
}
</style>
    <script type="text/javascript">
function clickResp(action){
    var diff;
if(action==='Easy'){
     diff=3
}
else if(action==='Medium'){
     diff=6
}
else if(action==='Hard'){
    diff=20
}
}</script>

<%
String  userId = (String)session.getAttribute("username");
session.setAttribute("NewOrOldGame","New");
session.setAttribute("Difficulty","3");
if(userId == null) {
    out.println("<script type=\"text/javascript\">");
    out.println("alert('Login first');");
    out.println("location='/loginpage';");
    out.println("</script>");
    }
%>
</head>
<body><div>
   <form action="vscomputer" method="post" align="center" >
       <br>
       <br>
       <br>
   <input id="button1"  type="submit" onclick="clickResp('Easy')" value="Easy" ><br><br>
   <input id="button2"  type="submit"  onclick="clickResp('Medium')" value="Medium"><br><br>
   <input id="button3"  type="submit" onclick="clickResp('Hard')"   value="Hard"><br><br>
    </form>
</div>
</body>
</html>