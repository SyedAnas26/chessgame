package WebServerletLogic.Servlets.GameServlets;

import GameLogic.Managers.PlayPgnFileManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class PgnGameServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PlayPgnFileManager playPgnFileManager = new PlayPgnFileManager();
        String responseStep = null;
        String servletPath = req.getServletPath();
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        int uniqueId = (int) session.getAttribute("uniqueId");
        try {
            if (servletPath.equals("/getHistory")) {
                List<String> games = playPgnFileManager.getHistoryOfGames(uniqueId);
                out.print(Arrays.toString(games.toArray()));
            } else {
                int step = Integer.parseInt(req.getParameter("step"));
                int logId = Integer.parseInt(req.getParameter("logid"));
                int uniqueid = Integer.parseInt(req.getParameter("uniqueId"));
                String log = req.getParameter("log");
                responseStep = playPgnFileManager.playGame(log, logId, step, uniqueid);
            }
        } catch (Exception exception) {
            System.out.println("Png Game Servlet " + exception);
            System.out.println("Line No "+exception.getStackTrace()[0].getLineNumber());
        }
        out.print(responseStep);
    }
}

