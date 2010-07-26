package cs236369.hw5.reporter;

import java.io.IOException;
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

import cs236369.hw5.db.DbManager;
import cs236369.hw5.xslt.XsltTransformer;
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
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Connection con=null;
		ResultSet set= null;
		try {
			builder = factory.newDocumentBuilder();

			Document doc = builder.newDocument();
			Element results = doc.createElement("Results");
			doc.appendChild(results);

			

			con=DbManager.DbConnections.getInstance().getConnection();
			PreparedStatement report= con.prepareStatement("select * from reservations"); //TODO: change
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
			PrintWriter sw = response.getWriter();
			String str= getServletContext().getRealPath("groupReservationsByUserGroup.xsl"); //TODO: Boris
			XsltTransformer.transform(doc, str, response.getWriter());
			
			System.out.println(sw);


			System.out.println(sw.toString());
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
		finally {

			try {
				con.close();
				set.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
