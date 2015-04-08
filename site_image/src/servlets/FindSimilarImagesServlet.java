package servlets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
 
@WebServlet(name="find",urlPatterns={"/find"})
@MultipartConfig(maxFileSize = 16177215)    // upload file's size up to 16MB
public class FindSimilarImagesServlet extends HttpServlet {
	private final static Logger LOGGER = 
            Logger.getLogger(FindSimilarImagesServlet.class.getCanonicalName());
	 
   
	File destinationDir = new File("/tmp");
	
	
	
	protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // gets values of text fields
        String nom = request.getParameter("nom");
                  String message;
               // Create path components to save the file
                  final String path="data";
                  final Part filePart = request.getPart("image");
                  final String fileName =getFileName(filePart);
                  OutputStream out = null;
                  InputStream filecontent = null;
                  final PrintWriter writer = response.getWriter();

                   
       ObjetImage image;; 
         SaveImageToDatabase sitdb=new SaveImageToDatabase();
		try {
			
			  
			 
			  
			out = new FileOutputStream(new File(destinationDir + File.separator
                + fileName));
			
			filecontent = filePart.getInputStream();

	        int read = 0;
	        final byte[] bytes = new byte[1024];

	        while ((read = filecontent.read(bytes)) != -1) {
	            out.write(bytes, 0, read);
	        }
	        writer.println("New file " + fileName + " created at " + destinationDir);
	       
	        
	        LOGGER.log(Level.INFO, "File{0}being uploaded to {1}", 
	                new Object[]{fileName, destinationDir});

	        try { 
	        	image=new ObjetImage(destinationDir+"/"+fileName);
	        	 
			 sitdb.trouverSimilaire(image); 
				
		       // writer.println(" Félicitation !!!!image charger dans la base " );

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	// message = "File uploaded and saved into database";
           
		} catch (FileNotFoundException fne) {
	        writer.println("You either did not specify a file to upload or are "
	                + "trying to upload a file to a protected or nonexistent "
	                + "location.");
	        writer.println("<br/> ERROR: " + fne.getMessage());

	        LOGGER.log(Level.SEVERE, "Problems during file upload. Error: {0}", 
	                new Object[]{fne.getMessage()});
	    } finally {
	        if (out != null) {
	            out.close();
	        }
	        if (filecontent != null) {
	            filecontent.close();
	        }
	        if (writer != null) {
	            writer.close();
	        }
	    }
	}
         
    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    } }
 