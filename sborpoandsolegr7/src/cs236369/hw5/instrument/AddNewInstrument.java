package cs236369.hw5.instrument;


import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import com.octo.captcha.module.servlet.image.SimpleImageCaptchaServlet;

/**
 * Servlet implementation class AddNewUser
 */
public class AddNewInstrument extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddNewInstrument() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServletFileUpload upload = new ServletFileUpload();

		// Parse the request
		try{
		FileItemIterator iter = upload.getItemIterator(request);
		while (iter.hasNext()) {
		    FileItemStream item = iter.next();
		    String name = item.getFieldName();
		    InputStream stream = item.openStream();
		    if (item.isFormField()) {
		        System.out.println("Form field " + name + " with value "
		            + Streams.asString(stream) + " detected.");
		        //TODO
		    } else {
		        System.out.println("File field " + name + " with file name "
		            + item.getName() + " detected.");
		        // Process the input stream
		        
		    }
		}
		}
		catch (FileUploadException ex)
		{
			//TODO: problem with file upload
			ex.printStackTrace();
		}
		
		
		String userCaptchaResponse = request.getParameter("jcaptcha");
		boolean captchaPassed = SimpleImageCaptchaServlet.validateResponse(request, userCaptchaResponse);
		if(captchaPassed){
			response.getWriter().println("Corret");
		}else{
			response.getWriter().println("wrong");
		}
	}

}
