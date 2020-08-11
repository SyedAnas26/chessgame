package WebServerletLogic.Servlets;

import GameLogic.PlayPgnFile;

import javax.servlet.ServletContext;
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
        PlayPgnFile playPgnFile = new PlayPgnFile();
        ServletContext servletContext = req.getServletContext();
        String responseStep = null;
        String servletPath = req.getServletPath();
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        int uniqueId = (int) session.getAttribute("uniqueId");
        if (servletPath.equals("/getHistory")) {
            List<String> games = playPgnFile.getHistoryOfGames(uniqueId);
            out.print(Arrays.toString(games.toArray()));

        } else if (servletPath.equals("/setGame")) {

            String idGameLog = req.getParameter("idGameLog");
            int id=Integer.parseInt(idGameLog.substring(1));
            session.setAttribute("log", "gamelog");
            session.setAttribute("logId", id);

        } else{
            int step = Integer.parseInt(req.getParameter("step"));
            String log = (String) session.getAttribute("log");
            int logId = (int) session.getAttribute("logId");
            try {
                responseStep = playPgnFile.playGame(log, logId, step);
            } catch (Exception exception) {
                System.out.println(exception);
            }
            out.print(responseStep);
        }
    }
}

