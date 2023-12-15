import React, {useEffect, useState, useCallback} from 'react';
import ApiClient from "../api/client";
import {useParams, useLocation, Link, useNavigate} from "react-router-dom";
import Header from "./header";
import Footer from "../shared/footer";
import {Button, FormControl, FormGroup, FormLabel} from "react-bootstrap";

export default function ReviewDetails(props) {
	const [error, setError] = useState("");

	const location = useLocation();
	const hotelId = location.state.hotelId;
	const reviewId = location.state.reviewId;
	const userId = location.state.userId;
	const username = location.state.username;
	const [reviewTitle, setReviewTitle] = useState(location.state.reviewTitle);
	const [reviewText, setReviewText] = useState(location.state.reviewText);

	const navigate = useNavigate();

	function validateForm() {
		return reviewTitle.length > 0 && reviewText.length > 0;
	}

	const handleUpdate = useCallback((event) => {
		event.preventDefault();
		ApiClient.updateReview(reviewId, reviewTitle, reviewText)
			.then(data => {
				console.log(data);
				if(data.status === 200) {
					setError("");
					data.text().then(data => {
						console.log("resp data: " + data);
						navigate('/dashboard/hotel', {state: {hotelId: hotelId, username: username, userId: userId}});
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
	const handleDelete = useCallback((event) => {
		event.preventDefault();
		ApiClient.deleteReview(reviewId)
			.then(data => {
				console.log(data);
				if(data.status === 200) {
					setError("");
					data.text().then(data => {
						console.log("resp data: " + data);
						navigate('/dashboard/hotel', {state: {hotelId: hotelId, username: username, userId: userId}});
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
					<p>Update review</p>
					<form onSubmit={handleUpdate}>
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
								style={{marginTop: 5, marginBottom: 20}}>Update</Button>
					</form>
				</div>
				<div className="row">
					<p>Delete review</p>
					<form onSubmit={handleDelete}>
						<Button type="submit"
								className="btn btn-primary btn-block"
								style={{marginTop: 5, marginBottom: 20}}>Delete</Button>
					</form>
				</div>

				{error && error.length > 0  &&
					<p>{error}</p>
				}
			</div>
			<Footer/>
		</div>
	);
}
