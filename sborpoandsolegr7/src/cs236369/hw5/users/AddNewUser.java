package cs236369.hw5.users;

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
import cs236369.hw5.Utils;
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
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ErrorInfoBean err= Utils.notSupported();
		UserUtils.forwardToErrorPage(err,request,response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		SerialBlob imageBlob=null;
		HashMap<String, String> params = new HashMap<String, String>();
		ErrorInfoBean err = new ErrorInfoBean();
		err.setLink("addUser.jsp"); err.setLinkStr("Try again");
		try{
		UserUtils.handleParameters(request, params, imageBlob);
		addUserCheckParameters(params,err);
		UserType databaseUserType=UserUtils.determineUserType(params);
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
		catch (UserUtils.FileTooBigExp ex)
		{
    		err.setErrorString("File Uploading Error");
    		err.setReason("The file that you have tried to upload is biggger than "+UserManager.FileSizeInBytes/1000+" KB");
    		UserUtils.forwardToErrorPage(err,request,response);
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
		} catch (UserUtils.ParametersExp e) {
			
		}
		UserUtils.forwardToErrorPage(err,request,response);

		
	}
	


	private void addUserCheckParameters(HashMap<String, String> params,ErrorInfoBean err) throws UserUtils.ParametersExp {
		if (!(params.containsKey(UserManager.Usern)&&params.containsKey(UserManager.Password)&&params.containsKey(UserManager.PassConfirm)&&params.containsKey(UserManager.Group)&&params.containsKey(UserManager.UserTypen)&&params.containsKey(UserManager.Name)&&params.containsKey(UserManager.Captcha)))
		{
			err.setErrorString("Parameters Error");
			err.setReason("You must fill the mandatory fields");
			throw new UserUtils.ParametersExp(err);
		}
		if (!(params.get(UserManager.Password).equals(params.get(UserManager.PassConfirm))))
			{
				err.setErrorString("Password Error");
				err.setReason("The password and the confirmation password don't match");
				throw new UserUtils.ParametersExp(err);
			}
		if (params.containsKey(UserManager.PhoneNumber))
		{
			if (!(UserUtils.containsOnlyNumbers(params.get(UserManager.PhoneNumber))))
			{
				err.setErrorString("Phone Number Error");
				err.setReason("The phone number should contain only numbers");
				throw new UserUtils.ParametersExp(err);
			}
		}
		else
		{
			params.put(UserManager.PhoneNumber, null);
		}
		if (!params.containsKey(UserManager.Address))
		{
			params.put(UserManager.Address, null);
		}
		if ((!(params.get(UserManager.UserTypen).equals("Admin"))) && (!(params.get(UserManager.UserTypen).equals("Researcher"))))
		{
			err.setErrorString("User Type Error");
			err.setReason("The user type should be only Reasearcher or Admin");
			throw new UserUtils.ParametersExp(err);
		}
		//TODO : check if group leader is on
		

	}

	

}
