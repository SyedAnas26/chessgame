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
    int step = 0;
    boolean nextCalled = true;
    boolean prevCalled = false;
    PlayPgnFile playPgnFile = new PlayPgnFile();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


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

            newGame();
            String tomPath = req.getServletContext().getRealPath("");
            String idGameLog = req.getParameter("idGameLog");
            playPgnFile.setWatchHistoryFile(tomPath, idGameLog);
            session.setAttribute("filename", "watchHistory.txt");

        } else if (servletPath.equals("/startnewgame")) {

            newGame();

        } else {

            String fileName = (String) session.getAttribute("filename");
            String File = "/FileUploads/" + fileName;
            updateStep(servletPath);
            playPgnFile.playGame(File, step, servletContext);
            responseStep = playPgnFile.getResponseStep(step);
            out.print(responseStep);

        }
    }

    private void newGame() {
        step = 0;
    }

    private void updateStep(String servletPath) {
        if (servletPath.equals("/nextstep")) {
            if (prevCalled) {
                prevCalled = false;
            } else {
                step++;
            }
            nextCalled = true;


        } else if (servletPath.equals("/previous_step")) {
            if (!nextCalled) {
                if (step > 0) {
                    --step;
                    prevCalled = true;
                } else {
                    System.out.println("Play next first");
                }
            } else if (nextCalled) {
                nextCalled = false;
                prevCalled = true;
                step = step;
            }

        }
    }
}
