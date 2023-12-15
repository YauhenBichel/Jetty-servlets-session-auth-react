package bichel.yauhen.hotel.api.controller;

import bichel.yauhen.hotel.api.mapper.HotelReviewMapper;
import bichel.yauhen.hotel.api.mapper.HotelReviewsMapper;
import bichel.yauhen.hotel.api.model.ReviewEntity;
import bichel.yauhen.hotel.api.service.HotelService;
import bichel.yauhen.hotel.api.service.ReviewService;
import bichel.yauhen.hotel.api.utils.JsonUtils;
import bichel.yauhen.hotel.api.vo.HotelReviewRequest;
import bichel.yauhen.hotel.api.vo.HotelReviewResponse;
import bichel.yauhen.hotel.api.vo.HotelReviewsResponse;
import bichel.yauhen.hotel.api.vo.UpdateHotelReviewRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static bichel.yauhen.hotel.api.Constants.CONTENT_TYPE_JSON;
import static bichel.yauhen.hotel.api.Constants.KEY_ACCESS_CONTROL_ALLOW_ORIGIN;
import static bichel.yauhen.hotel.api.Constants.KEY_SESSION_USER_ID;
import static bichel.yauhen.hotel.api.Constants.OBTAIN_EXISTING_SESSION_AND_NOT_CREATE_A_NEW_ONE;

/**
 * Servlet for endpoint hotel
 */
public class HotelReviewServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(HotelReviewServlet.class);
    private final HotelReviewMapper hotelReviewMapper;
    private final HotelReviewsMapper hotelReviewsMapper;
    private final HotelService hotelService;
    private final ReviewService reviewService;

    public HotelReviewServlet(HotelService hotelService, ReviewService reviewService) {
        super();
        this.hotelReviewMapper = new HotelReviewMapper();
        this.hotelReviewsMapper = new HotelReviewsMapper(hotelReviewMapper);
        this.hotelService = hotelService;
        this.reviewService = reviewService;
    }

    /**
     * Action for HTTP request method POST
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(CONTENT_TYPE_JSON);
        response.addHeader(KEY_ACCESS_CONTROL_ALLOW_ORIGIN, "localhost, 127.0.0.1");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setStatus(HttpServletResponse.SC_CREATED);

        HttpSession session = request.getSession(OBTAIN_EXISTING_SESSION_AND_NOT_CREATE_A_NEW_ONE);

        if (session == null) {
            logger.error("session is not found");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            int userId = Integer.parseInt((String) session.getAttribute(KEY_SESSION_USER_ID));
            HotelReviewRequest hotelReviewRequest = hotelReviewMapper.mapToHotelReviewRequest(request);

            ReviewEntity dbReviewEntity = reviewService.add(userId, hotelReviewRequest);

            HotelReviewResponse respBody = hotelReviewMapper.mapToResponse(dbReviewEntity);
            PrintWriter out = response.getWriter();
            out.write(JsonUtils.getJsonString(respBody));
        } catch (Exception ex) {
            logger.error(ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        logger.info("Process resource '/hotel-review' to get all reviews");
        response.addHeader(KEY_ACCESS_CONTROL_ALLOW_ORIGIN, "localhost");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, DELETE, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "*");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        HttpSession session = request.getSession(OBTAIN_EXISTING_SESSION_AND_NOT_CREATE_A_NEW_ONE);

        if (session == null) {
            logger.error("session is not found");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        response.setContentType(CONTENT_TYPE_JSON);
        PrintWriter out = response.getWriter();
        final String paramHotelId = "hotelId";

        if (request.getQueryString() == null || request.getQueryString().isEmpty()) {
            logger.warn("query params are missing");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.printf(JsonUtils.wrapInvalidResponseToJson(paramHotelId));
        } else {
            String hotelId = request.getParameter(paramHotelId);
            if (hotelId == null || hotelId.isEmpty()) {
                logger.info("Hotel ID is missing");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.printf(JsonUtils.wrapInvalidResponseToJson(paramHotelId));
            } else {
                List<ReviewEntity> reviews = reviewService.getByHotelId(Integer.parseInt(hotelId));
                logger.info("Reviews size: " + reviews.size());
                HotelReviewsResponse respBody = hotelReviewsMapper.mapToResponse(hotelId, reviews);
                String json = JsonUtils.getJsonString(respBody);

                response.setStatus(HttpServletResponse.SC_OK);
                out.printf(json);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        logger.info("Process resource '/hotel-review' to delete review");
        response.addHeader(KEY_ACCESS_CONTROL_ALLOW_ORIGIN, "localhost, 127.0.0.1");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, DELETE, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "*");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        HttpSession session = request.getSession(OBTAIN_EXISTING_SESSION_AND_NOT_CREATE_A_NEW_ONE);

        if (session == null) {
            logger.error("session is not found");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        response.setContentType(CONTENT_TYPE_JSON);
        PrintWriter out = response.getWriter();
        final String paramReviewId = "reviewId";

        if (request.getQueryString() == null || request.getQueryString().isEmpty()) {
            logger.warn("query params are missing");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.printf(JsonUtils.wrapInvalidResponseToJson(paramReviewId));
        } else {
            String reviewId = request.getParameter(paramReviewId);
            if (reviewId == null || reviewId.isEmpty()) {
                logger.info("Review ID is missing");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.printf(JsonUtils.wrapInvalidResponseToJson(paramReviewId));
            } else {
                reviewService.delete(Integer.parseInt(reviewId));
                response.setStatus(HttpServletResponse.SC_OK);
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(CONTENT_TYPE_JSON);
        response.addHeader(KEY_ACCESS_CONTROL_ALLOW_ORIGIN, "localhost");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setStatus(HttpServletResponse.SC_OK);

        HttpSession session = request.getSession(OBTAIN_EXISTING_SESSION_AND_NOT_CREATE_A_NEW_ONE);

        if (session == null) {
            logger.error("session is not found");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        PrintWriter out = response.getWriter();
        final String paramReviewId = "reviewId";

        if (request.getQueryString() == null || request.getQueryString().isEmpty()) {
            logger.warn("query params are missing");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.printf(JsonUtils.wrapInvalidResponseToJson(paramReviewId));
        } else {
            String reviewId = request.getParameter(paramReviewId);
            if (reviewId == null || reviewId.isEmpty()) {
                logger.info("Review ID is missing");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.printf(JsonUtils.wrapInvalidResponseToJson(paramReviewId));
            } else {
                try {
                    int userId = Integer.parseInt((String) session.getAttribute(KEY_SESSION_USER_ID));
                    UpdateHotelReviewRequest hotelReviewRequest = hotelReviewMapper.mapToUpdateHotelReviewRequest(request);
                    ReviewEntity dbReviewEntity = reviewService.update(Integer.parseInt(reviewId), userId, hotelReviewRequest);
                    HotelReviewResponse respBody = hotelReviewMapper.mapToResponse(dbReviewEntity);
                    out.write(JsonUtils.getJsonString(respBody));
                    response.setStatus(HttpServletResponse.SC_OK);
                } catch (Exception ex) {
                    logger.error(ex);
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            }
        }
    }
}
