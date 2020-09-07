package WebServerletLogic.Servlets.GameServlets;

import GameLogic.Managers.PlayPgnFileManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;

@MultipartConfig(maxFileSize = 16177215)
public class FileUploadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws  IOException {
        PlayPgnFileManager playPgnFileManager = new PlayPgnFileManager();
        HttpSession session = request.getSession();
        int uniqueId = (int) session.getAttribute("uniqueId");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        if (request.getServletPath().equals("/setFileName")) {
            String fileName = request.getParameter("fileName");
            session.setAttribute("fileName",fileName);
        } else  {
            try {
                String fileName =(String) session.getAttribute("fileName");
                // obtains the upload file part in this multipart request
                InputStream inputStream = null; // input stream of the upload file
                Part filePart = request.getPart("file");
                if (filePart != null) {
                    inputStream = filePart.getInputStream();
                }
                assert inputStream != null;
                Scanner s = new Scanner(inputStream).useDelimiter("\\A");
                String gamePlay = s.hasNext() ? s.next() : "";
                int idPgnLog = playPgnFileManager.storePgn(gamePlay, uniqueId, fileName);
                String url="/PlayPgn?id="+uniqueId+"&pgnlog="+idPgnLog;
                response.sendRedirect(url);
            } catch (Exception ex) {
                System.out.println("Exception in FileUpload Servlet "+ex);
                System.out.println("Line No "+ex.getStackTrace()[0].getLineNumber());
                out.println("<script type=\"text/javascript\">");
                out.println("alert('You Have not uploaded a File !');");
                out.println("location='/load';");
                out.println("</script>");

            }
        }
    }
}