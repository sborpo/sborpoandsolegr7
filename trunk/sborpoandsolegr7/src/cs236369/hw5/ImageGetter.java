package cs236369.hw5;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs236369.hw5.users.UserManager;

/**
 * Servlet implementation class ImageGetter
 */
public class ImageGetter extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageGetter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  response.setContentType("image/gif");
		  byte[] photo=null;
		  try{
		   photo= UserManager.getPhoto(request.getParameter("username"));
		  }
		  catch (SQLException ex)
		  {
			  return;
		  }
		  if (photo==null)
		  {
			  return;
		  }
		  
	      OutputStream out=response.getOutputStream();
	      out.write(photo, 0, photo.length);
	      out.flush();
	}

}
