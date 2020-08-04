package WebServerletLogic.Servlets;

import GameLogic.AiManager;

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
        try {

            if (servletPath.equals("/diff")) {

                difficulty = req.getParameter("difficulty");
                aiManager.newGame();

            } else if (servletPath.equals("/usermoves") || servletPath.equals("/gamestatus")) {
                String userTimer=req.getParameter("timeTaken");
                String userMove=req.getParameter("userMove");
                String gameStatus = req.getParameter("gameStatus");
                aiManager.addMove(userMove, gameStatus,uniqueId,userTimer);

            } else if (servletPath.equals("/aiMove")) {
                PrintWriter out = resp.getWriter();
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                responseStep = aiManager.getAiMove(difficulty);
                out.print(responseStep);

            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
}
