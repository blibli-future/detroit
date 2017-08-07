import React from 'react';
import { Link } from 'react-router-dom';
import { BootstrapTable, TableHeaderColumn } from 'react-bootstrap-table';

import BaseDetroitComponent from './BaseDetroitComponent';

class AgentList extends BaseDetroitComponent {

  constructor(props) {
    super(props);
    this.state = {
      agentList: [{
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
        agentChannel: '',
        agentPosition: '',
      }]
    };
    this.deleteAgent = this.deleteAgent.bind(this);
    this.columnNumberFormatter = this.columnNumberFormatter.bind(this);
    this.actionFormatter = this.actionFormatter.bind(this);
  }

  componentDidMount() {
    this.getAgentData();
  }

  getAgentData() {
    let component = this;
    this.auth.apiCall('/api/v1/users?type=AGENT', {
      method: 'GET'
    }).then((response) => response.json())
      .then((json) => {
        component.setState({
          agentList: json.content
        });
      });
  }

  deleteAgent(agent) {
    let component = this;
    this.auth.apiCall('/api/v1/users/' + agent.id, {
      method: 'DELETE'
    }).then((response) => component.getAgentData());
  }

  columnNumberFormatter(cell, row, formatExtraData, index) {
    return index+1;
  }

  actionFormatter(cell, row) {
    return (
      <Link to={'/view/agent/'+cell}
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
              <h3>Agent Data</h3>
            </div>
          </div>

          <div className="clearfix" />

          <div className="row">
            <div className="col-md-12 col-sm-12 col-xs-12">
              <div className="x_panel">
                <div className="x_content">
                  <BootstrapTable data={this.state.agentList} striped pagination>
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
                    <TableHeaderColumn dataField="agentPosition"
                                       dataSort={true}
                                       filter={{ type: 'TextFilter' }}>
                      Position
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField="agentChannel"
                                       dataSort={true}
                                       filter={{ type: 'TextFilter' }}>
                      Channel
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


export default AgentList;
