package WebServerletLogic.Servlets;

import GameLogic.AiManager;
import GameLogic.StockfishConnector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class SinglePlayerServlet extends HttpServlet {
    String difficulty;
    String responseStep;
    AiManager aiManager = new AiManager();
    int uniqueId=0;
    int i=0;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        HttpSession session=req.getSession();
        uniqueId=(int)session.getAttribute("uniqueId");
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        StockfishConnector stc=new StockfishConnector();
        try {

            if (servletPath.equals("/diff")) {
                long gameId=0;
                difficulty = req.getParameter("difficulty");
                try {
                     gameId = System.currentTimeMillis();
                } catch (Exception throwables) {
                    throwables.printStackTrace();
                }
                String sendGameId="{\"gameId\":\""+gameId+"\"}";
                out.print(sendGameId);

            } else if (servletPath.equals("/usermoves") || servletPath.equals("/gamestatus")) {
                String userTimer=req.getParameter("timeTaken");
                System.out.println("UT"+userTimer);
                String userMove=req.getParameter("userMove");
                int uniqueId=Integer.parseInt(req.getParameter("uniqueId"));
                String gameStatus = req.getParameter("gameStatus");
                String gamePgn=req.getParameter("gamePgn");
                String fen=req.getParameter("FEN");
                long gameId=Long.parseLong(req.getParameter("gameId"));
                int moveNo=Integer.parseInt(req.getParameter("moveNo"));
                aiManager.addMove(gameId,moveNo,userMove,gameStatus,uniqueId,userTimer,gamePgn);
                String res=aiManager.getTotalTime(uniqueId,gameId);
                out.print(res);
            } else if (servletPath.equals("/aiMove")){
                int skill=Integer.parseInt(req.getParameter("skillLevel"));
                String FEN=req.getParameter("gameFEN");
                responseStep = aiManager.getAiMove(FEN,skill);
                System.out.println(responseStep);
                out.print(responseStep);
            }else if (servletPath.equals("/aiMoveinPgn")){
                long gameId=Long.parseLong(req.getParameter("gameId"));
                int moveNo=Integer.parseInt(req.getParameter("moveNo"));
                String aiTime=req.getParameter("aiTime");
                System.out.println("AT "+aiTime);
                String aimove=req.getParameter("aiMovePgn");
                String fen=req.getParameter("FEN");
                String game=req.getParameter("gamePgn");
                System.out.println(game);
                aiManager.addMove(gameId,moveNo,aimove,null,0,aiTime,game);
                String res=aiManager.getTotalTime(0,gameId);
                out.print(res);
            }
            else if(servletPath.equals("/getGamePosition")){
                uniqueId=Integer.parseInt(req.getParameter("uniqueId"));
                long gameId=Long.parseLong(req.getParameter("gameId"));
                String pgn=aiManager.getGamePosition(gameId,uniqueId);
                pgn="{\"gamePosition\":\""+pgn+"\"}";
                System.out.println(pgn);
                out.print(pgn);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
