package bichel.yauhen.hotel.api.controller;

import bichel.yauhen.hotel.api.utils.JsonUtils;
import bichel.yauhen.hotel.api.mapper.HotelMapper;
import bichel.yauhen.hotel.api.mapper.WordHotelsMapper;
import bichel.yauhen.hotel.api.vo.WordHotelsResponse;
import bichel.yauhen.hotel.core.data.ThreadSafeHotelReviewDataForReading;
import bichel.yauhen.hotel.core.model.Hotel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import static bichel.yauhen.hotel.api.Constants.CONTENT_TYPE_JSON;
import static bichel.yauhen.hotel.api.Constants.KEY_ACCESS_CONTROL_ALLOW_ORIGIN;
import static bichel.yauhen.hotel.api.Constants.OBTAIN_EXISTING_SESSION_AND_NOT_CREATE_A_NEW_ONE;

/**
 * Servlet for endpoint find hotels by word
 */
public class HotelsByWordServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(HotelsByWordServlet.class);

    private final ThreadSafeHotelReviewDataForReading hotelReviewData;
    private final WordHotelsMapper wordHotelsMapper;

    public HotelsByWordServlet(ThreadSafeHotelReviewDataForReading hotelReviewData) {
        super();
        this.hotelReviewData = hotelReviewData;
        wordHotelsMapper = new WordHotelsMapper(new HotelMapper());
    }

    /**
     * Action for HTTP request method GET
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        logger.info("Process resource '/hotels-by-word' to find a word");
        response.addHeader(KEY_ACCESS_CONTROL_ALLOW_ORIGIN, "localhost, 127.0.0.1");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, DELETE, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "*");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        HttpSession session = request.getSession(OBTAIN_EXISTING_SESSION_AND_NOT_CREATE_A_NEW_ONE);

        if(session == null) {
            logger.error("session is not found");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        response.setContentType(CONTENT_TYPE_JSON);
        PrintWriter out = response.getWriter();

        final String paramWord = "word";
        final String paramNum = "num";

        if (request.getQueryString() == null || request.getQueryString().isEmpty()) {
            logger.warn("query params are missing");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.printf(JsonUtils.wrapInvalidResponseToJson(paramWord));
        } else {
            String wordParamValue = request.getParameter(paramWord);
            String numParamValue = request.getParameter(paramNum);

            if (wordParamValue == null) {
                logger.warn("word is missing");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.printf(JsonUtils.wrapInvalidResponseToJson(paramWord));
            } else if (numParamValue == null) {
                logger.warn("num is missing");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.printf(JsonUtils.wrapInvalidResponseToJson(paramNum));
            } else {
                int amount = Integer.parseInt(numParamValue, 10);
                Set<Hotel> wordHotels = hotelReviewData.findHotelsByWord(wordParamValue, amount);

                WordHotelsResponse respBody = wordHotelsMapper.mapToResponse(wordParamValue, wordHotels);
                String json = JsonUtils.getJsonString(respBody);

                response.setStatus(HttpServletResponse.SC_OK);
                out.printf(json);
            }
        }
    }
}
