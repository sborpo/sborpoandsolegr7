package cs236369.hw5.users;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;

import org.apache.commons.fileupload.FileUploadException;

import com.octo.captcha.module.servlet.image.SimpleImageCaptchaServlet;

import cs236369.hw5.ErrorInfoBean;
import cs236369.hw5.Utils;
import cs236369.hw5.User.UserType;
import cs236369.hw5.users.UserManager.UserExists;
import cs236369.hw5.users.UserManager.UserNotExists;

/**
 * Servlet implementation class UpdateUser
 */
public class UpdateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUser() {
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
		//TODO: admin update
		//check if user tries to change it's data , and not someone else
		SerialBlob imageBlob=null;
		HashMap<String, String> params = new HashMap<String, String>();
		ErrorInfoBean err = new ErrorInfoBean();
		err.setLink("viewUser.jsp?username="+params.get(UserManager.Usern)); err.setLinkStr("Try again");
		try{
		UserUtils.handleParameters(request, params, imageBlob);
		updateUserCheckParameters(params,err);
		UserType databaseUserType=UserUtils.determineUserType(params);
		String userCaptchaResponse = params.get(UserManager.Captcha);
		boolean captchaPassed = SimpleImageCaptchaServlet.validateResponse(request, userCaptchaResponse);
		if(captchaPassed){
		
			UserManager.updateUser(params.get(UserManager.Usern), null, params.get(UserManager.Group), null, params.get(UserManager.Name), params.get(UserManager.PhoneNumber), params.get(UserManager.Address), imageBlob, databaseUserType);
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
		} catch (UserNotExists e) {
			err.setErrorString("User Error");
			err.setReason("The username that you have tried to change his details <br/> was removed from system.");
		} catch (UserUtils.ParametersExp e) {
			
		}
		UserUtils.forwardToErrorPage(err,request,response);
	}

	private void updateUserCheckParameters(HashMap<String, String> params,
			ErrorInfoBean err) throws cs236369.hw5.users.UserUtils.ParametersExp {
		if (!(params.containsKey(UserManager.Group)&&params.containsKey(UserManager.UserTypen)&&params.containsKey(UserManager.Name)&&params.containsKey(UserManager.Captcha)))
		{
			err.setErrorString("Parameters Error");
			err.setReason("The mandatory fields should be filled");
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
