package WebServerletLogic;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

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
    public final String UPLOAD_DIRECTORY = "C:\\Users\\User\\Desktop\\Saddique\\apache-tomcat-9.0.36\\webapps\\ROOT\\FileUploads";
   String FileName;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        if(ServletFileUpload.isMultipartContent(request)){
            try {
                List<FileItem> multiparts = new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(request);

                for(FileItem item : multiparts){
                    if(!item.isFormField()){
                         FileName = new File(item.getName()).getName();
                         request.setAttribute("filename",FileName);
                        File file = new File(UPLOAD_DIRECTORY + File.separator + FileName);
                        item.write(file);

                    }
                }
                HttpSession session = request.getSession();
                session.setAttribute("filename",FileName);

                out.print("<html>");
                out.print("<head>");
                out.print("</head>");
                out.print("<body>");
                out.print("<h1 style=\"font-family:verdana;\" align=\"center\" >Your file successfully uploaded  </h1>");
                out.print("<form align=\"center\" action=\"Play\" method=\"post\">\n    <input  type=\"submit\" value=\"Click Here to Watch Game\"/>");
                out.print("</form>");
                out.print("</body>");
                out.print("</html>");
            } catch (Exception ex) {
                out.printf("File not Uploaded Successfully due to %s",ex);
            }

        }else{
            out.println("Sorry this Servlet only handles file upload request");
        }
    }

}
