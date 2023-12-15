import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle';
import {Col, Container, Row} from 'react-bootstrap';
import './../../assets/css/style.css';

class Footer extends Component {
	render() {
		return (
			<footer>
				<div className="footer-area blue-theme">
					<hr />
					<Container>
						<div className="footer-inner">
							<Row className="footer-inner-copy-left" style={{height: 20}} />
							<Row className="footer-inner-copy-left">
								<Col lg={1} md={1} sm={1}/>
								<Col lg={3} md={3} sm={3}>
									<h3>Contact Us</h3>
									<div>
										<a style={{color:'skyblue'}} href="mailto:yauhen.bichel@gmail.com">Mail to Us</a>
									</div>
									<div>
										<a style={{color:'skyblue'}} href="/about-us">About Us</a>
									</div>
								</Col>
								<Col lg={3} md={3} sm={3}/>
								<Col lg={1} md={1} sm={1}/>
							</Row>
							<Row className="footer-inner-copy-left" style={{height: 50}} />
							<Row className="footer-inner-copy-left">
								<p style={{margin: 10}}>&copy; 2023 All rights reserved</p>
							</Row>
						</div>
					</Container>
				</div>
			</footer>
		)
	}
};

export default Footer;
