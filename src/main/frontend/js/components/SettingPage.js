import React from 'react';
import swal from 'sweetalert';

import BaseDetroitComponent from './BaseDetroitComponent';
import download from 'downloadjs';

class SettingPage extends  BaseDetroitComponent {
  constructor(props) {
    super(props);
    this.checkLogin();
  }

  componentDidMount() {

  }

  checkLogin() {
    let component = this;
    return this.auth.apiCall('/api/v1/users/login', {
      method: 'GET'
    })
  }

  onCloseCutOffClick() {
    swal({
      title: 'Are you sure?',
      text: 'Closing a CutOff period will sum up all review in current CutOff period. Do this with precaution!',
      type: 'warning',
      confirmButtonColor: "#DD6B55",
      confirmButtonText: "Yes, close it!",
      closeOnConfirm: false,
      showCancelButton: true,
      showLoaderOnConfirm: true,
    }, () => {
      this.closeCurrentCutOff();
    });
  }

  closeCurrentCutOff() {
    let component = this;
    return this.auth.apiCall('/api/v1/reviews/end-period', {
      method: 'GET'
    }).then(response => response.json())
      .then(json => {
        if (json.success) {
          swal({
            title: "Success",
            text: "CutOff has been closed and new CutOff period has been created.",
            type: "success",
          });
        } else {
          swal("Error", json.errorMessage || json.message, "error");
        }
      });

  }

  render() {
    return(
      <div className="right_col" role="main">
        <div className="">
          <div className="page-title">
            <div className="title_left">
              <h3>Setting</h3>
            </div>
          </div>

            <div className="clearfix"></div>
            <div className="row">
              <div className="col-md-12 col-sm-12 col-xs-12">
                <div className="x_panel">
                  <div className="x_title">
                    <h2> Setting Form</h2>
                    <div className="clearfix"></div>
                  </div>
                  <div className="x_content">
                    <div style={ {'textAlign':'center'}}>
                      <button className="btn btn-warning btn-lg" onClick={ this.onCloseCutOffClick.bind(this) }>End CutOff Period</button>
                      <a href="/api/v1/statistic/export-data" target="_blank" className="btn btn-info btn-lg">Export Data</a>
                    </div>
                  </div>
                </div>
              </div>
            </div>

        </div>
      </div>
    )
  }
}

export default SettingPage;
