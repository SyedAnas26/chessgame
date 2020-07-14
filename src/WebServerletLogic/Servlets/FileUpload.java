package WebServerletLogic.Servlets;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


public class FileUpload extends HttpServlet {
  
    private final String UPLOAD_DIRECTORY = "\\FileUploads";
    String FileName;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tomPath = request.getServletContext().getRealPath("");
        File Folder = new File(tomPath+UPLOAD_DIRECTORY);
        FileUtils.deleteDirectory(Folder);
        createFolder(Folder);
        HttpSession session = request.getSession();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                List<FileItem> multiparts = new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(request);

                for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                        FileName = new File(item.getName()).getName();
                        File file = new File(tomPath+UPLOAD_DIRECTORY + File.separator + FileName);
                        item.write(file);
                    }
                }

                session.setAttribute("filename", FileName);
                session.setAttribute("NewOrOld", "New");
                out.print("<html>");
                out.print("<head>");
                out.print("</head>");
                out.print("<body>");
                out.print("<h1 style=\"font-family:verdana;\" align=\"center\" >Your file successfully uploaded  </h1>");
                out.print("<form align=\"center\" action=\"Play\" method=\"post\">\n    <input  type=\"submit\" value=\"Click Here to Watch Game\"/>");
                out.print("</form>");
                out.println("<br>");
                out.println("<form align=\"center\"  action=\"/logout\" method=\"post\" >");
                out.println("<input type=\"submit\" value=\"Logout\">");
                out.println("</form>");
                out.print("</body>");
                out.print("</html>");

            } catch (Exception ex) {
                out.printf("File not Uploaded Successfully due to %s", ex);
            }

        } else {
            out.println("Sorry this Servlet only handles file upload request");
        }

    }

    private void createFolder(File folder) {
        boolean bool = folder.mkdir();
        if (bool) {
            System.out.println("Directory created successfully");
        } else {
            System.out.println("Sorry couldn’t create specified directory");
        }

    }
}