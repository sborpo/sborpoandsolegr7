package cs236369.hw5.xslt;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Writer;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.fileupload.util.Streams;
import org.w3c.dom.Document;

import cs236369.hw5.db.DbManager;

public class XsltTransformer {
	public static class TransformationError extends Exception{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;}
	public static class NoXsltStyleInDb extends Exception{}
	public static InputStream getXsltFromDb(String username) throws NoXsltStyleInDb, SQLException
	{
		Connection conn=null;
		ResultSet set=null;
		try{
		conn=DbManager.DbConnections.getInstance().getConnection();
		String query="SELECT xslt FROM xslt WHERE login=?";
		PreparedStatement getStyleSheet= conn.prepareStatement(query);
		getStyleSheet.setString(1, username);
		set= getStyleSheet.executeQuery();
		if (!set.next())
		{
			throw new NoXsltStyleInDb();
		}
		ByteArrayInputStream arr = new ByteArrayInputStream(set.getString("xslt").getBytes());
		return arr;
		}
		finally{
			if (set!=null)
			{
				set.close();
			}
			if (conn!=null)
			{
				conn.close();
			}
		}
		
	}
	public static void transform(Document doc, InputStream xsltInputStream,Writer resultWriter) throws TransformationError
	{
		try{
		Document inDoc= doc;
		TransformerFactory tFactory= TransformerFactory.newInstance();
		
		StreamSource stream = new StreamSource(xsltInputStream);
		Transformer transofrmer= tFactory.newTransformer(stream);
	
		transofrmer.transform(new DOMSource(inDoc), new StreamResult(resultWriter));
		transofrmer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transofrmer.setOutputProperty(OutputKeys.METHOD, "xml");
		transofrmer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new TransformationError();
			
		}
		
		
	}

}
