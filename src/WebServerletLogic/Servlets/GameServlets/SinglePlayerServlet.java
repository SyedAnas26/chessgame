package WebServerletLogic.Servlets.GameServlets;

import GameLogic.Managers.AiManager;
import GameLogic.Managers.StockfishConnector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SinglePlayerServlet extends HttpServlet {

    AiManager aiManager = new AiManager();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        StockfishConnector stc=new StockfishConnector();
        try {
            switch (servletPath) {
                case "/diff":
                    int uniqueId = Integer.parseInt(req.getParameter("uniqueId"));
                    String challengeType = req.getParameter("difficulty");
                    String response = aiManager.createAiGame(uniqueId, challengeType);
                    out.print(response);
                    break;

                case "/aiMove":
                    int skill = Integer.parseInt(req.getParameter("skillLevel"));
                    String FEN = req.getParameter("gameFEN");
                    String responseStep = aiManager.getAiMove(FEN, skill);
                    out.print(responseStep);
                    break;

                case "/aiMoveinPgn":
                    long gameId = Long.parseLong(req.getParameter("gameId"));
                    int moveNo = Integer.parseInt(req.getParameter("moveNo"));
                    String aiTime = req.getParameter("aiTime");
                    String aiMovePgn = req.getParameter("aiMovePgn");
                    String fen = req.getParameter("FEN");
                    String game = req.getParameter("gamePgn");
                    aiManager.addMove(gameId, moveNo, aiMovePgn, null, 0, aiTime, game);
                    String res = aiManager.opponentRemainingTime(0, gameId,moveNo);
                    out.print(res);
                    break;

            }
        } catch (Exception exception) {
            System.out.println("SinglePlayerServlet "+exception);
        }
    }
}
