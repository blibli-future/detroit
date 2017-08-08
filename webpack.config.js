const ExtractTextPlugin = require('extract-text-webpack-plugin');
const path = require('path');
const webpack = require('webpack');

module.exports = {
    entry: './src/main/frontend/js/app.js',
    devtool: 'sourcemaps',
    cache: true,
    plugins: [
        new webpack.LoaderOptionsPlugin({
           debug: true
        }),
        new ExtractTextPlugin({
            filename: './src/main/resources/static/style.css',
            allChunks: true,
        }),
        new webpack.ProvidePlugin({
          $: "jquery",
          jQuery: "jquery"
        })
    ],
    output: {
        path: __dirname,
        filename: './src/main/resources/static/bundle.js'
    },
    module: {
        rules: [
            {
                test: path.join(__dirname, '.'),
                exclude: /(node_modules)/,
                loader: 'babel-loader',
                query: {
                    cacheDirectory: true,
                    presets: ['es2015', 'react']
                }
            }, {
                test: /\.s?css$/,
                use: ExtractTextPlugin.extract({
                    fallback: 'style-loader',
                    use: [ 'css-loader', 'sass-loader' ]
                })
            }, {
                test: /\.woff(2)?(\?v=[0-9]\.[0-9]\.[0-9])?$/,
                loader: "url-loader",
                options: {
                    limit: 10000,
                    mimetype: "application/font-woff",
                    outputPath: "./src/main/resources/static/fonts/",
                    publicPath: function(url) {
                        return "/fonts/" + url.split('/').pop();
                    }
                }
            }, {
                test: /\.(ttf|eot|svg)(\?v=[0-9]\.[0-9]\.[0-9])?$/,
                loader: "file-loader",
                options: {
                    outputPath: "./src/main/resources/static/fonts/",
                    publicPath: function(url) {
                        return "/fonts/" + url.split('/').pop();
                    }
                }
            }
        ]
    }
};
