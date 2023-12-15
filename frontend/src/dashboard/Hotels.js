import React, {useCallback, useState} from 'react';
import { Button,
	FormGroup,
	FormControl,
	FormLabel } from 'react-bootstrap';
import ApiClient from "../api/client";
import { Link } from "react-router-dom";

export default function Hotels(props) {
	const [keyword, setKeyword] = useState("");
	const [hotelSet, setHotelSet] = useState({});
	const [items, setItems] = useState([]);
	const [error, setError] = useState("");
	const username = props.username;
	const userId = props.userId;

	const handleSubmit = useCallback((event) => {
		event.preventDefault();
		ApiClient.searchHotelByKeyword(keyword)
			.then(data => {
				console.log("resp data: " + data);

				if(data.status === 200) {
					setError("");
					data.text().then(data => {
						const body = data ? JSON.parse(data) : null;
						if(!body) {
							return;
						}
						console.log("resp hotels: " + JSON.stringify(body.hotels));
						const hotels = body.hotels;
						if(hotels && hotels.length > 0) {
							setHotelSet(hotels);
							console.log("hotels: " + JSON.stringify(hotels));
							console.log("hotelSet: " + JSON.stringify(hotelSet));
							const listItems = hotels.map((hotel) => {
								console.log("hotel: " + hotel.name);
								return (<li key={hotel.hotelId}>
									<Link to="hotel" state={{hotelId: hotel.hotelId, username: username, userId: userId}}>{hotel.name}</Link>
								</li>);
							});

							setItems(listItems);
						}
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
	}, [event]);

	return (
		<div className="container justify-content-center align-items-center">
			<form onSubmit={handleSubmit}>
				<FormGroup controlId="email">
					<FormLabel>Keyword</FormLabel>
					<FormControl
						autoFocus
						type="text"
						value={keyword}
						onChange={e => setKeyword(e.target.value)}
					/>
				</FormGroup>
				<Button type="submit"
						className="btn btn-primary btn-block"
						style={{marginTop: 25}}>Search</Button>
			</form>
			{error.length > 0  &&
				<p>{error}</p>
			}
			<div>
				<p>Hotels</p>
				<div>
					<ul>{items}</ul>
				</div>
			</div>
		</div>
	);
}
