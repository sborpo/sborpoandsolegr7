package cs236369.hw5.xslt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import cs236369.hw5.ErrorInfoBean;
import cs236369.hw5.User;
import cs236369.hw5.Utils;
import cs236369.hw5.db.DbManager;
import cs236369.hw5.users.UserManager;
import cs236369.hw5.users.UserUtils.FileTooBigExp;

/**
 * Servlet implementation class UploadXSLT
 */
public class UploadXSLT extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadXSLT() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ErrorInfoBean err = new ErrorInfoBean();
		try{
		ServletFileUpload upload = new ServletFileUpload();
		FileItemIterator iter = upload.getItemIterator(request);
		if (!iter.hasNext())
		{
			throw new FileUploadException();
		}
		FileItemStream item = iter.next();
		InputStream stream = item.openStream();
		String xsltFile=Streams.asString(stream);
		String currentUser=request.getUserPrincipal().getName();
		insertXsltFile(currentUser,xsltFile);
		return;
		}
		catch (SQLException e) {
				err.setErrorString("Database Error");
				err.setReason("The file couldn't be upload due to database error");
		} catch (FileUploadException e) {
			err.setErrorString("File Upload Error");
			err.setReason("The file couldn't be uploaded");
		}
		Utils.forwardToErrorPage(err,request,response);
	}

	private void insertXsltFile(String currentUser, String xsltFile) throws SQLException {
		Connection conn=null;
		try{
		conn=DbManager.DbConnections.getInstance().getConnection();
		String query="INSERT INTO xslt VALUES (?,?) ON DUPLICATE KEY UPDATE xslt=?;";
		PreparedStatement insertStyleSheet= conn.prepareStatement(query);
		insertStyleSheet.setString(1, currentUser);
		insertStyleSheet.setString(2, xsltFile);
		insertStyleSheet.setString(3, xsltFile);
		insertStyleSheet.execute();
		}
		finally{
			if (conn!=null)
			{
				conn.close();
			}
		}
		
		
	}

}
