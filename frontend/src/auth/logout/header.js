import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle';
import './../../../assets/css/style.css';
import {Col, Container, Nav, NavItem, Row} from 'react-bootstrap';
import {Link} from "react-router-dom";

class Header extends Component {
	render() {
		return (
			<header className="header-area">
				<Container className="menu-area">
					<Row className="show-grid">
						<Col className="main-menu-item header-logo">
							<Nav className="mr-auto main-menu justify-content-end">

							</Nav>
						</Col>
						<Col  className="main-menu-item">
							<Nav className="mr-auto main-menu justify-content-end">
								<Link className="main-menu-item" to={`/`}>
									Login
								</Link>
							</Nav>
						</Col>
					</Row>
				</Container>
			</header>
		)
	}
};

export default Header;
