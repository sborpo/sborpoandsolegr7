package cs236369.hw5.instrument;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs236369.hw5.ErrorInfoBean;
import cs236369.hw5.Utils;
import cs236369.hw5.instrument.InstrumentManager.InstrumentNotExists;

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
		String id=request.getParameter("id");
		ErrorInfoBean err = new ErrorInfoBean();
		err.setLinkStr("Try Again");
		if (id==null)
		{
			err.setLink("viewInstruments.jsp");
			err.setErrorString("Parameter Error");
			err.setReason("The username parameter wasn't specified");
		}
		err.setLink("viewInstrument.jsp?id="+id);
		try {
			InstrumentManager.removeInstrument(id);
			Utils.forwardToSuccessPage("viewInstruments.jsp", request, response);
			return;
		} catch (SQLException e) {
			err.setErrorString("Database Error");
			err.setReason("There was a problem accessing the database.");
			e.printStackTrace();
		} catch (NumberFormatException e) {
			err.setErrorString("Instrument Error");
			err.setReason("Enter ID in digits");
			e.printStackTrace();
		} catch (InstrumentNotExists e) {
			err.setErrorString("Instrument Error");
			err.setReason("There was a problem accessing the database.");
			e.printStackTrace();
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
