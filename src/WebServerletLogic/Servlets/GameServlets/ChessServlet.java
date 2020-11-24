package WebServerletLogic.Servlets.GameServlets;

import GameLogic.Managers.ChessManager;
import GameLogic.Managers.StockfishConnector;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ChessServlet extends HttpServlet {
    ChessManager chessManager = new ChessManager();
    StockfishConnector stockfishConnector=new StockfishConnector();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        String servletPath = req.getServletPath();
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String uniqueId; long gameId;
        try {
            switch (servletPath)
            {
            case "/storeMove" :
            case "/gamestatus":
                System.out.println("Store came");
                String timeTaken = req.getParameter("timeTaken");
                System.out.println("tt "+timeTaken);
                String move = req.getParameter("move");
                System.out.println("move= "+move);
                String gameStatus = req.getParameter("gameStatus");
                String gamePgn = req.getParameter("gamePgn");
                System.out.println("gamePgn= "+gamePgn);
                String fen = req.getParameter("FEN");
                gameId = Long.parseLong(req.getParameter("gameId"));
                int moveNo = Integer.parseInt(req.getParameter("moveNo"));
                if(gamePgn!=null) {
                    chessManager.addMove(gameId, moveNo, move, gameStatus, req.getParameter("uniqueId"), timeTaken, gamePgn);
                }
                String res = chessManager.opponentRemainingTime(req.getParameter("uniqueId"), gameId,moveNo);
                out.print(res);
                break;

                case "/analyseBoard":
                    String Fen=req.getParameter("fen");
                    String Score = stockfishConnector.getEvalScore(Fen);
                    out.print("{\"Score\":\""+Score+"\"}");
                    break;

                case "/getGamePosition":
                System.out.println("ajaxCame");
                uniqueId = req.getParameter("uniqueId");
                gameId = Long.parseLong(req.getParameter("gameId"));
                String response = chessManager.getGamePosition(gameId, uniqueId);
                System.out.println(response);
                out.print(response);
                break;
                default:
                    throw new IllegalStateException("Unexpected value: " + servletPath);
            }
        } catch (Exception e) {
            System.out.println("Chess Servlet Exception " + e);
            e.printStackTrace();
            System.out.println("Line No "+e.getStackTrace()[3].getLineNumber());
        }
    }
}
