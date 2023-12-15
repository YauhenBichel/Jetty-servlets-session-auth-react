import React, {Component} from 'react';
import Footer from '../../shared/footer';
import Header from './header';
import Logout from './Logout';

class Index extends Component {

	componentDidMount() {
		window.scrollTo(0, 0)
	}

	render() {
		return (
			<div className="home">
				<Header/>
				<Logout/>
				<Footer/>
			</div>
		);
	}
};

export default Index;
