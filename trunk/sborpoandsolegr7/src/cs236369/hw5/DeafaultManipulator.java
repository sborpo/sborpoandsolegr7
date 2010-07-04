package cs236369.hw5;

import java.sql.SQLException;
import java.util.HashMap;

import cs236369.hw5.users.UserManager.UserExists;
import cs236369.hw5.users.UserManager.UserNotExists;
import cs236369.hw5.users.UserUtils.ParametersExp;

public interface DeafaultManipulator  {
	
	public void manipulate(HashMap<String, String> params,Object inage,Object type) throws UserExists, UserNotExists, SQLException;
	public void paramsChecker(HashMap<String, String> params , ErrorInfoBean err) throws ParametersExp;
	public void returnLinkSetter(ErrorInfoBean err);
}
