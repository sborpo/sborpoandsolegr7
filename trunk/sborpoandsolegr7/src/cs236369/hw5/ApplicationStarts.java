package cs236369.hw5;

import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cs236369.hw5.db.DbManager;
import cs236369.hw5.users.UserUtils;
import cs236369.wsClients.YellowPagesRegistrator;
import cs236369.wsClients.YellowPagesRegistrator.YelloPageError;

/**
 * Application Lifecycle Listener implementation class ApplicationStarts
 *
 */
public class ApplicationStarts implements ServletContextListener {

    
	
    /**
     * Default constructor. 
     */
    public ApplicationStarts() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
     try {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	} catch (InstantiationException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (IllegalAccessException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (ClassNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    Utils.redirectDelay=Integer.parseInt(arg0.getServletContext().getInitParameter("successRedirectDelay"));
    Utils.supportMail=arg0.getServletContext().getInitParameter("labSupportMail");
    UserUtils.authorizationStr=arg0.getServletContext().getInitParameter("authKey");
      DbManager.DbConnections.getInstance().setUrl( arg0.getServletContext().getInitParameter("db-server"));
      DbManager.DbConnections.getInstance().setUserName( arg0.getServletContext().getInitParameter("db-user"));
      DbManager.DbConnections.getInstance().setPassword( arg0.getServletContext().getInitParameter("db-password"));
      DbManager.DbConnections.getInstance().setDbName( arg0.getServletContext().getInitParameter("db-dbname"));

      try {
		DbManager.constructTables();
	} catch (SQLException e) {
		e.printStackTrace();
	}
//	  try {
//		YellowPagesRegistrator.registerApplicationToYellow();
//	} catch (YelloPageError e) {
//		//cannot register
//		System.out.println("Cannot register application to yellow pages");
//	}
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
//        try {
//			YellowPagesRegistrator.removeApplicationFromYellow();
//			System.out.println("The yellow page was removed!");
//		} catch (YelloPageError e) {
//			System.out.println("Failed to remove application to yellow pages");
//		}
    }
	
}
