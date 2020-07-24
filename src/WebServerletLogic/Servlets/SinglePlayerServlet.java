package WebServerletLogic.Servlets;

import GameLogic.AiManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SinglePlayerServlet extends HttpServlet {
    String difficulty;
    String responseStep;
    AiManager aiManager = new AiManager();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        //System.out.println("Servlet Path" + servletPath);
        try {

            if (servletPath.equals("/diff")) {

                difficulty = req.getParameter("difficulty");
                aiManager.newGame();

            } else if (servletPath.equals("/usermoves") || servletPath.equals("/drawclaim")) {

                String fromPos = req.getParameter("fromMove");
                String toPos = req.getParameter("toMove");
                String drawClaim = req.getParameter("claimDraw");
                aiManager.addMove(fromPos, toPos, drawClaim);

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
