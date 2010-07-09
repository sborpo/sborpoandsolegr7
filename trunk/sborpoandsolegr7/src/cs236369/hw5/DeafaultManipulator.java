package cs236369.hw5;

import java.sql.SQLException;
import java.util.HashMap;

import cs236369.hw5.Utils.ParametersExp;
import cs236369.hw5.instrument.InstrumentManager.InstrumentExists;
import cs236369.hw5.users.UserManager.UserExists;
import cs236369.hw5.users.UserManager.UserNotExists;

public interface DeafaultManipulator  {
	
	public void manipulate(HashMap<String, String> params,Object inage,Object type) throws UserExists, UserNotExists, SQLException, InstrumentExists;
	public void paramsChecker(HashMap<String, String> params , ErrorInfoBean err) throws ParametersExp, cs236369.hw5.users.UserUtils.ParametersExp;
	public void returnLinkSetter(ErrorInfoBean err);
}
