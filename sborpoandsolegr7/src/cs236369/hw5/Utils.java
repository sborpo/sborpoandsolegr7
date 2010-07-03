package cs236369.hw5;

public class Utils {
	
	public static ErrorInfoBean notSupported()
	{   ErrorInfoBean err = new ErrorInfoBean();
		err.setLink("javascript:history.back(1);"); 
		err.setLinkStr("Try again");
		err.setErrorString("Parameters Error");
		err.setReason("You must fill the mandatory fields");
		return err;
	}

}
