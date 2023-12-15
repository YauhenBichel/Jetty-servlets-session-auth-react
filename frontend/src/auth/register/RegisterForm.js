import React, {useCallback, useState} from 'react';
import { Button, FormGroup, FormControl, FormLabel } from 'react-bootstrap';
import { RingLoader } from 'react-spinners';
import { css } from '@emotion/react';
import './css/style.css';
import ApiClient from "../../api/client";
import {Navigate, useNavigate} from "react-router-dom";
import { createContext } from 'react';
import User from "../../model/user";

export let UserContext = createContext(new User(""));

const override = css`
  display: block;
  margin: 0 auto;
  border-color: red;
`;

export default function RegisterForm(props) {
	const [email, setEmail] = useState("");
	const [password, setPassword] = useState("");
	const [error, setError] = useState("");
	const [loading, setLoading] = useState(false);
	const [registerRedirect, setRegisterRedirect] = useState(false);
	const navigate = useNavigate();

	function validateForm() {
		return true;// email.length > 5 && password.length >= 8;
	}

	const handleSubmit = useCallback((event) => {
		event.preventDefault();
		ApiClient.register(email, password)
			.then(data => {
				console.log(data);
				if(data.status === 201) {
					setError("");
					data.text().then(data => {
						console.log("resp data: " + data);
						const body = data ? JSON.parse(data) : null;
						console.log("username: " + email);
						console.log("userId: " + body.id);
						const userId = body.id;
						navigate('/dashboard',{state: { username:email, userId: userId}});
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

	if (registerRedirect === true) {
		return <Navigate to={"/register"}/>
	} else {
		return (
			<div className="container justify-content-center align-items-center">
				<div className="row justify-content-center align-items-center">Please register</div>
				<div className="row justify-content-center align-items-center">
					<form onSubmit={handleSubmit}>
						<FormGroup controlId="email">
							<FormLabel>Email</FormLabel>
							<FormControl
								autoFocus
								type="text"
								value={email}
								onChange={e => setEmail(e.target.value)}
							/>
						</FormGroup>
						<FormGroup controlId="password">
							<FormLabel>Password</FormLabel>
							<FormControl
								value={password}
								onChange={e => setPassword(e.target.value)}
								type="password"
							/>
						</FormGroup>
						<Button disabled={!validateForm()}
								type="submit"
								className="btn btn-primary btn-block"
								style={{marginTop: 25}}>Register</Button>
						<RingLoader
							css={override}
							size={150}
							color={"skyblue"}
							loading={loading}
						/>
					</form>
				</div>
				<div className="row justify-content-center align-items-center">
					{error && error.length > 0  &&
						<p>{error}</p>
					}
				</div>

		</div>);
	}

}
