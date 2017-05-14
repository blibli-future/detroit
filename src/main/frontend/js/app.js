const React = require('react');
const ReactDOM = require('react-dom');

import 'bootstrap/dist/css/bootstrap.css';
import 'font-awesome/css/font-awesome.css';
import 'jquery/dist/jquery.min.js';
import '../css/app.css';
import '../css/gantellela-theme.css';

import Sidebar from './containers/Sidebar.js';
import TopNavigation from './containers/TopNavigation.js';
import Footer from './containers/Footer.js';

import AgentOverview from './components/AgentList.js';
class App extends React.Component {

  render() {
    return (
      <div className="nav-md">
        <div className="container body">
          <div className="main_container">
            <Sidebar />
            <TopNavigation />
            <AgentOverview />
            <Footer />
          </div>
        </div>
      </div>
    )
  }
}

ReactDOM.render(
  <App />,
  document.getElementById('root')
);
