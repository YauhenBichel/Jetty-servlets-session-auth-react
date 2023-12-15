const path = require("path");
const HtmlWebpackPlugin = require("html-webpack-plugin");
const webpack = require('webpack');

module.exports = (env, argv) => ({
	mode: argv && argv.mode || 'development',
	devtool: (argv && argv.mode || 'development') === 'production' ? 'source-map' : 'eval',
	entry: {
		index: './src/index.js'
	},
	module: {
		rules: [
			{
				//For every file with a js or jsx extension Webpack pipes the code through babel-loader. With this in place we're ready to write a React
				test: /\.(js|jsx)$/,
				exclude: /node_modules/,
				use: {
					loader: "babel-loader"
				}
			},
			{
				test: /\.css$/,
				use: ['style-loader', 'css-loader']
			},
			{
				test: /\.(jpg|png)$/,
				use: {
					loader: "url-loader",
					options: {
						limit: 25000,
					},
				},
			},
			{
				test: /\.(woff(2)?|ttf|eot|svg)(\?v=\d+\.\d+\.\d+)?$/,
				use: [
					{
						loader: 'file-loader',
						options: {
							name: '[name].[ext]'
						}
					}
				]
			}
		]
	},
	plugins: [
		new HtmlWebpackPlugin({
			template: './src/index.html',
			favicon: './assets/img/favicon.ico',
		})
	],
	output: {
		//path: path.resolve(__dirname, 'dist'),
		filename: 'bundle.js',
		publicPath: '/',
	},
	devServer: {
		compress: true,
		port: 3030,
		allowedHosts: [
			'localhost',
		],
		proxy: {
			'/register': 'http://localhost:8080',
			'/login': 'http://localhost:8080',
			'/logout': 'http://localhost:8080',
			'/hotels-by-word': 'http://localhost:8080',
			'/hotel': 'http://localhost:8080',
			'/hotel-review': 'http://localhost:8080',
		},
	}
});
