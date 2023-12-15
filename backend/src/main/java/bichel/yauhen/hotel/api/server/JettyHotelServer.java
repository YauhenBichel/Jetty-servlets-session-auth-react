package bichel.yauhen.hotel.api.server;

import bichel.yauhen.hotel.api.exception.HttpHandlerNotFoundException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.http.HttpServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JettyHotelServer {
	private static final Logger logger = LogManager.getLogger(JettyHotelServer.class);

	private final Server jettyServer;
	private final ServletContextHandler handler;

	public JettyHotelServer(int port) {
		jettyServer = new Server(port);
		handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
		jettyServer.setHandler(handler);
	}

	/**
	 * Maps a given URL path/endpoint to the name of the servlet class that will handle requests coming at this endpoint
	 * @param path end point
	 * @param httpServlet  the servlet instance
	 */
	public void addHandler(String path, HttpServlet httpServlet) {
		logger.info("Added servlet: " + httpServlet.getClass().getName());
		try {
			handler.addServlet(new ServletHolder(httpServlet), path);
		}  catch (Exception ex) {
			logger.error(ex);
			throw new HttpHandlerNotFoundException("The http servlet is not found for the request", ex);
		}
	}

	/**
	 * Function that starts the server
	 * @throws Exception throws exception if access failed
	 */
	public  void start() throws Exception {
		logger.info("Starting Jetty server");

		jettyServer.setHandler(handler);
		jettyServer.start();
		//if your application is heavy enough the start might take some time.
		//Call of join() guarantees that after it the server is indeed ready
		jettyServer.join();
	}
}
