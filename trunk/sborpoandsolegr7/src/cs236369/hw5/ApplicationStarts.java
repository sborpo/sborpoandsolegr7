package cs236369.hw5;

import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cs236369.hw5.db.DbManager;

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
      DbManager.DbConnections.getInstance().setUrl( arg0.getServletContext().getInitParameter("db-server"));
      DbManager.DbConnections.getInstance().setUserName( arg0.getServletContext().getInitParameter("db-user"));
      DbManager.DbConnections.getInstance().setPassword( arg0.getServletContext().getInitParameter("db-password"));
      DbManager.DbConnections.getInstance().setDbName( arg0.getServletContext().getInitParameter("db-dbname"));
      try {
		DbManager.constructTables();
	} catch (SQLException e) {
		e.printStackTrace();
	}
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
