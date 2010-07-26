package cs236369.hw5.reporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cs236369.hw5.ErrorInfoBean;
import cs236369.hw5.Utils;
import cs236369.hw5.db.DbManager;
import cs236369.hw5.xslt.XsltTransformer;
import cs236369.hw5.xslt.XsltTransformer.NoXsltStyleInDb;
import cs236369.hw5.xslt.XsltTransformer.TransformationError;

/**
 * Servlet implementation class XMLReportGenerator
 */
public class XMLReportGenerator extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public XMLReportGenerator() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Connection con=null;
		ResultSet set= null;
		ErrorInfoBean err = new ErrorInfoBean();
		String styleId=request.getParameter("styleId");
		String instId=request.getParameter("instId");
		if (!styleId.equals("2"))
		{
			instId=null;
		}
		
		if ( (!styleId.equals("1")) && (!styleId.equals("2")) && (!styleId.equals("3")) )
		{
			err.setErrorString("Parameter Error");
			err.setReason("The parameters are not correct");
			Utils.forwardToErrorPage(err, request, response);
			return;
		}
		if ( styleId.equals("2"))
		{
			
			if ((instId==null) || (instId=="") || (notNumber(instId)))
			{
			err.setErrorString("Parameter Error");
			err.setReason("The parameters are not correct");
			Utils.forwardToErrorPage(err, request, response);
			return;
			}
		}
		try {
			
			
			
			String str=null;
			InputStream xsltInputStream=null;
			if (request.getParameter("styleId").equals("1"))
			{
				str= getServletContext().getRealPath( "generateNumberOfSlotsPerInstrumentPerGroup.xsl");
				File f = new File(str);
				xsltInputStream= new FileInputStream(f);
			}
			if (request.getParameter("styleId").equals("2"))
			{
				str= getServletContext().getRealPath( "groupReservationsByUserGroup.xsl"); 
				File f = new File(str);
				xsltInputStream= new FileInputStream(f);
			}
			if (request.getParameter("styleId").equals("3"))
			{
				//get the uploaded file from the database
				try {
					xsltInputStream= XsltTransformer.getXsltFromDb(request.getUserPrincipal().getName());
				} catch (NoXsltStyleInDb e) {
					err.setErrorString("XSLT File Error");
					err.setReason("You haven't uploaded and XSLT file yet, please do it.");
					Utils.forwardToErrorPage(err, request, response);
					return;
				}
			}
			builder = factory.newDocumentBuilder();

			Document doc = builder.newDocument();
			Element results = doc.createElement("Results");
			doc.appendChild(results);
			System.out.println(doc);
			

			con=DbManager.DbConnections.getInstance().getConnection();
			PreparedStatement report=(!styleId.equals("2"))? con.prepareStatement("select * from reservations") :  con.prepareStatement("select * from reservations where instId=?") ;//TODO: change
			if (styleId.equals("2"))
			{
				report.setLong(1, Long.valueOf(instId));
			}
			set= report.executeQuery();

			ResultSetMetaData rsmd = null;
			rsmd = set.getMetaData();
			
			int colCount;
			colCount = rsmd.getColumnCount();
			while (set.next()) {
				Element row = doc.createElement("Row");
				results.appendChild(row);
				for (int i = 1; i <= colCount; i++) {
					String columnName = rsmd.getColumnName(i);
					Object value = set.getObject(i);
					Element node = doc.createElement(columnName);
					node.appendChild(doc.createTextNode(value.toString()));
					row.appendChild(node);
				}
			}

			err.setErrorString("XSLT Transform Error");
			err.setReason("There was a problem transforming the XML to HTML wil the given XSLT");
			
			XsltTransformer.transform(doc,xsltInputStream, response.getWriter());
			return;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Utils.forwardToErrorPage(err, request, response);
	}

	private boolean notNumber(String instId) {
		try{
		Long.valueOf(instId);
		return false;
		}
		catch (NumberFormatException e)
		{
			return true;
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ErrorInfoBean err= Utils.notSupported();
		Utils.forwardToErrorPage(err,request,response);
	}

}
