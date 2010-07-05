package cs236369.hw5.users;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs236369.hw5.ErrorInfoBean;
import cs236369.hw5.users.SendMail.SendingMailError;
import cs236369.hw5.users.UserManager.UserNotExists;
import cs236369.hw5.users.UserUtils.ParametersExp;

/**
 * Servlet implementation class ResetPassword
 */
public class ResetPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResetPassword() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username=request.getParameter(UserManager.Usern);
		ErrorInfoBean err = new ErrorInfoBean();
		err.setLinkStr("Main Page");
		err.setLink("javascript:history.back(1)");
		err.setErrorString("Parameter Error");
		try{
		if (username==null)
		{
			err.setReason("The username parameter wasn't specified");
			throw new UserUtils.ParametersExp(err);
		}
		String newPass=request.getParameter(UserManager.Password);
		String passConfirm=request.getParameter(UserManager.PassConfirm);
		if ((newPass==null) || (passConfirm==null))
		{
			err.setReason("The password or the confirmation password were not specified");
			throw new UserUtils.ParametersExp(err);
		}
		err.setLinkStr("Back");
		UserManager.resetPassword(username,newPass);
		UserUtils.forwardToSuccessPage("updateUser.jsp?username="+username, request, response);
			return;
		} catch (UserNotExists e) {
			err.setErrorString("User Error");
			err.setReason("The username that you specified not exists");
		} catch (SQLException e) {
			err.setErrorString("Database Error");
			err.setReason("There was a problem accessing the database.");
			e.printStackTrace();
		} catch (ParametersExp e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		UserUtils.forwardToErrorPage(err,request,response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
