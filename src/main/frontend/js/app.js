import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router, Route, BrowserHistory } from 'react-router-dom';

import 'bootstrap/dist/css/bootstrap.css';
import 'font-awesome/css/font-awesome.css';
import 'jquery/dist/jquery.min.js';
import '../css/app.css';
import '../css/gantellela-theme.css';
import '../css/gantellela-js.js';
import 'react-bootstrap-table/dist/react-bootstrap-table-all.min.css';

import Footer from './containers/Footer.js';
import Sidebar from './containers/Sidebar.js';
import TopNavigation from './containers/TopNavigation.js';

import AgentDetail from './components/user/AgentDetail.js';
import AgentList from './components/AgentList.js';
import LoginPage from './components/LoginPage.js';
import ReviewerList from './components/ReviewerList.js';
import ReviewerDetail from './components/user/ReviewerDetail.js';
import StatisticAll from './components/StatisticAll.js';
import StatisticIndividual from './components/StatisticIndividual';
import ReviewOverview from './components/ReviewOverview';

class App extends React.Component {

  render() {
    return (
      <Router history={ BrowserHistory }>
        <div className="nav-md">
          <div className="container body">
            <div className="main_container">
              <Sidebar />
              <TopNavigation />

              <Route path="/view/statistic-all" component={StatisticAll} />
              <Route path="/view/statistic-individual/:agentId" component={StatisticIndividual} />
              <Route path="/view/review/overview" component={ReviewOverview} />
              <Route exact path="/view/agent-list" component={AgentList} />
              <Route exact path="/view/create-agent" component={AgentDetail} />
              <Route exact path="/view/agent/:userId" component={AgentDetail} />
              <Route exact path="/view/reviewer-list" component={ReviewerList} />
              <Route exact path="/view/reviewer/:reviewerId" component={ReviewerDetail} />
              <Route exact path="/view/login" component={LoginPage} />


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
