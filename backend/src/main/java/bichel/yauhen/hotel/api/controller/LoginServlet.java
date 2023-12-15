package bichel.yauhen.hotel.api.controller;

import bichel.yauhen.hotel.api.utils.JsonUtils;
import bichel.yauhen.hotel.api.exception.AccountNotFoundException;
import bichel.yauhen.hotel.api.mapper.AccountMapper;
import bichel.yauhen.hotel.api.model.Account;
import bichel.yauhen.hotel.api.service.AccountService;
import bichel.yauhen.hotel.api.vo.ErrorResponse;
import bichel.yauhen.hotel.api.vo.LoginRequest;
import bichel.yauhen.hotel.api.vo.LoginResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

import static bichel.yauhen.hotel.api.Constants.CONTENT_TYPE_JSON;
import static bichel.yauhen.hotel.api.Constants.ERROR_ACCOUNT_NOT_FOUND;
import static bichel.yauhen.hotel.api.Constants.KEY_ACCESS_CONTROL_ALLOW_ORIGIN;
import static bichel.yauhen.hotel.api.Constants.KEY_SESSION_USER_ID;

public class LoginServlet extends HttpServlet {

	private static final Logger logger = LogManager.getLogger(LoginServlet.class);

	private final AccountMapper accountMapper;
	private final AccountService accountService;

	public LoginServlet(AccountService accountService, AccountMapper accountMapper) {
		super();
		this.accountMapper = accountMapper;
		this.accountService = accountService;
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType(CONTENT_TYPE_JSON);
		response.addHeader(KEY_ACCESS_CONTROL_ALLOW_ORIGIN, "localhost, 127.0.0.1");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, DELETE, OPTIONS");
		response.addHeader("Access-Control-Allow-Headers", "*");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		response.setStatus(HttpServletResponse.SC_OK);

		LoginRequest loginRequest = accountMapper.mapToLoginRequest(request);
		Account dbAccount = null;

		try {
			dbAccount = accountService.login(loginRequest);
		} catch (AccountNotFoundException ex) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			PrintWriter out = response.getWriter();

			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setError(ERROR_ACCOUNT_NOT_FOUND);
			errorResponse.setMessage("User not found");
			out.write(JsonUtils.getJsonString(errorResponse));
			return;
		}

		logger.info("Successfully authenticated the user " + loginRequest.getUsername());

		LoginResponse bodyResp = accountMapper.mapToLoginResponse(dbAccount);

		//create a new session
		HttpSession session = request.getSession();
		session.setAttribute(KEY_SESSION_USER_ID, String.valueOf(bodyResp.getId()));

		PrintWriter out = response.getWriter();
		out.write(JsonUtils.getJsonString(bodyResp));
	}
}
