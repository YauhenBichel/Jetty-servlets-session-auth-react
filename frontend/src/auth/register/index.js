import React, {Component} from 'react';
import Footer from '../../shared/footer';
import Header from './header';
import Register from './Register';

class Index extends Component {

	componentDidMount() {
		window.scrollTo(0, 0)
	}

	render() {
		return (
			<div className="home">
				<Header/>
				<Register/>
				<Footer/>
			</div>
		);
	}
};

export default Index;
