const React = require('react');
const ReactDOM = require('react-dom');

import 'bootstrap/dist/css/bootstrap.css';
import 'font-awesome/css/font-awesome.css';
import 'jquery/dist/jquery.min.js';
import '../css/app.css';
import '../css/gantellela-theme.css';

class App extends React.Component {

    render() {
        return (
            <h1>Hello World</h1>
        )
    }
}

ReactDOM.render(
    <App />,
    document.getElementById('root')
);
