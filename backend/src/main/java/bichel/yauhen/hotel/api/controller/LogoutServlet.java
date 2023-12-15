package bichel.yauhen.hotel.api.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static bichel.yauhen.hotel.api.Constants.KEY_ACCESS_CONTROL_ALLOW_ORIGIN;
import static bichel.yauhen.hotel.api.Constants.KEY_SESSION_USER_ID;
import static bichel.yauhen.hotel.api.Constants.OBTAIN_EXISTING_SESSION_AND_NOT_CREATE_A_NEW_ONE;

public class LogoutServlet extends HttpServlet {

	private static final Logger logger = LogManager.getLogger(LogoutServlet.class);

	public LogoutServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(OBTAIN_EXISTING_SESSION_AND_NOT_CREATE_A_NEW_ONE);

		if(session != null) {
			String userId = (String)session.getAttribute(KEY_SESSION_USER_ID);
			//invalidate whole data it stores when a user logs out
			session.invalidate();

			logger.info("Logout for user id " + userId);
		}

		response.addHeader(KEY_ACCESS_CONTROL_ALLOW_ORIGIN, "localhost, 127.0.0.1");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, DELETE, OPTIONS");
		response.addHeader("Access-Control-Allow-Headers", "*");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		response.setStatus(HttpServletResponse.SC_OK);
	}
}
