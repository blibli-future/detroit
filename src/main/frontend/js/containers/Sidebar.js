import React from 'react';
import { NavLink } from 'react-router-dom';
import PropTypes from 'prop-types';
import { withRouter } from 'react-router';
import AuthenticationService from "../services/AuthenticationService";

class Sidebar extends React.Component {

  constructor(props){
    super(props);
    this.state = {
      email: 'NOT LOGGED IN',
    };
    this.auth = AuthenticationService.instance;
  }

  componentDidMount() {
    if(this.auth.isLoggedIn()) {
      this.setState({email: this.auth.getEmail()});
    }
  }

  render() {
    return (
      <div className="col-md-3 left_col">
        <div className="left_col scroll-view">
          <div className="navbar nav_title" style={{border: 0}}>
            <a href="index.html" className="site_title"><i className="fa fa-rocket"></i>
              <span>DETROIT</span></a>
          </div>

          <div className="clearfix"></div>

          <div className="profile">
            <div className="profile_info" style={{width:'100%'}}>
              <span>Logged in as,</span>
              <h2>{this.state.email}</h2>
            </div>
          </div>

          <br />

          <div className="clearfix"></div>

          <div id="sidebar-menu" className="main_menu_side hidden-print main_menu">
            <div className="menu_section">
              <ul className="nav side-menu">
                <Sidebar_Link_WithRouter
                    to="/view/parameter-management">
                  <i className="fa fa-list-ol" /> Parameter Management
                </Sidebar_Link_WithRouter>
                <Sidebar_Link_WithRouter
                  to="/view/agent-list"
                  currentLink="testign">
                  <i className="fa fa-users"></i>Agent Management
                </Sidebar_Link_WithRouter>
                <Sidebar_Link_WithRouter
                  to="/view/reviewer-list"
                  currentLink="testign">
                  <i className="fa fa-user"></i>Reviewer Management
                </Sidebar_Link_WithRouter>
                <Sidebar_Link_WithRouter
                  to="/view/review/overview">
                  <i className="fa fa-pencil-square-o"></i>Review Overview
                </Sidebar_Link_WithRouter>
                <Sidebar_Link_WithRouter
                  to="/view/review/history">
                  <i className="fa fa-clock-o"></i>Review History
                </Sidebar_Link_WithRouter>
                <Sidebar_Link_WithRouter
                  to="/view/statistic-all">
                  <i className="fa fa-line-chart"></i>Statistic All
                </Sidebar_Link_WithRouter>
                <li><a><i className="fa fa-cog"></i> Settings</a>
                </li>
              </ul>
            </div>

          </div>
        </div>
      </div>
    )
  }
}

class Sidebar_Link extends React.Component {

  constructor(props) {
    super(props);
    this.getActiveClass = this.getActiveClass.bind(this);
  }

  getActiveClass() {
    if (this.props.to === this.props.location.pathname) {
      return "current-page";
    }
  }

  render() {
    return (
      <li className={ this.getActiveClass() }>
        <NavLink
          to={ this.props.to }>
          { this.props.children }
        </NavLink>
      </li>
    );
  }
}

Sidebar_Link.propTypes = {
  match: PropTypes.object.isRequired,
  location: PropTypes.object.isRequired,
  history: PropTypes.object.isRequired
};

const Sidebar_Link_WithRouter = withRouter(Sidebar_Link);

export default Sidebar;
