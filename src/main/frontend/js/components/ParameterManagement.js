import React from 'react';
import { Link } from 'react-router-dom';
import {BootstrapTable, TableHeaderColumn} from 'react-bootstrap-table';
import {Button, ButtonGroup} from 'react-bootstrap';
import swal from 'sweetalert';

import BaseDetroitComponent from './BaseDetroitComponent';

class ParameterManagement extends BaseDetroitComponent {

  constructor(props) {
    super(props);
    this.state = {
      parameterList: [],
      saveBatch: false,
    };
    this.cellEditProp = {
      mode: 'click',
    };
  }

  componentDidMount() {
    this.getAgentData();
  }

  getAgentData() {
    let component = this;
    this.auth.apiCall('/api/v1/parameters', {
      method: 'GET'
    }).then((response) => response.json())
      .then((json) => {
        component.setState({
          parameterList: json.content
        });
      });
  }

  handleDeleteButton(parameterId) {
    swal({
      title: 'Are you sure?',
      text: 'Deleting a parameter may make statistics behave incorrectly. Do this with precaution!',
      type: 'warning',
      confirmButtonColor: "#DD6B55",
      confirmButtonText: "Yes, delete it!",
      closeOnConfirm: false,
      showCancelButton: true,
      showLoaderOnConfirm: true,
    }, () => {
      this.deleteParameter(parameterId)
    });
  }

  deleteParameter(parameterId) {
    let component = this;
    this.auth.apiCall('/api/v1/parameters/' + parameterId, {
      method: 'DELETE'
    }).then((response) => response.json())
      .then(json => {
        if (json.success) {
          component.getAgentData();
          swal("Success", "Parameter has been deleted.", "success");
        } else {
          swal("Failed to save data", json.errorMessage || json.message, "error");
        }
      });
  }

  batchSaveParameterWeight() {
    let jsonLoad = this.state.parameterList.map((parameter) => {
      return {parameterId: parameter.id, weight: parameter.weight}
    });
    this.auth.apiCall('/api/v1/parameters/batch-update', {
      method: 'PATCH',
      body: JSON.stringify(jsonLoad)
    }).then((response) => response.json())
      .then((json) => {
        if (json.success) {
          swal("Success", "New weight saved.", "success");
        } else {
          swal("Failed to save data", json.errorMessage || json.message, "error");
        }
      });
  }

  weightFormatter(weight, row) {
    return weight + "%";
  }

  columnNumberFormatter(cell, row, formatExtraData, index) {
    return index+1;
  }

  actionButtonFormatter(id, row) {
    return (
      <ButtonGroup>
        <Link to={'/view/parameter-detail/' + id}
              className="btn btn-info btn-xs">
          Detail
        </Link>
        <Button bsSize="xs" bsStyle="danger" onClick={this.handleDeleteButton.bind(this, id)}>
          Delete
        </Button>
      </ButtonGroup>
    )
  }

  render() {
    return (
      <div className="right_col" role="main">
        <div className="">
          <div className="page-title">
            <div className="title_left">
              <h3>Parameter Management</h3>
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

          <div className="clearfix" />

          <div className="row">
            <div className="col-md-12 col-sm-12 col-xs-12">
              <div className="x_panel">
                <div className="x_content">
                  <BootstrapTable data={this.state.parameterList}
                                  cellEdit={this.cellEditProp}
                                  striped hover pagination>
                    <TableHeaderColumn isKey dataField='id' dataFormat={this.columnNumberFormatter.bind(this)} dataSort={true}>#</TableHeaderColumn>
                    <TableHeaderColumn
                      dataField='channelName'
                      editable={ false }
                      dataSort={true}
                      filter={{ type: 'TextFilter' }}>
                      Channel Name
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField='name'
                                       dataSort={true}
                                       filter={{ type: 'TextFilter' }}>
                      Name
                    </TableHeaderColumn>
                    <TableHeaderColumn
                      dataField='weight'
                      dataFormat={ this.weightFormatter }>
                      Weight
                    </TableHeaderColumn>
                    <TableHeaderColumn
                        dataField="id"
                        dataFormat={ this.actionButtonFormatter.bind(this) }
                        editable={ false }>
                      Action
                    </TableHeaderColumn>
                  </BootstrapTable>
                  <button onClick={this.batchSaveParameterWeight.bind(this)} className="btn btn-default">Save weight data</button>
                  <Link to={ "/view/parameter-detail/create" }
                        className="btn btn-success">
                    Create new Parameter
                  </Link>
                </div>
              </div>
            </div>

          </div>
        </div>
      </div>
    );
  }
}

export default ParameterManagement;
