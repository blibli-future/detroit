const React = require('react');

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
              <img src="images/img.jpg" alt="..." className="img-circle profile_img"/>
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
                <li><a href="domino-review-overview.html"><i className="fa fa-clone"></i>Review Overview
                </a>
                </li>
                <li><a href="domino-category-overview.html"><i className="fa fa-list-ol"></i> Category
                  Setting</a>
                </li>
                <li><a href="domino-customer-service-overview.html"><i className="fa fa-user"></i> Agent
                  Management</a>
                </li>
                <li><a href="domino-reviewer-overview.html"><i className="fa fa-users"></i> User
                  Management</a>
                </li>
                <li><a><i className="fa fa-bar-chart-o"></i> Statistics <span
                  className="fa fa-chevron-down"></span></a>
                  <ul className="nav child_menu">
                    <li><a href="domino-statistic-all.html">Summary All Data</a></li>
                    <li><a href="domino-statistic-individual.html">Individual Data</a></li>
                  </ul>
                </li>
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

export default Sidebar;
