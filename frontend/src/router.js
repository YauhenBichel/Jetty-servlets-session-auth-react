import React from 'react';
import Login from './auth/login/index';
import Dashboard from './dashboard/dashboard';
import Register from './auth/register';
import Logout from './auth/logout';
import {
	createBrowserRouter,
	RouterProvider
} from 'react-router-dom';
import HotelDetails from "./dashboard/HotelDetails";
import ReviewDetails from "./dashboard/ReviewDetails";

let router = createBrowserRouter([
	{
		path: "/register",
		loader: () => ({ message: "loading" }),
		Component() {
			return <Register />;
		}
	}, {
		path: "/",
		loader: () => ({ message: "loading" }),
		Component() {
			return <Login />;
		},
	}, {
		path: "/dashboard",
		loader: () => ({ message: "loading" }),
		Component() {
			return <Dashboard />;
		},
	}, {
		path: "/dashboard/hotel",
		loader: () => ({ message: "loading" }),
		Component() {
			return <HotelDetails />;
		},
	}, {
		path: "/dashboard/hotel/review",
		loader: () => ({ message: "loading" }),
		Component() {
			return <ReviewDetails />;
		},
	}, {
		path: "/logout",
		loader: () => ({ message: "loading" }),
		Component() {
			return <Logout />;
		}
	},
]);

export default function Router() {
	return <RouterProvider router={router} fallbackElement={<p>Loading...</p>} />;
}

if (import.meta.hot) {
	import.meta.hot.dispose(() => router.dispose());
}
