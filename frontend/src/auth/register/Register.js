import React, {Component} from 'react';
import RegisterForm from './RegisterForm';
import './css/style.css';

export default class Register extends Component {
	constructor(props) {
		super(props);
	}

	render() {
		return (
			<div className="register">
				<RegisterForm />
				<div className="sweet-loading" />
			</div>
		);
	}
}
