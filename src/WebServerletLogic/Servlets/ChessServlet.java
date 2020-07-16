package WebServerletLogic.Servlets;
import GameLogic.GameManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChessServlet extends HttpServlet {
    GameManager manager= null;
    int step = 0;
    boolean nextcalled=true;



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        String FileName = (String) session.getAttribute("filename");
        manager=null;
        step = 0;
        try {
            initManager(FileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String responseStep = null;
        HttpSession session = req.getSession();
        String FileName = (String) session.getAttribute("filename");
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String servletPath = req.getServletPath();
        try {

            isNew(req);
            initManager(FileName);
            updateStep(servletPath);
            String winCheck=checkForStatus(manager,resp);
            if(winCheck!=null){
                out.print(winCheck);
            }
            for(int i=1;i<=step;i++){
                manager.conductGame(i);
            }
            responseStep =manager.getLastMovementAsString();
            System.out.println("responseStep: " + responseStep);
            out.print(responseStep);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String checkForStatus(GameManager gameManager,HttpServletResponse resp ) throws IOException {


        int totalsteps;
        int add;
        String responseStatus = null;
        try {
            totalsteps = Integer.parseInt(gameManager.gamePlayAsArray[gameManager.gamePlayAsArray.length - 3]);
            System.out.println("totalStep"+totalsteps);
            add = 0;
        } catch (NumberFormatException e) {
            totalsteps = Integer.parseInt(gameManager.gamePlayAsArray[gameManager.gamePlayAsArray.length - 4]);
            System.out.println("totolstep" + totalsteps);
            add = 1;
        }

        if (step == (totalsteps*2)+1) {
            if (gameManager.gamePlayAsArray[((totalsteps * 3) - 1 )+ add].equals("1/2-1/2")) {
                  responseStatus="{\"from_pos\": \"0\", \"to_pos\" : \"0\",\"checkStatus\":\"Draw\"}";

            } else if (gameManager.gamePlayAsArray[((totalsteps * 3) - 1) + add].equals("0-1")) {
                responseStatus="{\"from_pos\": \"0\", \"to_pos\" : \"0\",\"checkStatus\":\"Player2\"}";

            } else if (gameManager.gamePlayAsArray[((totalsteps * 3) - 1) + add].equals("1-0"))
            {
                responseStatus="{\"from_pos\": \"0\", \"to_pos\" : \"0\",\"checkStatus\":\"Player1\"}";

                 }

        }
        return responseStatus;

    }


    private void updateStep(String servletPath) {
        if(servletPath.equals("/nextstep"))
        {
            step++;
            nextcalled=true;

        }
        else if(servletPath.equals("/previous_step")){
            if(!nextcalled){
                if(step>0) {
                    --step;
                }
                else {
                    System.out.println("Play next first");
                }
            }
            else if(nextcalled){
                nextcalled=false;
                step=step;
            }

        }

    }

    private void isNew(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String NewOrOld = (String) session.getAttribute("NewOrOld");
        if(NewOrOld.equals("New")) {
            manager=null;
            step=0;
            session.setAttribute("NewOrOld", "Old");
        }
    }

    void initManager(String FileName) throws Exception {

        String File="/FileUploads/"+FileName;
        ServletContext context=getServletContext();
        InputStream is = context.getResourceAsStream(File);
        InputStreamReader isr =new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line = br.readLine();
        StringBuilder sb = new StringBuilder();
        String string = null;
        while (line != null) {
            sb.append(line).append("\n");
            line = br.readLine();
            string =sb.toString();
        }
        String gamePlay =regexElimination(string);
        gamePlay = gamePlay.replace("\n", " ").replace("\r", "");
        manager= new GameManager(gamePlay);

    }

    private String regexElimination(String string) {

        String regex = "\\[.*]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        String tmp = matcher.replaceAll(" ");
        tmp.trim ();
        String regex2 = "\\{.*?}";
        Pattern pattern2 = Pattern.compile(regex2);
        Matcher matcher2 = pattern2.matcher(tmp);
        String tmp2 = matcher2.replaceAll(" ");
        tmp2.trim ();
        String regex3 = " \"\\\\(.*?)\"";
        Pattern pattern3 = Pattern.compile(regex3);
        Matcher matcher3 = pattern3.matcher(tmp2);
        String tmp3 = matcher3.replaceAll(" ");
        tmp3.trim ();
        return tmp3;

    }

}
