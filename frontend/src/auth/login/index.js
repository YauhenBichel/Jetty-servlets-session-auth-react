import React, {Component} from 'react';
import Footer from '../../shared/footer';
import Header from './header';
import Login from './Login';

class Index extends Component {
	
	componentDidMount() {
		window.scrollTo(0, 0)
	}
	
	render() {
		return (
			<div className="home">
				<Header/>
				<Login/>
				<Footer/>
			</div>
		);
	}
};

export default Index;
