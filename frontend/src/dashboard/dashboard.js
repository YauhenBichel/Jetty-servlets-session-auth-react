import React, {Component} from 'react';
import Footer from '../shared/footer';
import Header from './header';
import Hotels from "./Hotels";
import {useLocation} from "react-router-dom";

export default function Dashboard(props) {
	const location = useLocation();
	const username = location.state.username;
	const userId = location.state.userId;

	console.log("userId: " + userId);

	return (
		<div className="user">
			<Header />
			<div className="container justify-content-center align-items-center"
			 style={{marginTop:100}}>
				<div className="row">
					<p>Hi, {username}</p>
				</div>
				<div className="row">
					<p>Search Hotels By Keyword</p>
				</div>
				<div className="row">
					<Hotels username={username} userId={userId} />
				</div>
			</div>
			<Footer/>
		</div>
	);
}
