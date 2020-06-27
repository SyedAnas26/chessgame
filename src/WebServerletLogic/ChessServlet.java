package WebServerletLogic;

import GameLogic.GameManager;
import GameLogic.Position;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

public class ChessServlet extends HttpServlet {
    GameManager manager= null;
    int step = 1;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Object gamePlay = req.getParameter("Next");
        String responseStep = null;

        try {
            HttpSession session = req.getSession();
            String FileName = (String) session.getAttribute("filename");
            initManager(FileName);
            manager.conductGame(step);
            responseStep =manager.getLastMovementAsString(step);
            step++;
            //printBoardInClient(manager,resp,step);
            System.out.println("responseStep: " + responseStep);
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.print(responseStep);
        } catch (Exception e) {
            e.printStackTrace();
        }




    }

    void printBoardInClient(GameManager manager, HttpServletResponse response,int step) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        if (step == manager.gamePlayAsArray.length - 3) {
            if (manager.gamePlayAsArray[manager.gamePlayAsArray.length - 1].charAt(1) == '/') {
                out.println("<h1 style=\"text-align:center\"> Match Draw !!!</h1>");
            } else if (manager.gamePlayAsArray[manager.gamePlayAsArray.length - 1].charAt(0) == '0') {
                out.println("<h1 style=\"text-align:center\"> Player 2 Won The Match (Black) !!!</h1>");
            } else
                out.println("<h1 style=\"text-align:center\"> Player 1 Won The Match(White) !!!</h1>");

        } else {
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset=\"UTF-8\"> ");
            out.println("<title>Chess Game</title>");
            out.println("<body>");
            out.println("<h1>Welcome To Chess Game</h1>");
            out.println("<style type=\"text/css\">");
            out.println("input { background-color: red;\n" +
                    "        color: white;\n" +
                    "        padding: 15px 25px;);\n" +
                    "       text-align: center;\n" +
                    "        text-decoration: none;\n" +
                    "        display: inline-block;\n" +
                    "            float:right;\n" +
                    "            margin:auto;\n" +
                    "        } ");

            out.println("input:hover, input:active {\n" +
                    "            background-color: red;\n" +
                    "        }");
            out.println(".chessboard {\n" +
                    "            width: 640px;\n" +
                    "            height: 640px;\n" +
                    "            margin: auto;\n" +
                    "            font-size:50px;\n" +
                    "            border: 5px solid #333;\n" +
                    "        }");
            out.println(".black {\n" +
                    "            float:left;\n" +
                    "            width: 80px;\n" +
                    "            height: 80px;\n" +
                    "            background-color: #999;\n" +
                    "            font-size:50px;\n" +
                    "            text-align:center;\n" +
                    "            display: table-cell;\n" +
                    "            vertical-align:middle;\n" +
                    "        }");
            out.println(".white {\n" +
                    "            float:left;\n" +
                    "            width: 80px;\n" +
                    "            height: 80px;\n" +
                    "            background-color: #fff;\n" +
                    "            font-size:50px;\n" +
                    "            text-align:center;\n" +
                    "            display: table-cell;\n" +
                    "            vertical-align:middle;\n" +
                    "        }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class=\"chessboard\">");


            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    String piece = manager.b.cell[i][j].getPieceType();
                    String htmlPiece = getHtmlPiece(piece);
                    Position pos = new Position(i, j);
                    String cellColor = manager.GetCurrentCellColor(pos);
                    out.printf("<div class=\"%s\">%s</div>", cellColor, htmlPiece);
                }
            }
            out.println("</div>");
            out.println(" <form action=\"\" method=\"post\">");
            out.println("<input type=\"submit\" name=\"Next\" value=\"Next\">");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private String getHtmlPiece(String piece) {
        for(int i=0;i<8;i++){
            if(piece.equals("BP"+i)){
                return "&#9823;";
            }
            if(piece.equals("WP"+i)){
                return "&#9817;";
            }
        }

        switch (piece) {
            case "BRR":
            case "BLR":
            case "BNR":
                return "&#9820;";
            case "BRN":
            case "BLN":
            case "BNN":
                return "&#9822;";
            case "BRB":
            case "BLB":
            case "BNB":
                return "&#9821;";
            case "BK":
                return "&#9818;";
            case "BQ":
            case "BNQ":
                return "&#9819;";
            case "WRR":
            case "WLR":
            case "WNR":
                return "&#9814;";
            case "WRN":
            case "WLN":
            case "WNN":
                return "&#9816;";
            case "WRB":
            case "WLB":
            case "WNB":
                return "&#9815;";
            case "WK":
                return "&#9812;";
            case "WQ":
            case "WNQ":
                return "&#9813;";
            default:
                return " ";
        }
    }

    void initManager(String FileName) throws Exception {
        if (manager != null) {
            return;
        }
        FileUpload fu=new FileUpload();
        String File="/FileUploads/"+FileName;
        ServletContext context=getServletContext();
        InputStream is = context.getResourceAsStream(File);
        InputStreamReader isr =new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line = br.readLine();
        StringBuilder sb = new StringBuilder();
        String gamePlay = null;
        while (line != null) {
            sb.append(line).append("\n");
            line = br.readLine();
            gamePlay =sb.toString();
        }

        gamePlay = gamePlay.replace("\n", " ").replace("\r", "");

        manager= new GameManager(gamePlay);
    }
}
