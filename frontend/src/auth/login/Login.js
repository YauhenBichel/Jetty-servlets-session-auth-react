import React, {Component} from 'react';
import LoginForm from './LoginForm';
import './css/style.css';

export default class Login extends Component {
	constructor(props) {
		super(props);
	}

	render() {

		return (
			<div className="container justify-content-center align-items-center login">
				<div className="row justify-content-center align-items-center">
					<LoginForm />
				</div>
			</div>
		);
	}
}
