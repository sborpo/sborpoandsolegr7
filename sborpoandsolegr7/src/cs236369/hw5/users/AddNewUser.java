package cs236369.hw5.users;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import com.octo.captcha.module.servlet.image.SimpleImageCaptchaServlet;

import cs236369.hw5.ErrorInfoBean;
import cs236369.hw5.User.UserType;
import cs236369.hw5.users.UserManager.UserExists;

/**
 * Servlet implementation class AddNewUser
 */
public class AddNewUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddNewUser() {
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
		SerialBlob imageBlob=null;
		HashMap<String, String> params = new HashMap<String, String>();
		try{
		FileItemIterator iter = upload.getItemIterator(request);
		while (iter.hasNext()) {
		    FileItemStream item = iter.next();
		    String name = item.getFieldName();
		    InputStream stream = item.openStream();
		    if (item.isFormField()) {
		    	String str=Streams.asString(stream);
		    	System.out.println(str);
		    		params.put(name, str );
		    } else {
		        // Process the input stream
		        ByteArrayOutputStream byteStream= new ByteArrayOutputStream();
		        int c;
		        while ((c = stream.read()) != -1) {
		        	byteStream.write((char) c);
		        	}
		        byte [] image= byteStream.toByteArray();
		        imageBlob= new SerialBlob(image);
		        
		        
		    } 
		}
		UserType databaseUserType;
		if (params.get(UserManager.UserTypen).equals("Admin"))
		{
			databaseUserType=UserType.ADMIN;
		}
		else
		{
			databaseUserType=UserType.REASEARCHER;
		}
		
		String userCaptchaResponse = params.get(UserManager.Captcha);
		boolean captchaPassed = SimpleImageCaptchaServlet.validateResponse(request, userCaptchaResponse);
		if(!captchaPassed)
		{
			ErrorInfoBean err = new ErrorInfoBean();
			err.setErrorString("Catchpa Error");
			err.setReason("The string that you have typed in the catchpa text box is wrong");
			err.setLinkStr("try agin");
			err.setLink("addUser.jsp");
			request.setAttribute("ErrorInfoBean", err);
			RequestDispatcher rd = request.getRequestDispatcher("/errorPages/errorPage.jsp");
			rd.forward(request, response);
		}
		UserManager.AddUser(params.get(UserManager.Usern), params.get(UserManager.Password), params.get(UserManager.Group), "", params.get(UserManager.Name), params.get(UserManager.PhoneNumber), params.get(UserManager.Address), imageBlob, databaseUserType);
		response.getWriter().println("Success");
		}
		catch (FileUploadException ex)
		{
			//TODO: problem with file upload
			ex.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserExists e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
