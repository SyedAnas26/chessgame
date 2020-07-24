package WebServerletLogic.Servlets;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


public class FileUploadServlet extends HttpServlet {

    private final String UPLOAD_DIRECTORY = "\\FileUploads";
    String FileName;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tomPath = request.getServletContext().getRealPath("");
        File Folder = new File(tomPath + UPLOAD_DIRECTORY);
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
                        File file = new File(tomPath + UPLOAD_DIRECTORY + File.separator + FileName);
                        item.write(file);
                    }
                }
                session.setAttribute("filename", FileName);
                session.setAttribute("NewOrOld", "New");
                response.sendRedirect("/fileuploadsuccess");
            } catch (Exception ex) {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('You Have not uploaded a File !');");
                out.println("location='/load';");
                out.println("</script>");
            }

        } else {
            out.println("<script type=\"text/javascript\">");
            out.println("alert('You Have not uploaded a File !');");
            out.println("location='/load';");
            out.println("</script>");
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