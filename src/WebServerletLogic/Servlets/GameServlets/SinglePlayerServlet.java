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
                    System.out.println("ai came");
                    String FEN = req.getParameter("gameFEN");
                    System.out.println(FEN);
                    String skill = req.getParameter("skillLevel");
                    System.out.println(skill);
                    String responseStep = aiManager.getAiMove(FEN, Integer.parseInt(skill));
                    out.print(responseStep);
                    break;


            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
