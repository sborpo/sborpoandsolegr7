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
	public static class ParametersExp extends Exception { 
		ErrorInfoBean bean;
		public ParametersExp(ErrorInfoBean err ) {
		bean=err;
	}
		public ErrorInfoBean getError(){return bean;}
	}
       
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
		ErrorInfoBean err = new ErrorInfoBean();
		err.setLink("addUser.jsp"); err.setLinkStr("Try again");
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
		        int length=0;
		        while ((c = stream.read()) != -1) {
		        	//if the file size is too big
		        	if ((length>UserManager.FileSizeInBytes))
		        	{
		        		err.setErrorString("File Uploading Error");
		        		err.setReason("The file that you have tried to upload is biggger than "+UserManager.FileSizeInBytes/1000+" KB");
		        		byteStream.close();
		        		forwardToErrorPage(err,request,response);
		        	}
		        	byteStream.write((char) c);
		        	length++;
		        	}
		        byte [] image= byteStream.toByteArray();
		        imageBlob= new SerialBlob(image);
		    } 
		}
		checkParameters(params,err);
		
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
		if(captchaPassed){
		
			UserManager.AddUser(params.get(UserManager.Usern), params.get(UserManager.Password), params.get(UserManager.Group), "", params.get(UserManager.Name), params.get(UserManager.PhoneNumber), params.get(UserManager.Address), imageBlob, databaseUserType);
			response.getWriter().println("Success");
			return;
		}
		err.setErrorString("Catchpa Error");
		err.setReason("The string that you have typed in the catchpa text box is wrong");
		}
		catch (FileUploadException ex)
		{

			err.setErrorString("File Uploading Error");
    		err.setReason("There was a problem uploading you file");
			ex.printStackTrace();
		} catch (SQLException e) {
			err.setErrorString("Database Error");
			err.setReason("There was a problem accessing the database.");
			e.printStackTrace();
		} catch (UserExists e) {
			err.setErrorString("User Error");
			err.setReason("The username that you have tried to add already <br/> exists in the system.");
		} catch (ParametersExp e) {
			
		}
		forwardToErrorPage(err,request,response);

		
	}
	
	 public boolean containsOnlyNumbers(String str) {
	        
	        //It can't contain only numbers if it's null or empty...
	        if (str == null || str.length() == 0)
	            return false;
	        
	        for (int i = 0; i < str.length(); i++) {

	            //If we find a non-digit character we return false.
	            if (!Character.isDigit(str.charAt(i)))
	                return false;
	        }
	        
	        return true;
	    }

	private void checkParameters(HashMap<String, String> params,ErrorInfoBean err) throws ParametersExp {
		if (!(params.containsKey(UserManager.Usern)&&params.containsKey(UserManager.Password)&&params.containsKey(UserManager.PassConfirm)&&params.containsKey(UserManager.Group)&&params.containsKey(UserManager.UserTypen)&&params.containsKey(UserManager.Name)&&params.containsKey(UserManager.Captcha)))
		{
			err.setErrorString("Parameters Error");
			err.setReason("You must fill the mandatory fields");
			throw new ParametersExp(err);
		}
		if (!(params.get(UserManager.Password).equals(params.get(UserManager.PassConfirm))))
			{
				err.setErrorString("Password Error");
				err.setReason("The password and the confirmation password don't match");
				throw new ParametersExp(err);
			}
		if (params.containsKey(UserManager.PhoneNumber))
		{
			if (!(containsOnlyNumbers(params.get(UserManager.PhoneNumber))))
			{
				err.setErrorString("Phone Number Error");
				err.setReason("The phone number should contain only numbers");
				throw new ParametersExp(err);
			}
		}
		else
		{
			params.put(UserManager.PhoneNumber, null);
		}
		if ((!(params.get(UserManager.UserTypen).equals("Admin"))) && (!(params.get(UserManager.UserTypen).equals("Researcher"))))
		{
			err.setErrorString("User Type Error");
			err.setReason("The user type should be only Reasearcher or Admin");
			throw new ParametersExp(err);
		}
		//TODO : check if group leader is on
		

	}

	private void forwardToErrorPage(ErrorInfoBean err,HttpServletRequest request,HttpServletResponse response) {

		request.setAttribute("ErrorInfoBean", err);
		RequestDispatcher rd = request.getRequestDispatcher("/errorPages/errorPage.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			try {
				response.sendError(500);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (IOException e) {
			try {
				response.sendError(500);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}

}
