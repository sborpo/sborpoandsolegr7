package cs236369.hw5;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ReservationTimesSaver
 */
public class ReservationTimesSaver extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReservationTimesSaver() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
		Integer fromIdx= Integer.parseInt(request.getParameter("fromHour"));
		Integer toIdx=Integer.parseInt(request.getParameter("toHour"));
		HttpSession session = request.getSession(true);
		session.setAttribute("fromHour", fromIdx);
		session.setAttribute("toHour", toIdx);
		}
		catch (NumberFormatException ex)
		{
			
		}
	
	}

}
