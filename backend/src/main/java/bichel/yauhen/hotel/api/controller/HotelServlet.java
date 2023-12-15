package bichel.yauhen.hotel.api.controller;

import bichel.yauhen.hotel.api.utils.JsonUtils;
import bichel.yauhen.hotel.api.mapper.HotelMapper;
import bichel.yauhen.hotel.api.vo.HotelResponse;
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
import java.util.Optional;

import static bichel.yauhen.hotel.api.Constants.CONTENT_TYPE_JSON;
import static bichel.yauhen.hotel.api.Constants.KEY_ACCESS_CONTROL_ALLOW_ORIGIN;
import static bichel.yauhen.hotel.api.Constants.OBTAIN_EXISTING_SESSION_AND_NOT_CREATE_A_NEW_ONE;

/**
 * Servlet for endpoint hotel
 */
public class HotelServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(HotelServlet.class);

    private final ThreadSafeHotelReviewDataForReading hotelReviewData;
    private final HotelMapper hotelMapper;

    public HotelServlet(ThreadSafeHotelReviewDataForReading hotelReviewData) {
        super();
        this.hotelReviewData = hotelReviewData;
        this.hotelMapper = new HotelMapper();
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
            throws ServletException, IOException {

        logger.info("Process resource hotelInfo");
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

        final String paramHotelId = "hotelId";

        if(request.getQueryString() == null || request.getQueryString().isEmpty()) {
            logger.warn("query params are missing");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.printf(JsonUtils.wrapInvalidResponseToJson(paramHotelId));
        } else {
            String hotelId = request.getParameter(paramHotelId);
            if(hotelId == null || hotelId.isEmpty()) {
                logger.info("Hotel ID is missing");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.printf(JsonUtils.wrapInvalidResponseToJson(paramHotelId));
            } else {
                Optional<Hotel> optHotel = hotelReviewData.findHotel(hotelId);
                if (optHotel.isPresent()) {
                    logger.info("Hotel: " + optHotel.get());
                    HotelResponse respBody = hotelMapper.mapToResponse(optHotel.get());
                    String json = JsonUtils.getJsonString(respBody);

                    response.setStatus(HttpServletResponse.SC_OK);
                    out.printf(json);
                } else {
                    logger.info("Hotel with ID '" + hotelId + "' is not found");
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.printf(JsonUtils.wrapInvalidResponseToJson(paramHotelId));
                }
            }
        }
    }
}
