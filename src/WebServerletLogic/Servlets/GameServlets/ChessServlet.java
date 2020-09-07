package WebServerletLogic.Servlets.GameServlets;

import GameLogic.Managers.ChessManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ChessServlet extends HttpServlet {
    ChessManager chessManager = new ChessManager();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        String servletPath = req.getServletPath();
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        int uniqueId; long gameId;
        try {
            switch (servletPath)
            {
            case "/usermoves" :
            case "/gamestatus":
                String userTimer = req.getParameter("timeTaken");
                String userMove = req.getParameter("userMove");
                uniqueId = Integer.parseInt(req.getParameter("uniqueId"));
                String gameStatus = req.getParameter("gameStatus");
                String gamePgn = req.getParameter("gamePgn");
                String fen = req.getParameter("FEN");
                gameId = Long.parseLong(req.getParameter("gameId"));
                int moveNo = Integer.parseInt(req.getParameter("moveNo"));
                chessManager.addMove(gameId, moveNo, userMove, gameStatus, uniqueId, userTimer, gamePgn);
                String res = chessManager.getTotalTime(uniqueId, gameId);
                out.print(res);
                break;

                case "/getGamePosition":
                uniqueId = Integer.parseInt(req.getParameter("uniqueId"));
                gameId = Long.parseLong(req.getParameter("gameId"));
                String pgn = chessManager.getGamePosition(gameId, uniqueId);
                pgn = "{\"gamePosition\":\"" + pgn + "\"}";
                out.print(pgn);
                break;
            }
        } catch (Exception e) {
            System.out.println("Chess Servlet Exception " + e);
            System.out.println("Line No "+e.getStackTrace()[0].getLineNumber());
        }
    }
}
