import React, {Component} from 'react';
import LogoutForm from './LogoutForm';
import './css/style.css';

export default class Logout extends Component {
	constructor(props) {
		super(props);
	}

	render() {

		return (
			<div className="login">
				<LogoutForm />
				<div className="sweet-loading" />
			</div>
		);
	}
}
