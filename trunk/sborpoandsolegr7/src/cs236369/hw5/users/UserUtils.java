package cs236369.hw5.users;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;

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

import cs236369.hw5.ErrorInfoBean;
import cs236369.hw5.User.UserType;

public class UserUtils {

	public static class FileTooBigExp extends Exception{}
	public static class ParametersExp extends Exception { 
		ErrorInfoBean bean;
		public ParametersExp(ErrorInfoBean err ) {
		bean=err;
	}
		public ErrorInfoBean getError(){return bean;}
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
	
	
	public static void forwardToErrorPage(ErrorInfoBean err,HttpServletRequest request,HttpServletResponse response) {

		request.setAttribute("ErrorInfoBean", err);
		RequestDispatcher rd = request.getRequestDispatcher("/errorPages/errorPage.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			try {
				response.sendError(500);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (IOException e) {
			try {
				response.sendError(500);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}
	
	 public static boolean containsOnlyNumbers(String str) {
	        
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
	public static void handleParameters(HttpServletRequest request,	HashMap<String, String> params,SerialBlob imageBlob) throws FileTooBigExp, FileUploadException, IOException, SerialException, SQLException
	{
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
}
}
