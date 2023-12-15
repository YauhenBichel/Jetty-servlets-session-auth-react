import React from 'react';
import ReactDOM from 'react-dom/client';
import Router from './router';

const root = ReactDOM.createRoot(document.getElementById("app"));
root.render(
	<React.StrictMode>
		<Router/>
	</React.StrictMode>
);
