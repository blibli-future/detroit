const path = require('path');
const webpack = require('webpack');

module.exports = {
    entry: './src/main/frontend/js/app.js',
    devtool: 'sourcemaps',
    cache: true,
    plugins: [
       new webpack.LoaderOptionsPlugin({
           debug: true
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
            },
        ]
    }
};
