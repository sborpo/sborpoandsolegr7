package cs236369.hw5.instrument;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs236369.hw5.ErrorInfoBean;
import cs236369.hw5.Utils;
import cs236369.hw5.users.UserManager;
import cs236369.hw5.users.UserManager.TryDeleteAdmin;
import cs236369.hw5.users.UserManager.UserNotExists;

/**
 * Servlet implementation class DeleteInstrument
 */
public class DeleteInstrument extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteInstrument() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username=request.getParameter("id");
		ErrorInfoBean err = new ErrorInfoBean();
		err.setLinkStr("Try Again");
		if (username==null)
		{
			err.setLink("viewUsers.jsp");
			err.setErrorString("Parameter Error");
			err.setReason("The username parameter wasn't specified");
		}
		err.setLink("viewUser.jsp?username="+username);
		try {
			UserManager.removeUser(username);
			Utils.forwardToSuccessPage("viewUsers.jsp", request, response);
			return;
		} catch (UserNotExists e) {
			err.setErrorString("User Error");
			err.setReason("The username that you tried to remove doesn't exists");
		} catch (SQLException e) {
			err.setErrorString("Database Error");
			err.setReason("There was a problem accessing the database.");
			e.printStackTrace();
		} catch (TryDeleteAdmin e) {
			err.setErrorString("Permission Error");
			err.setReason("You don't have the permission to delete an administrator");
		}
		Utils.forwardToErrorPage(err,request,response);
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
