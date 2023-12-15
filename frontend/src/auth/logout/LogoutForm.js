import React, {useCallback, useState} from 'react';
import { Button } from 'react-bootstrap';
import './css/style.css';
import {Navigate} from "react-router-dom";
import ApiClient from "../../api/client";

export default function LogoutForm(props) {
	const [loginRedirect, setLoginRedirect] = useState(false);
	const [logoutRedirect, setLogoutRedirect] = useState(false);
	const [error, setError] = useState("");

	const handleSubmit = useCallback((event) => {
		event.preventDefault();
		ApiClient.logout()
			.then(data => {
				console.log(data);
				setLoginRedirect(true);
			}).catch(error => {
			console.log(error);
			setLogoutRedirect(true);
		});
	}, [event]);

	if (loginRedirect === true) {
		return <Navigate to={"/"}/>
	} else if (logoutRedirect === true) {
		return <Navigate to={"/logout"}/>
	} else {
		return (
			<div>
				<p>Logout</p>
				<form onSubmit={handleSubmit}>
					<Button size="lg"
							type="submit"
							className="btn btn-primary btn-block"
							style={{marginTop: 25}}>Logout</Button>
				</form>
				{error.length > 0  &&
					<p>{error}</p>
				}
			</div>
		);
	}
}
