import React from 'react';
import { Link } from 'react-router-dom';

import BaseDetroitComponent from './BaseDetroitComponent';

class ReviewerList extends BaseDetroitComponent {

  constructor(props) {
    super(props);
    this.state = {
      reviewerList: []
    };
    this.deleteReviewer = this.deleteReviewer.bind(this);
  }

  componentDidMount() {
    this.getReviewerData();
  }

  getReviewerData() {
    let component = this;
    this.auth.apiCall('/api/v1/users?type=REVIEWER', {
      method: 'GET'
    }).then((response) => response.json())
      .then((json) => {
        component.setState({
          reviewerList: json.content
        });
      })
  }

  deleteReviewer(reviewer) {
    let component = this;
    this.auth.apiCall('/api/v1/users/' + reviewer.id, {
      method: 'DELETE'
    }).then((response) => component.getAgentData());
  }

  render() {
    let reviewerListComponent = this.state.reviewerList.map((reviewer, index) => {
      return (<ReviewerList_Row key={reviewer.id} no={index+1} user={reviewer} deleteUser={this.deleteReviewer} />);
    });
    return (
      <div className="right_col" role="main">
        <div className="">
          <div className="page-title">
            <div className="title_left">
              <h3>Customer Service Data</h3>
            </div>

            <div className="title_right">
              <div className="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search">
                <div className="input-group">
                  <input type="text" className="form-control" placeholder="Search person..." />
                  <span className="input-group-btn">
                    <button className="btn btn-default" type="button">Go!</button>
                  </span>
                </div>
              </div>
            </div>
          </div>

          <div className="clearfix"></div>

          <div className="row">
            <div className="col-md-12 col-sm-12 col-xs-12">
              <div className="x_panel">
                <div className="x_title">
                  <div className="nav navbar-left">
                    <ul className="pagination pagination-sm" style={{margin:0}}>
                      <li>
                        <a href="#" aria-label="Previous">
                          <span aria-hidden="true">&laquo;</span>
                        </a>
                      </li>
                      <li className="active"><a href="#">1</a></li>
                      <li><a href="#">2</a></li>
                      <li><a href="#">3</a></li>
                      <li><a href="#">4</a></li>
                      <li><a href="#">5</a></li>
                      <li>
                        <a href="#" aria-label="Next">
                          <span aria-hidden="true">&raquo;</span>
                        </a>
                      </li>
                    </ul>
                    <p>Showing 10 of 300 Data</p>
                  </div>
                  <div className="nav navbar-right panel_toolbox">
                    <a href="domino-form-insert-cs.html" className="btn btn-success">
                      <i className="fa fa-plus-circle"></i>
                      Add Customer Service
                    </a>
                  </div>
                  <div className="clearfix"></div>
                </div>
                <div className="x_content">
                  <table className="table table-striped">
                    <thead>
                    <tr>
                      <th># <span className="fa fa-sort-amount-asc"></span></th>
                      <th>Full Name <span className="fa fa-sort-amount-asc"></span></th>
                      <th>Nickname <span className="fa fa-sort-amount-asc"></span></th>
                      <th>Email <span className="fa fa-sort-amount-asc"></span></th>
                      <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    { reviewerListComponent }
                    </tbody>
                  </table>
                  <div className="nav navbar-left">
                    <ul className="pagination pagination-sm" style={{margin:0}}>
                      <li>
                        <a href="#" aria-label="Previous">
                          <span aria-hidden="true">&laquo;</span>
                        </a>
                      </li>
                      <li className="active"><a href="#">1</a></li>
                      <li><a href="#">2</a></li>
                      <li><a href="#">3</a></li>
                      <li><a href="#">4</a></li>
                      <li><a href="#">5</a></li>
                      <li>
                        <a href="#" aria-label="Next">
                          <span aria-hidden="true">&raquo;</span>
                        </a>
                      </li>
                    </ul>
                    <p>Showing 10 of 300 Data</p>
                  </div>
                </div>
              </div>
            </div>

          </div>
        </div>
      </div>
    );
  }
}

class ReviewerList_Row extends React.Component {
  constructor(props) {
    super(props);
    this.handleDeleteUser = this.handleDeleteUser.bind(this);
  }

  handleDeleteUser(e) {
    this.props.deleteUser(this.props.user);
  }

  getUserDetailLink() {
    return '/view/reviewer/' + this.props.user.id;
  }

  getUserEditLink() {
    return '/view/reviewer/' + this.props.user.id + '/edit';
  }

  render() {
    return (
      <tr>
        <th scope="row">{ this.props.no }</th>
        <td>{ this.props.user.fullname }</td>
        <td>{ this.props.user.nickname }</td>
        <td><a>{ this.props.user.email }</a></td>
        <td>
          <Link to={ this.getUserDetailLink() }
                className="btn btn-info btn-xs">
            Detail
          </Link>
          {/*<Link to={ this.getUserEditLink() }*/}
                {/*className="btn btn-warning btn-xs">*/}
            {/*Edit*/}
          {/*</Link>*/}
          {/*<button className="btn btn-danger btn-xs"*/}
                  {/*onClick={ this.handleDeleteUser }>*/}
            {/*Delete*/}
          {/*</button>*/}
        </td>
      </tr>
    );
  }
}

export default ReviewerList;
