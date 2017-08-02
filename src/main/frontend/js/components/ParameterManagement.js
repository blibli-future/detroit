import React from 'react';
import { Link } from 'react-router-dom';
import {BootstrapTable, TableHeaderColumn} from 'react-bootstrap-table';

import BaseDetroitComponent from './BaseDetroitComponent';

class ParameterManagement extends BaseDetroitComponent {

  constructor(props) {
    super(props);
    this.state = {
      parameterList: []
    };
    this.deleteAgent = this.deleteAgent.bind(this);
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

  deleteAgent(agent) {
    let component = this;
    this.auth.apiCall('/api/v1/users/' + agent.id, {
      method: 'DELETE'
    }).then((response) => component.getAgentData());
  }

  channelFormatter(agentChannel, row) {
    return agentChannel.name;
  }

  weightFormatter(weight, row) {
    return weight + "%";
  }

  actionButtonFormatter(id, row) {
    return (
      <Link to={'/view/parameter-detail/' + id}
            className="btn btn-info btn-xs">
        Detail
      </Link>
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
                  <BootstrapTable data={this.state.parameterList} striped hover>
                    <TableHeaderColumn isKey dataField='id'>ID</TableHeaderColumn>
                    <TableHeaderColumn
                      dataField='agentChannel'
                      dataFormat={ this.channelFormatter } >
                      Channel Name
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField='name'>Name</TableHeaderColumn>
                    <TableHeaderColumn
                      dataField='weight'
                      dataFormat={ this.weightFormatter }>
                      Weight
                    </TableHeaderColumn>
                    <TableHeaderColumn
                        dataField="id"
                        dataFormat={ this.actionButtonFormatter }>
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

export default ParameterManagement;
