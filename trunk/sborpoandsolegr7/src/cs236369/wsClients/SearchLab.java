package cs236369.wsClients;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs236369.hw5.RegistrationServiceStub;
import cs236369.hw5.SearchWSStub;
import cs236369.hw5.TimeSlot;
import cs236369.hw5.RegistrationServiceStub.AddEndpoint;
import cs236369.hw5.RegistrationServiceStub.AddEndpointResponse;
import cs236369.hw5.SearchWSStub.Search;
import cs236369.hw5.SearchWSStub.SearchResponse;
import cs236369.wsClients.YellowPagesRegistrator.YelloPageError;

/**
 * Servlet implementation class SearchLab
 */
public class SearchLab extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchLab() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    private void printTag(String elem,String value,PrintWriter writer)
    {
    	writer.write("<"+elem+">"+value+"</"+elem+">");
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
		if ((request.getParameter("labname")==null) || (request.getParameter("keywords")==null) || request.getParameter("slots")==null)
		{
			throw new Exception();
		}
		String wsdlUrl=request.getParameter("labname");
		String [] keywords=request.getParameter("keywords").split(" ");
		int k = Integer.parseInt(request.getParameter("slots"));
		if (k>TimeSlot.MAX_K)
		{
			throw new Exception();
		}
		
			SearchWSStub stub = new SearchWSStub(wsdlUrl);
			Search search = new Search();
			search.setK(k);
			search.setKeywords(keywords);
			SearchResponse resp=stub.search(search);
			String [] answ=resp.get_return();
			//send XML response
			if ((answ==null) || (answ.length==0))
			{
				throw new Exception();
			}
			PrintWriter writer=response.getWriter();
			writer.write("<slots>");
			for (String avSlot : answ) {
				// labname || instid || (year,month,day,slot)
				writer.write("<slotElem>");
				String [] parsedComp= avSlot.split("\\|\\|");
				printTag("labName",parsedComp[0],writer);
				printTag("instid",parsedComp[1],writer);
				printTag("timeSlot",parsedComp[2],writer);
				writer.write("</slotElem>");
			}
			writer.write("</slots>");
			
			}
			catch (Exception e)
			{
				if (!response.isCommitted())
				{
					response.setStatus(500);
				}
			}
		
	}

}
