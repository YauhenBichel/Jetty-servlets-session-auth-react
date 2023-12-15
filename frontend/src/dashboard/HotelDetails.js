import React, {useEffect, useState, useCallback} from 'react';
import ApiClient from "../api/client";
import {useParams, useLocation, Link} from "react-router-dom";
import Header from "./header";
import Footer from "../shared/footer";
import {Button, FormControl, FormGroup, FormLabel} from "react-bootstrap";

export default function HotelDetails(props) {
	const [hotel, setHotel] = useState({});
	const [reviewTitle, setReviewTitle] = useState("");
	const [reviewText, setReviewText] = useState("");
	const [reviews, setReviews] = useState({});
	const [items, setItems] = useState([]);
	const [error, setError] = useState("");
	let location = useLocation();
	let hotelId = location.state.hotelId;
	let userId = location.state.userId;
	let username = location.state.username;

	useEffect(() => {
		if(!hotelId) {
			location = useLocation();
			hotelId = location.state.hotelId;
			userId = location.state.userId;
			username = location.state.username;
		}
		ApiClient.findHotelById(hotelId)
			.then(data => {

				if(data.status === 200) {
					setError("");
					data.text().then(data => {
						const hotel = data ? JSON.parse(data) : null;
						if(!hotel) {
							console.log("resp hotel: " + null);
							return;
						}
						console.log("resp hotel: " + JSON.stringify(hotel));
						setHotel(hotel);
					});
				} else if(data.status === 400) {
					data.text().then(data => {
						console.log("resp data: " + data);
						const body = data ? JSON.parse(data) : null;
						if(!body) {
							return;
						}
						console.log("resp message: " + JSON.stringify(body.message));
						setError(body.message);
					});
				} else {
					data.text().then(data => {
						console.log("resp data: " + data);
						const body = data ? JSON.parse(data) : null;
						if(!body) {
							return;
						}
						console.log("resp message: " + JSON.stringify(body.message));
						setError(body.message);
					});
				}
			}).catch(error => {
				console.log(error);
			});

		ApiClient.findReviewsByHotelId(hotelId)
			.then(data => {
				console.log(data);
				if(data.status === 200) {
					setError("");
					data.text().then(data => {
						const body = data ? JSON.parse(data) : null;
						if(!body) {
							return;
						}
						const reviews = body.reviews;
						if(reviews && reviews.length > 0) {
							setReviews(reviews);
							const listItems = reviews.map((review) => {
								console.log("review: " + review.id);
								let item = <li key={review.id}>
									{review.id} | {review.userId} | {review.hotelId} | {review.title} | {review.review} | {review.modified}
								</li>;
								if(review.userId === userId) {
									item = <li key={review.id}>
										<Link to="review" state={{hotelId: hotel.hotelId, reviewId:review.id, username: username, userId: userId, reviewTitle: review.title, reviewText: review.review}}>
											{review.id} | {review.userId} | {review.hotelId} | {review.title} | {review.review} | {review.modified}
										</Link>

									</li>;
								}
								return (item);
							});

							setItems(listItems);
						}
					});
				} else {
					data.text().then(data => {
						console.log("resp data: " + data);
						const body = data ? JSON.parse(data) : null;
						if(!body) {
							return;
						}
						console.log("resp message: " + JSON.stringify(body.message));
						setError(body.message);
					});
				}
			})
			.catch(error => {
				console.log(error);
			});
	}, []);

	function validateForm() {
		return reviewTitle.length > 0 && reviewText.length > 0;
	}

	const handleSubmit = useCallback((event) => {
		event.preventDefault();
		ApiClient.addReview(hotelId, reviewTitle, reviewText)
			.then(data => {
				console.log(data);
				if(data.status === 201) {
					setError("");
					data.text().then(data => {
						console.log("resp data: " + data);
						ApiClient.findReviewsByHotelId(hotelId)
							.then(data => {
								console.log(data);
								if(data.status === 200) {
									setError("");
									data.text().then(data => {
										const body = data ? JSON.parse(data) : null;
										if(!body) {
											return;
										}
										console.log("resp reviews: " + JSON.stringify(body.reviews));
										const reviews = body.reviews;
										if(reviews && reviews.length > 0) {
											setReviews(reviews);
											console.log("reviews: " + JSON.stringify(reviews));
											const listItems = reviews.map((review) => {
												console.log("review: " + review.id);
												let item = <li key={review.id}>
													{review.id} | {review.userId} | {review.hotelId} | {review.title} | {review.review} | {review.modified}
												</li>;
												if(review.userId === userId) {
													item = <li key={review.id}>
														<Link to="review" state={{hotelId: hotel.hotelId, reviewId:review.id, username: username, userId: userId, reviewTitle: review.title, reviewText: review.review}}>
															{review.id} | {review.userId} | {review.hotelId} | {review.title} | {review.review} | {review.modified}
														</Link>

													</li>;
												}
												return (item);
											});

											setItems(listItems);
										}
									});
								} else {
									data.text().then(data => {
										console.log("resp data: " + data);
										const body = data ? JSON.parse(data) : null;
										if(!body) {
											return;
										}
										console.log("resp message: " + JSON.stringify(body.message));
										setError(body.message);
									});
								}
							})
							.catch(error => {
								console.log(error);
							});
					});
				} else if(data.status === 400) {
					data.text().then(data => {
						console.log("resp data: " + data);
						const body = data ? JSON.parse(data) : null;
						if(!body) {
							return;
						}
						console.log("resp message: " + JSON.stringify(body.message));
						setError(body.message);
					});
				} else {
					data.text().then(data => {
						console.log("resp data: " + data);
						const body = data ? JSON.parse(data) : null;
						if(!body) {
							return;
						}
						console.log("resp message: " + JSON.stringify(body.message));
						setError(body.message);
					});
				}
			})
			.catch(error => {
				console.log(error);
			});
	}, [event]);

	return (
		<div>
			<Header />
			<div className="container justify-content-center align-items-center"
				 style={{marginTop:100}} style={{marginTop: 100}}>
				<div className="row">
					{hotel &&
						<div>
							<p>Hotel details:</p>
							<ul>
								<li>Name: {hotel.name}. ID: {hotel.hotelId}</li>
								<li>Address: {hotel.addr}</li>
								<li>Average rating: {hotel.avrRating}</li>
							</ul>
						</div>
					}
					{error.length > 0  &&
						<p>{error}</p>
					}
				</div>

				<div className="row">
					<p>Add review:</p>
					<form onSubmit={handleSubmit}>
						<FormGroup>
							<FormLabel>Review title</FormLabel>
							<FormControl
								autoFocus
								type="text"
								value={reviewTitle}
								onChange={e => setReviewTitle(e.target.value)}
							/>
							<FormLabel>Review details</FormLabel>
							<FormControl
								autoFocus
								type="text"
								value={reviewText}
								onChange={e => setReviewText(e.target.value)}
							/>
						</FormGroup>
						<Button disabled={!validateForm()}
								type="submit"
								className="btn btn-primary btn-block"
								style={{marginTop: 25}}>Add a review</Button>
					</form>
				</div>
				{reviews && reviews.length > 0 &&
				<div className="row">
						<p>Reviews:</p>
						<div>
							<ul>
								{items}
							</ul>
						</div>
				</div>
				}
				{error && error.length > 0  &&
					<p>{error}</p>
				}
			</div>
			<Footer/>
		</div>
	);
}
