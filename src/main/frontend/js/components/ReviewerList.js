import React from 'react';
import { Link } from 'react-router-dom';

import BaseDetroitComponent from './BaseDetroitComponent';
import {BootstrapTable, TableHeaderColumn} from "react-bootstrap-table";
import Label from "react-bootstrap/es/Label";

class ReviewerList extends BaseDetroitComponent {

  constructor(props) {
    super(props);
    this.state = {
      reviewerList: [{
        id: 0,
        fullname: '',
        nickname: '',
        email: '',
        dateOfBirth: '',
        gender: '',
        location: '',
        phoneNumber: '',
        userType: '',
        teamLeader: '',
        reviewerRole: [''],
      }]
    };
    this.deleteReviewer = this.deleteReviewer.bind(this);
    this.columnNumberFormatter = this.columnNumberFormatter.bind(this);
    this.reviewRoleFormatter = this.reviewRoleFormatter.bind(this);
    this.actionFormatter = this.actionFormatter.bind(this);
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

  columnNumberFormatter(cell, row, formatExtraData, index) {
    return index+1;
  }

  reviewRoleFormatter(cell) {
    let labels = cell.map((role) => {
      role = role.substr(6);
      return <div><Label bsStyle="default">{role}</Label>&nbsp;</div>;
    });
    return <div>{labels}</div>;
  }

  actionFormatter(cell) {
    return (
      <Link to={'/view/reviewer/'+cell}
            className="btn btn-info btn-xs">
        Detail
      </Link>
    );
  }

  render() {
    return (
      <div className="right_col" role="main">
        <div className="">
          <div className="page-title">
            <div className="title_left">
              <h3>Reviewer Data</h3>
            </div>
          </div>

          <div className="clearfix" />

          <div className="row">
            <div className="col-md-12 col-sm-12 col-xs-12">
              <div className="x_panel">
                <div className="x_content">
                  <BootstrapTable data={this.state.reviewerList} striped pagination>
                    <TableHeaderColumn dataField="id" isKey dataFormat={this.columnNumberFormatter} width="100">
                      #
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField="fullname"
                                       dataSort={true}
                                       filter={{ type: 'TextFilter' }}>
                      Fullname
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField="nickname"
                                       dataSort={true}
                                       filter={{ type: 'TextFilter' }}>
                      Nickname
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField="email"
                                       dataSort={true}
                                       filter={{ type: 'TextFilter' }}>
                      Email
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField="userType"
                                       dataSort={true}
                                       filter={{ type: 'TextFilter' }}>
                      User Type
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField="reviewerRole"
                                       dataFormat={this.reviewRoleFormatter}
                                       dataSort={true}
                                       filter={{ type: 'TextFilter' }}>
                      Roles
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField="id" dataFormat={this.actionFormatter}>
                      Action
                    </TableHeaderColumn>
                  </BootstrapTable>
                </div>
              </div>
            </div>

          </div>
        </div>
      </div>
    );
  }
}

export default ReviewerList;
