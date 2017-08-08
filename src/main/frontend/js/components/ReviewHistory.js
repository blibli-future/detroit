import React from 'react';

import BaseDetroitComponent from './BaseDetroitComponent';
import { Link } from 'react-router-dom'
import {BootstrapTable, TableHeaderColumn} from 'react-bootstrap-table'
import { Tab, Tabs, TabList, TabPanel } from 'react-tabs';
import 'react-tabs/style/react-tabs.css';
import { ButtonGroup } from 'react-bootstrap';

export default class ReviewHistory extends BaseDetroitComponent {
  constructor(props) {
    super(props);
    this.state = {
      reviewHistory: [
        {
          id: 0,
          emailAgent: '',
          agentFullname: '',
          position: '',
          channel: '',
          parameter: '',
          score: 0,
          reviewDate: ''
        }
      ]
    }

    this.rowFormatter = this.rowFormatter.bind(this);
  }

  componentDidMount() {
    this.getReviewHistory();
    this.convertDate();
  }

  onDeleteReviewClick(reviewId) {
    swal({
      title: 'Are you sure?',
      text: 'Deleting a review may make statistics behave incorrectly. Do this with precaution!',
      type: 'warning',
      confirmButtonColor: "#DD6B55",
      confirmButtonText: "Yes, delete it!",
      closeOnConfirm: false,
      showCancelButton: true,
      showLoaderOnConfirm: true,
    }, () => {
      this.deleteReview(reviewId)
    });
  }

  deleteReview(reviewId) {
    let component = this;
    this.auth.apiCall('/api/v1/reviews/' + reviewId, {
      method: 'DELETE'
    }).then(response => response.json())
      .then(json => {
        if (json.success) {
          component.getReviewHistory();
          swal('Success', 'Review has been deleted.', 'success');
        } else {
          return swal('Error', json.errorMessage || json.message, 'error');
        }
      });
  }

  getReviewHistory() {
    let component = this;
    return this.auth.apiCall('/api/v1/reviews/history', {
      method: 'GET'
    }).then((response) => response.json())
      .then((json) => {
        component.setState({
          reviewHistory: json.content
        });
      });
  }

  convertDate(cell, row) {
    return new Date(cell).toLocaleString(['id','en-US']);
  }

  rowFormatter(cell, row) {
    return (
    <div>
      <ButtonGroup>
        <Link to={ "/view/review/edit/"+cell }
              className="btn btn-warning btn-xs">
          Edit
        </Link>
        <a onClick={ this.onDeleteReviewClick.bind(this, cell) } className="btn btn-danger btn-xs">Delete</a>
      </ButtonGroup>
    </div>
    );
  }

  render() {
    return (
      <div className="right_col" role="main">
        <div className="">
          <div className="page-title">
            <div className="title_left">
              <h3>Review History</h3>
            </div>
          </div>

          <div className="clearfix"></div>

          <div className="row">
            <div className="col-md-12 col-sm-12 col-xs-12">
              <div className="x_content">
                <div className="x_panel">
                  <div className="x_title">
                    <h2> Review History Table <small>all review in current cutoff</small></h2>
                    <div className="clearfix"></div>
                  </div>
                  <div className="x_content">
                    <BootstrapTable data={this.state.reviewHistory} pagination={true}>
                      <TableHeaderColumn filter={ { type: 'TextFilter', delay: 0 } } dataSort={ true } dataField='emailAgent'>Email</TableHeaderColumn>
                      <TableHeaderColumn filter={ { type: 'TextFilter', delay: 0 } } dataSort={ true } dataField='agentFullname'>Fullname</TableHeaderColumn>
                      <TableHeaderColumn filter={ { type: 'TextFilter', delay: 0 } } dataSort={ true } dataField='position'>Position</TableHeaderColumn>
                      <TableHeaderColumn filter={ { type: 'TextFilter', delay: 0 } } dataSort={ true }  width="100" dataField='channel'>Channel</TableHeaderColumn>
                      <TableHeaderColumn filter={ { type: 'TextFilter', delay: 0 } } dataSort={ true } dataField='parameter'>Parameters</TableHeaderColumn>
                      <TableHeaderColumn filter={ { type: 'NumberFilter', delay: 0 } } dataSort={ true } dataAlign="center" dataField='score'>Score</TableHeaderColumn>
                      <TableHeaderColumn filter={ { type: 'DateFilter', delay: 0 } } dataSort={ true } dataField='reviewDate' dataFormat={this.convertDate}>Created At</TableHeaderColumn>
                      <TableHeaderColumn dataField='id' width="100" dataAlign="center" dataFormat={this.rowFormatter} isKey>Action</TableHeaderColumn>
                    </BootstrapTable>
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
