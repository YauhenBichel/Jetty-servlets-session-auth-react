import URLS from "../constants";

export default class ApiClient {

    static register = function(username, password) {
        const url = URLS.BACKEND_REGISTER;
        const body = {
            "username": username,
            "password": password
        };
        const requestOptions = {
            method: 'POST',
            credentials: 'include',//for session
            headers: { 'Content-Type': 'application/json'},
            body: JSON.stringify(body)
        };

        return fetch(url, requestOptions);
    }

    static login = function(username, password) {
        const url = URLS.BACKEND_LOGIN;
        const body = {
            "username": username,
            "password": password
        };
        const requestOptions = {
            method: 'POST',
            credentials: 'include',//for session
            headers: { 'Content-Type': 'application/json'},
            body: JSON.stringify(body)
        };

        return fetch(url, requestOptions);
    }

    static logout = function() {
        const url = URLS.BACKEND_LOGOUT;
        const requestOptions = {
            method: 'GET',
            credentials: 'include',//for session
            headers: {
                'Content-Type': 'application/json'
            },
        };

        return fetch(url, requestOptions);
    }

    static searchHotelByKeyword = function(keyword) {
        console.log("searchHotelByKeyword: " + keyword);
        const url = URLS.BACKEND_HOTELS_BY_WORD + "?word=" + keyword + "&num=5";
        const requestOptions = {
            method: 'GET',
            credentials: 'include',//for session
            headers: {
                'Content-Type': 'application/json'
            },
        };

        return fetch(url, requestOptions);
    }

    static findHotelById = function(hotelId) {
        console.log("findHotelById: " + hotelId);
        const url = URLS.BACKEND_HOTEL + "?hotelId=" + hotelId;
        const requestOptions = {
            method: 'GET',
            credentials: 'include',//for session
            headers: {
                'Content-Type': 'application/json'
            },
        };

        return fetch(url, requestOptions);
    }

    static findReviewById = function(reviewId) {
        console.log("findReviewById: " + reviewId);
        const url = URLS.BACKEND_HOTEL_REVIEW + "?reviewId=" + reviewId;
        const requestOptions = {
            method: 'GET',
            credentials: 'include',//for session
            headers: {
                'Content-Type': 'application/json'
            },
        };

        return fetch(url, requestOptions);
    }

    static addReview = function(hotelId, reviewTitle, reviewText) {
        const url = URLS.BACKEND_HOTEL_REVIEW;
        const body = {
            "hotelId": hotelId,
            "title": reviewTitle,
            "review": reviewText
        };
        const requestOptions = {
            method: 'POST',
            credentials: 'include',//for session
            headers: { 'Content-Type': 'application/json'},
            body: JSON.stringify(body)
        };

        return fetch(url, requestOptions);
    }

    static findReviewsByHotelId = function(hotelId) {
        console.log("findReviewsByHotelId: " + hotelId);
        const url = URLS.BACKEND_HOTEL_REVIEW + "?hotelId=" + hotelId;
        const requestOptions = {
            method: 'GET',
            credentials: 'include',//for session
            headers: {
                'Content-Type': 'application/json'
            },
        };

        return fetch(url, requestOptions);
    }

    static deleteReview = function(reviewId) {
        console.log("deleteReview: " + reviewId);
        const url = URLS.BACKEND_HOTEL_REVIEW + "?reviewId=" + reviewId;
        const requestOptions = {
            method: 'DELETE',
            credentials: 'include',//for session
            headers: {
                'Content-Type': 'application/json'
            },
        };

        return fetch(url, requestOptions);
    }

    static updateReview = function(reviewId, reviewTitle, reviewText) {
        const url = URLS.BACKEND_HOTEL_REVIEW+ "?reviewId=" + reviewId;
        const body = {
            "title": reviewTitle,
            "review": reviewText
        };
        const requestOptions = {
            method: 'PUT',
            credentials: 'include',//for session
            headers: { 'Content-Type': 'application/json'},
            body: JSON.stringify(body)
        };

        return fetch(url, requestOptions);
    }
}
