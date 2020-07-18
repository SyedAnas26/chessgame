<!DOCTYPE unspecified PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<script type="text/javascript" src="../clientlib/jquery.min-3.5.js"></script>
<script type="text/javascript" src="../clientlib/jquery-ui.min.js"></script>

<script type="text/javascript">
this.CallAi=function () {

    this.moveAi=function () {


    <%
  String[] moveArray = (String[])session.getAttribute("MovesArr");
  for (int i = 0; i < moveArray.length; ++i)
  {
%>
    var moveArr=new Array();
    moveArr["<%=i%>"]= "<%= moveArray[i] %>"
    <%
  }
%>
 var chessAI = require('chess-ai-kong');
 var diff = sessionStorage.getItem("Difficulty")
 chessAI.setOptions(
         {
          depth: diff,
          monitor: false,
          strategy: 'basic',
          timeout: 10000
         });
 var move =  chessAI.play([moveArr.slice(2)]);
    $.ajax({
        url: "http://localhost:8080/aimove",
        type: 'POST',
        data: {"move":move},
        success: function () {
            console.log(' AI ajax success!!');
        },
        error: function (jqXHR, exception) {
            console.log(' AI Error occured!!');
        }
    });
    };

};
</script>
</html>