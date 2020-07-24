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

public class PgnGameServlet extends HttpServlet {
int step=0;
boolean nextCalled=true;
PlayPgnFile playPgnFile=new PlayPgnFile();

@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        ServletContext servletContext=req.getServletContext();
        String responseStep = null;
        HttpSession session = req.getSession();
        String fileName = (String) session.getAttribute("filename");
        String File="/FileUploads/"+fileName;
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String servletPath = req.getServletPath();
        if(servletPath.equals("/startnewgame")) {
            newGame();
        }else {
         updateStep(servletPath);
        playPgnFile.playGame(File,step,servletContext);
        responseStep=playPgnFile.getResponseStep(step);
        out.print(responseStep);
        }
    }

    private void newGame() {
        step=0;
    }
    private void updateStep(String servletPath) {
        if(servletPath.equals("/nextstep"))
        {
            step++;
            nextCalled=true;

        }
        else if(servletPath.equals("/previous_step")){
            if(!nextCalled){
                if(step>0) {
                    --step;
                }
                else {
                    System.out.println("Play next first");
                }
            }
            else if(nextCalled){
                nextCalled=false;
                step=step;
            }
        }
    }
}
