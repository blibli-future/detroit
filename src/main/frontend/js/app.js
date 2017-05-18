import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router, Route, BrowserHistory } from 'react-router-dom';

import 'bootstrap/dist/css/bootstrap.css';
import 'font-awesome/css/font-awesome.css';
import 'jquery/dist/jquery.min.js';
import '../css/app.css';
import '../css/gantellela-theme.css';

import Footer from './containers/Footer.js';
import Sidebar from './containers/Sidebar.js';
import TopNavigation from './containers/TopNavigation.js';

import AgentDetail from './components/AgentDetail.js';
import AgentList from './components/AgentList.js';
import ReviewerList from './components/ReviewerList.js';
import ReviewerDetail from './components/ReviewerDetail.js';

class App extends React.Component {

  render() {
    return (
      <Router history={ BrowserHistory }>
        <div className="nav-md">
          <div className="container body">
            <div className="main_container">
              <Sidebar />
              <TopNavigation />

              <Route path="/view/agent-list" component={AgentList} />
              <Route path="/view/agent/:agentId" component={AgentDetail} />
              <Route path="/view/reviewer-list" component={ReviewerList} />
              <Route path="/view/agent/:reviewerId" component={ReviewerDetail} />

              <Footer />
            </div>
          </div>
        </div>
      </Router>
    )
  }
}

ReactDOM.render(
  <App />,
  document.getElementById('root')
);
