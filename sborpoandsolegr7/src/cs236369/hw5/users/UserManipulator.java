package cs236369.hw5.users;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import cs236369.hw5.ErrorInfoBean;
import cs236369.hw5.User.UserType;
import cs236369.hw5.users.UserManager.UserExists;
import cs236369.hw5.users.UserManager.UserNotExists;
import cs236369.hw5.users.UserUtils.ParametersExp;

public interface UserManipulator {
	
	public void manipulate(HashMap<String, String> params,Blob imageBlob,UserType databaseUserType ) throws UserExists,UserNotExists,SQLException;
	public void paramsChecker(HashMap<String, String> params , ErrorInfoBean err) throws ParametersExp;
	public void returnLinkSetter(ErrorInfoBean err);
}
