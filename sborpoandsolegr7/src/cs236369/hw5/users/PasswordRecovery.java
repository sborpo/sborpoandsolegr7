package cs236369.hw5.users;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs236369.hw5.ErrorInfoBean;
import cs236369.hw5.Utils;
import cs236369.hw5.users.SendMail.SendingMailError;
import cs236369.hw5.users.UserManager.TryDeleteAdmin;
import cs236369.hw5.users.UserManager.UserNotExists;

/**
 * Servlet implementation class PasswordRecovery
 */
public class PasswordRecovery extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PasswordRecovery() {
        super();
    }
    
    private  static  String generatePasswordRecoveryPass(){
	    
	    // generate a semi-random password
	    Random rand = new Random(System.currentTimeMillis());
	    int passwlength = 6 + rand.nextInt(4);
	    StringBuilder sb = new StringBuilder();
	    
	    // generate ascii codes in the range of 48 - 57 (numbers), 65 - 90 (capitals) and 97 - 122 (lowercases)
	    while(sb.length() < passwlength){
	      
	      // 48 + 74 = 122, the maximum value allowed.
	      char chr = (char) (48 + rand.nextInt(74));
	      if((chr >= '0' && chr <= '9') || (chr >= 'A' && chr <= 'Z') || (chr >= 'a' && chr <= 'z')){
	        sb.append(chr);
	      }
	    }
	 
	    return sb.toString();
	  }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username=request.getParameter("username");
		ErrorInfoBean err = new ErrorInfoBean();
		err.setLinkStr("Main Page");
		if (username==null)
		{
			err.setLink("index.jsp");
			err.setErrorString("Parameter Error");
			err.setReason("The username parameter wasn't specified");
		}
		err.setLink("javascript:history.back(1)");
		err.setLinkStr("Back");
		try {
			String newpass= generatePasswordRecoveryPass();
			UserManager.recoverPassword(username,newpass);
			Utils.forwardToSuccessPage("index.jsp", request, response);
			return;
		} catch (UserNotExists e) {
			err.setErrorString("User Error");
			err.setReason("The username that you specified not exists");
		} catch (SQLException e) {
			err.setErrorString("Database Error");
			err.setReason("There was a problem accessing the database.");
			e.printStackTrace();
	} catch (SendingMailError e) {
		err.setErrorString("Mail Sending Error");
		err.setReason("An error occured when trying to send a recovery email.");
			e.printStackTrace();
		}
	Utils.forwardToErrorPage(err,request,response);

	}
}
	
