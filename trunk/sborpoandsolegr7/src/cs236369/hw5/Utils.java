package cs236369.hw5;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialException;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import com.octo.captcha.module.servlet.image.SimpleImageCaptchaServlet;

import cs236369.hw5.ReservationManager.ReservationOverlapingException;
import cs236369.hw5.instrument.InstrumentManager;
import cs236369.hw5.instrument.InstrumentManager.InstrumentExists;
import cs236369.hw5.instrument.InstrumentManager.InstrumentNotExists;
import cs236369.hw5.users.UserManager;
import cs236369.hw5.users.UserUtils;
import cs236369.hw5.users.UserManager.LeaderNotExists;
import cs236369.hw5.users.UserManager.UserExists;
import cs236369.hw5.users.UserManager.UserNotExists;
import cs236369.hw5.users.UserUtils.FileTooBigExp;

public class Utils {

	private static Utils utils;
	public static  String supportMail;
	public static int redirectDelay;
	
	
	
	public static ErrorInfoBean notSupported() {
		ErrorInfoBean err = new ErrorInfoBean();
		err.setLink("javascript:history.back(1);");
		err.setLinkStr("Try again");
		err.setErrorString("Parameters Error");
		err.setReason("You must fill the mandatory fields");
		return err;
	}
	
	
	public static Utils getInstance()
	{
		if (utils==null)
		{
			utils = new Utils();
		}
		return utils;
	}

	public static class ParametersExp extends Exception {
		ErrorInfoBean bean;

		public ParametersExp(ErrorInfoBean err) {
			bean = err;
		}

		public ErrorInfoBean getError() {
			return bean;
		}
	}

	public static boolean containsOnlyLeters(String string) {
		return string.contains("a-zA-Z ");
	}

	public static void manipulateInstrument(HttpServletRequest request,
			HttpServletResponse response, DeafaultManipulator manipulator) throws IOException, Utils.ParametersExp, UserUtils.ParametersExp, InstrumentNotExists {
		HashMap<String, String> params = new HashMap<String, String>();
		ErrorInfoBean err = new ErrorInfoBean();
		// TODO: err
		manipulator.returnLinkSetter(err);
		try {
			Utils.handleParameters(request, params);
			manipulator.paramsChecker(params, err);
			String captchaResponse = params.get(InstrumentManager.Captcha);
			boolean captchaPassed = SimpleImageCaptchaServlet.validateResponse(
					request, captchaResponse);
			if (captchaPassed) {
				manipulator.manipulate(params, null, null);
				Utils.forwardToSuccessPage("viewInstruments.jsp", request,response);
				return;
			}
			err.setErrorString("Catchpa Error");
			err
					.setReason("The string that you have typed in the catchpa text box is wrong");
		} catch (UserUtils.FileTooBigExp ex) {
			err.setErrorString("File Uploading Error");
			err
					.setReason("The file that you have tried to upload is biggger than "
							+ UserManager.FileSizeInBytes / 1000 + " KB");
			Utils.forwardToErrorPage(err, request, response);
		} catch (FileUploadException ex) {
			err.setErrorString("File Uploading Error");
			err.setReason("There was a problem uploading you file");
			ex.printStackTrace();
		} catch (SQLException e) {
			err.setErrorString("Database Error");
			err.setReason("There was a problem accessing the database.");
			e.printStackTrace();
		} catch (UserExists e) {
		} catch (UserNotExists e) {
		} catch (Utils.ParametersExp e) {
		} catch (LeaderNotExists e) {
		} catch (InstrumentExists e) {
			err.setErrorString("Instrument Error");
			err.setReason("The id that you have tried to add already <br/> exists in the system.");
			e.printStackTrace();
		} catch (ReservationOverlapingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Utils.forwardToErrorPage(err, request, response);

	}

	public static void handleParameters(HttpServletRequest request,
			HashMap<String, String> params)
			throws FileTooBigExp, FileUploadException, IOException,
			SerialException, SQLException {
		ServletFileUpload upload = new ServletFileUpload();
		FileItemIterator iter = upload.getItemIterator(request);
		while (iter.hasNext()) {
			FileItemStream item = iter.next();
			String name = item.getFieldName();
			InputStream stream = item.openStream();
			if (item.isFormField()) {
				String str = Streams.asString(stream);
				System.out.println(str);
				params.put(name, str);
			}
		}

	}

	public static void forwardToSuccessPage(String link,HttpServletRequest request,HttpServletResponse response)
	{
		request.getSession().setAttribute("successPage", link);
		RequestDispatcher rd = request.getRequestDispatcher("successPage.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			try {
				response.sendError(500);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (IOException e) {
			try {
				response.sendError(500);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	public static void forwardToErrorPage(ErrorInfoBean err,HttpServletRequest request,HttpServletResponse response) {

		request.setAttribute("ErrorInfoBean", err);
		RequestDispatcher rd = request.getRequestDispatcher("/errorPages/errorPage.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			try {
				response.sendError(500);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (IOException e) {
			try {
				response.sendError(500);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}


	public static void manipulateReservation(HttpServletRequest request,
			HttpServletResponse response, DeafaultManipulator manipulator) throws InstrumentNotExists, FileTooBigExp, FileUploadException, IOException {
		HashMap<String, String> params = new HashMap<String, String>();
		ErrorInfoBean err = new ErrorInfoBean();
		// TODO: err
		manipulator.returnLinkSetter(err);
		try {	
				handleParameters(request, params);
				manipulator.paramsChecker(params, err);
				manipulator.manipulate(params, null, null);
				Utils.forwardToSuccessPage("viewInstruments.jsp", request,response);
				return;
		} catch (SQLException e) {
			err.setErrorString("Database Error");
			err.setReason("There was a problem accessing the database.");
			e.printStackTrace();
		} catch (UserExists e) {
		} catch (UserNotExists e) {
		} catch (Utils.ParametersExp e) {
		} catch (LeaderNotExists e) {
		} catch (InstrumentExists e) {
			err.setErrorString("Instrument Error");
			err.setReason("The id that you have tried to add already <br/> exists in the system.");
			e.printStackTrace();
		} catch (ReservationOverlapingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Utils.forwardToErrorPage(err, request, response);
	}


}
