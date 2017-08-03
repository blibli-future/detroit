import React from 'react';

import BaseDetroitComponent from './BaseDetroitComponent';

class ReviewForm extends BaseDetroitComponent {

  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div className="right_col" role="main">
        <div className="">
          <div className="page-title">
            <div className="title_left">
              <h3>All Statistic</h3>
            </div>
          </div>

          <div className="clearfix"></div>
          <div className="row">
            <div className="col-md-12 col-sm-12 col-xs-12">
              <div className="x_panel">
                <div className="x_title">
                  <h2> Review Table</h2>
                  <div className="clearfix"></div>
                </div>
                <div className="x_content">
                  <h1>TEST TEST</h1>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    )
  }
}

export default ReviewForm;
