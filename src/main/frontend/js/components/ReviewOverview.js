import React from 'react';

import BaseDetroitComponent from './BaseDetroitComponent';
import { Link } from 'react-router-dom'
import {BootstrapTable, TableHeaderColumn} from 'react-bootstrap-table'
import { Tab, Tabs, TabList, TabPanel } from 'react-tabs';
import 'react-tabs/style/react-tabs.css';

class ReviewOverview extends BaseDetroitComponent {
  constructor(props) {
    super(props);
    this.state = {
      reviewOverviews: []
    }
  }

  componentDidMount() {
    this.getReviewOverview();
  }

  getReviewOverview() {
    let component = this;
    return this.auth.apiCall('/api/v1/reviews/overviews', {
      method: 'GET'
    }).then((response) => response.json())
      .then((json) => {
        component.setState({
          reviewOverviews: json.content
        });
      });
  }

  render() {
    let component = this;

    let tabTitle = [];
    let reviewData = [];

    var counter = 0;

    if(this.state.reviewOverviews.length != 0) {
      this.state.reviewOverviews.forEach((item, index) => {
        counter++;
        tabTitle.push(
            <Tab key={ counter }>{ item['role'] }</Tab>
        )

        reviewData.push(
          <TabPanel key={ counter }>
            <TabContent key={ counter } title={ item['role'] } data={ item['agents'] } />
          </TabPanel>
        )
      })
    }

    return (
      <div className="right_col" role="main">
        <div className="">
          <div className="page-title">
            <div className="title_left">
              <h3>Review Overview</h3>
            </div>

            <div className="title_right">
              <div className="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search">
                <div className="input-group">
                  <input type="text" className="form-control" placeholder="Search person..."/>
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
              <div className="x_content">
                <Tabs>
                  <TabList>
                    { tabTitle }
                  </TabList>

                  { reviewData }
                </Tabs>

              </div>
            </div>
          </div>

        </div>
      </div>
    );
  }
}

class TabContent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      title: this.props.title,
        data: this.props.data
    }
  }

  rowFormatter(cell, row) {
    return <a href={cell} className="btn btn-success btn-xs">Review</a>;
  }

  render() {
    return (
        <div className="x_panel">
          <div className="x_title">
            <h2> { this.props.title } Review Table</h2>
            <div className="clearfix"></div>
          </div>
          <div className="x_content">
            <BootstrapTable data={this.props.data}>
            <TableHeaderColumn dataField='email' isKey>Email</TableHeaderColumn>
            <TableHeaderColumn dataField='nickname'>Nickname</TableHeaderColumn>
            <TableHeaderColumn dataField='position'>Position</TableHeaderColumn>
            <TableHeaderColumn dataField='channel'>Channel</TableHeaderColumn>
            <TableHeaderColumn dataField='reviewCount'>Review Count</TableHeaderColumn>
            <TableHeaderColumn dataField='idAgent' dataFormat={this.rowFormatter}>Action</TableHeaderColumn>
            </BootstrapTable>
          </div>
        </div>
    )
  }
}


export default ReviewOverview;
