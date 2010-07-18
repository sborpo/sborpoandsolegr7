package cs236369.hw5.users;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import com.octo.captcha.module.servlet.image.SimpleImageCaptchaServlet;

import cs236369.hw5.DeafaultManipulator;
import cs236369.hw5.ErrorInfoBean;
import cs236369.hw5.User;
import cs236369.hw5.Utils;
import cs236369.hw5.User.UserType;
import cs236369.hw5.instrument.InstrumentManager.InstrumentExists;
import cs236369.hw5.users.UserManager.LeaderNotExists;
import cs236369.hw5.users.UserManager.Unauthenticated;
import cs236369.hw5.users.UserManager.UserExists;
import cs236369.hw5.users.UserManager.UserNotExists;

public class UserUtils {
	public static String authorizationStr;
	public static class FileTooBigExp extends Exception{}
	public static class ParametersExp extends Exception { 
		ErrorInfoBean bean;
		public ParametersExp(ErrorInfoBean err ) {
		bean=err;
	}
		public ErrorInfoBean getError(){return bean;}
	}
	
	
	public static boolean isValidEmail(String email)
	{
	      Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
	      Matcher m = p.matcher(email);
	      boolean matchFound = m.matches();
	      if (matchFound){
	        return true;
	      }
	      return false;
	}
	
	
	public static UserType determineUserType(HashMap<String, String> params)
	{
		if (params.get(UserManager.UserTypen).equals("Admin"))
		{
			return UserType.ADMIN;
		}
		else
		{
			return UserType.REASEARCHER;
		}
	}
	
	public static boolean isAdmin(HttpServletRequest request)
	{
		if (request.getUserPrincipal()==null)
		{
			return false;
		}
		String username=request.getUserPrincipal().getName();
		try {
			User user=UserManager.getUserDetails(username);
			if ( (user==null) || (!user.getRole().equals(UserType.ADMIN))) {
				return false;
			}
			return true;
		} catch (SQLException e) {
			return false;
		}
		
	}
	
	
	public static void manipulateUser(HttpServletRequest request, HttpServletResponse response,DeafaultManipulator manipulator) throws IOException,  InstrumentExists, Utils.ParametersExp
	{
		
		HashMap<String, String> params = new HashMap<String, String>();
		ErrorInfoBean err = new ErrorInfoBean();
		//TODO: err
		manipulator.returnLinkSetter(err);
		try{
		SerialBlob imageBlob = UserUtils.handleParameters(request, params);
		manipulator.paramsChecker(params, err);
		manipulator.authenticate(params, request, response);
		UserType databaseUserType=UserUtils.determineUserType(params);
		String userCaptchaResponse = params.get(UserManager.Captcha);
		boolean captchaPassed = SimpleImageCaptchaServlet.validateResponse(request, userCaptchaResponse);
		if(captchaPassed){
			manipulator.manipulate(params, imageBlob, databaseUserType);
			Utils.forwardToSuccessPage("/sborpoandsolegr7/indexLoged.jsp", request, response);
			return;
		}
		err.setErrorString("Catchpa Error");
		err.setReason("The string that you have typed in the catchpa text box is wrong");
		}
		catch (UserUtils.FileTooBigExp ex)
		{
    		err.setErrorString("File Uploading Error");
    		err.setReason("The file that you have tried to upload is biggger than "+UserManager.FileSizeInBytes/1000+" KB");
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
			
		}catch (UserNotExists e) {
		err.setErrorString("User Error");
		err.setReason("The username that you have tried to add already <br/> exists in the system.");
		} catch (LeaderNotExists e) {
			err.setErrorString("Group Error");
			err.setReason("The group leader that you have supplied , no longer exists in the system. <br/>" +
					"He might have been transfered to different group.");
		} catch (Unauthenticated e) {
			response.sendError(501);
			return;
		}
		 catch (Utils.ParametersExp e) {
			
		}
		Utils.forwardToErrorPage(err,request,response);
	}
	
	
	
	 public static boolean containsOnlyNumbers(String str) {
	        
	        //It can't contain only numbers if it's null or empty...
	        if (str == null || str.length() <9)
	            return false;
	        
	        for (int i = 0; i < str.length(); i++) {

	            //If we find a non-digit character we return false.
	            if (!Character.isDigit(str.charAt(i)))
	                return false;
	        }
	        
	        return true;
	    }
	public static SerialBlob handleParameters(HttpServletRequest request,	HashMap<String, String> params) throws FileTooBigExp, FileUploadException, IOException, SerialException, SQLException
	{
		SerialBlob imageBlob = null;
		ServletFileUpload upload = new ServletFileUpload();
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
		        		byteStream.close();
		        		throw new FileTooBigExp ();
		        	}
		        	byteStream.write((char) c);
		        	length++;
		        	}
		        byte [] image= byteStream.toByteArray();
		        if (image.length==0)
		        {
		        	imageBlob=null;
		        }
		        else
		        {
		        	imageBlob= new SerialBlob(image);
		        }
		    } 
	}
		return imageBlob;
}
}
