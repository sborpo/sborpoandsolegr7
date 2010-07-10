package cs236369.hw5;

import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs236369.hw5.Utils.ParametersExp;
import cs236369.hw5.instrument.InstrumentManager.InstrumentExists;
import cs236369.hw5.users.UserManager;
import cs236369.hw5.users.UserManager.AuthenticationMode;
import cs236369.hw5.users.UserManager.LeaderNotExists;
import cs236369.hw5.users.UserManager.UserExists;
import cs236369.hw5.users.UserManager.UserNotExists;

public interface DeafaultManipulator  {
	
	public void manipulate(HashMap<String, String> params,Object inage,Object type) throws UserExists, UserNotExists, SQLException, InstrumentExists, LeaderNotExists;
	public void paramsChecker(HashMap<String, String> params , ErrorInfoBean err) throws Utils.ParametersExp;
	public void returnLinkSetter(ErrorInfoBean err);
	public void authenticate(HashMap<String, String> params,HttpServletRequest request,HttpServletResponse respone) throws UserManager.Unauthenticated;
}
