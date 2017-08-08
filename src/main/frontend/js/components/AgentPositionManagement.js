import React from 'react';
import { Link } from 'react-router-dom';
import { BootstrapTable, TableHeaderColumn } from 'react-bootstrap-table';

import BaseDetroitComponent from './BaseDetroitComponent';
import {Button, ButtonGroup} from "react-bootstrap";

export default class AgentPositionManagement extends BaseDetroitComponent {

  constructor(props) {
    super(props);
    this.state = {
      agentPositions: [{
        id: 0,
        name: '',
      }],
    };
    this.deleteAgentPosition = this.deleteAgentPosition.bind(this);
    this.columnNumberFormatter = this.columnNumberFormatter.bind(this);
    this.actionFormatter = this.actionFormatter.bind(this);
    this.saveAgentPosition = this.saveAgentPosition.bind(this);
  }

  componentDidMount() {
    this.getData();
  }

  getData() {
    let component = this;
    this.auth.apiCall('/api/v1/agent-positions/', {
      method: 'GET'
    }).then((response) => response.json())
      .then((json) => {
        component.setState({
          agentPositions: json.content
        });
      });
  }

  deleteAgentPosition(agentPosition) {
    let component = this;
    swal({
      title: "Are you sure?",
      text: "Make sure this parameters doesn't belong to any active agent.",
      type: 'warning',
      confirmButtonColor: "#DD6B55",
      confirmButtonText: "Yes, delete it!",
      closeOnConfirm: false,
      showCancelButton: true,
      showLoaderOnConfirm: true,
    }, () => {
      this.auth.apiCall('/api/v1/agent-positions/' + agentPosition.id, {
        method: 'DELETE'
      }).then(response => response.json())
        .then(json => {
          if (json.success) {
            swal('Success', 'Parameter has been deleted.', 'success');
            component.getData();
          } else {
            swal('Error', json.errorMessage || json.message, 'error');
          }
        })
    });
  }

  saveAgentPosition(agentPosition) {
    let component = this;
    agentPosition.id = 0;
    this.auth.apiCall('/api/v1/agent-positions', {
      method: 'POST',
      body: JSON.stringify(agentPosition),
    }).then(response => response.json())
      .then(json => {
        if (json.success) {
          swal('Success', 'New parameter has been created.', 'success');
          component.getData();
        } else {
          swal('Error', json.errorMessage || json.message, 'error');
        }
      });
  }

  columnNumberFormatter(cell, row, formatExtraData, index) {
    return index+1;
  }

  actionFormatter(cell) {
    return (
      <ButtonGroup>
        <Link to={'/view/agent-channels-management/'+cell}
              className="btn btn-info btn-xs">
          Detail
        </Link>
        <Button bsStyle="danger" bsSize="xs" onClick={this.deleteAgentPosition.bind(this, cell)}>
          Delete
        </Button>
      </ButtonGroup>
    );
  }

  render() {
    return (
      <div className="right_col" role="main">
        <div className="">
          <div className="page-title">
            <div className="title_left">
              <h3>Agent Position Data</h3>
            </div>
          </div>

          <div className="clearfix" />

          <div className="row">
            <div className="col-md-12 col-sm-12 col-xs-12">
              <div className="x_panel">
                <div className="x_content">
                  <BootstrapTable data={this.state.agentPositions}
                                  striped pagination
                                  insertRow={true}
                                  options={{afterInsertRow:this.saveAgentPosition}}
                                  cellEdit={{mode: 'click'}}>
                    <TableHeaderColumn hiddenOnInsert dataField="parameters"
                                       dataFormat={this.columnNumberFormatter}
                                       editable={false}
                                       width="100">
                      #
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField="name"
                                       dataSort={true}
                                       filter={{ type: 'TextFilter' }}>
                      Position name
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField="id" hiddenOnInsert isKey autoValue={ true }
                                       dataFormat={this.actionFormatter}
                                       editable={false}>
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
