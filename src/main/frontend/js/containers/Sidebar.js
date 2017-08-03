import React from 'react';
import { NavLink } from 'react-router-dom';
import PropTypes from 'prop-types';
import { withRouter } from 'react-router';

class Sidebar extends React.Component {

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
            <div className="profile_pic">
              <img src="/images/img.jpg" alt="..." className="img-circle profile_img"/>
            </div>
            <div className="profile_info">
              <span>Welcome Admin,</span>
              <h2>John Doe</h2>
            </div>
          </div>

          <br />

          <div id="sidebar-menu" className="main_menu_side hidden-print main_menu">
            <div className="menu_section">
              <h3>General</h3>
              <ul className="nav side-menu">
                <Sidebar_Link_WithRouter
                  to="/view/review-overview"
                  currentLink="testign">
                  <i className="fa fa-clone"></i>Review Overview
                </Sidebar_Link_WithRouter>
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
                  to="/view/review/form/">
                  <i className="fa fa-user"></i>Review Form
                </Sidebar_Link_WithRouter>
                <Sidebar_Link_WithRouter
                  to="/view/review/overview">
                  <i className="fa fa-user"></i>Review Overview
                </Sidebar_Link_WithRouter>
                <Sidebar_Link_WithRouter
                  to="/view/statistic-all">
                  <i className="fa fa-user"></i>Statistic All
                </Sidebar_Link_WithRouter>
                <Sidebar_Link_WithRouter
                  to="/view/statistic-individual">
                  <i className="fa fa-user"></i>Statistic Individual
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
