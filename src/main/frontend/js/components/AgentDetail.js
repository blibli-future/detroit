import React from 'react';

import BaseDetroitComponent from './BaseDetroitComponent';
import { Link } from 'react-router-dom';

class AgentDetail extends BaseDetroitComponent {

  constructor(props) {
    super(props);
    this.state = {
      agent: {}
    };

    this.getAgentData();
  }

  getAgentData() {
    let component = this;
    fetch('/api/v1/users/' + this.props.match.params.agentId, {
      method: 'GET',
      headers: {
        'Authorization': 'Basic '+btoa('agent@example.com:secret'),
      },
    }).then((response) => response.json())
      .then((json) => {
        component.setState({
          agent: json.content
        });
      })

  }

  render() {
    return (
      <div className="right_col" role="main">
        <div className="">
          <div className="page-title">
            <div className="title_left">
              <h3>Agent Detail</h3>
            </div>

          </div>
          <div className="clearfix"></div>
          <div className="row">
            <div className="col-md-12 col-sm-12 col-xs-12">
              <div className="x_panel">
                <div className="x_content">
                  <br />
                  <form id="demo-form2" data-parsley-validate className="form-horizontal form-label-left">

                    <div className="form-group">
                      <label className="control-label col-md-3 col-sm-3 col-xs-12">
                        Profile Picture <span className="required">*</span>
                      </label>
                      <div className="col-md-6 col-sm-6 col-xs-12">
                        <img src="https://qph.ec.quoracdn.net/main-qimg-220ae86dafbf81b8227586161b2aee61-c?convert_to_webp=true"
                             width="250"/>
                      </div>
                    </div>

                    <div className="form-group">
                      <label className="control-label col-md-3 col-sm-3 col-xs-12" htmlFor="fullname">
                        Fullname <span className="required">*
                      </span>
                      </label>
                      <div className="col-md-6 col-sm-6 col-xs-12">
                        <input type="text" required="required" name="fullname"
                               className="form-control col-md-7 col-xs-12" readOnly
                               value={ this.state.agent.fullname } />
                      </div>
                    </div>

                    <div className="form-group">
                      <label className="control-label col-md-3 col-sm-3 col-xs-12" htmlFor="nickname">
                        Nickname <span className="required">*</span>
                      </label>
                      <div className="col-md-6 col-sm-6 col-xs-12">
                        <input type="text" name="nickname" required="required"
                               className="form-control col-md-7 col-xs-12" readOnly
                               value={ this.state.agent.nickname }/>
                      </div>
                    </div>

                    <div className="form-group">
                      <label htmlFor="email" className="control-label col-md-3 col-sm-3 col-xs-12">
                        Email
                      </label>
                      <div className="col-md-6 col-sm-6 col-xs-12">
                        <input className="form-control col-md-7 col-xs-12" type="email"
                               name="email" readOnly
                               value={ this.state.agent.email } />
                      </div>
                    </div>

                    <div className="form-group">
                      <label htmlFor="phoneNumber" className="control-label col-md-3 col-sm-3 col-xs-12">
                        Phone Number
                      </label>
                      <div className="col-md-6 col-sm-6 col-xs-12">
                        <input id="phoneNumber" className="form-control col-md-7 col-xs-12"
                               type="text" name="phonenumber" readOnly
                               value={ this.state.agent.phoneNumber }/>
                      </div>
                    </div>

                    <div className="form-group">
                      <label htmlFor="location" className="control-label col-md-3 col-sm-3 col-xs-12">
                        Location
                      </label>
                      <div className="col-md-6 col-sm-6 col-xs-12">
                        <input id="location" className="form-control col-md-7 col-xs-12"
                               type="text" name="location" readOnly
                               value={ this.state.agent.location }/>
                      </div>
                    </div>

                    <div className="form-group">
                      <label htmlFor="agentPosition" className="control-label col-md-3 col-sm-3 col-xs-12">
                        Position
                      </label>
                      <div className="col-md-6 col-sm-6 col-xs-12">
                        <input className="form-control col-md-7 col-xs-12"
                               type="text" name="agentPosition" readOnly
                               value={ this.state.agent.agentPosition } />
                      </div>
                    </div>

                    <div className="form-group">
                      <label htmlFor="agentChannel" className="control-label col-md-3 col-sm-3 col-xs-12">
                        Channel
                      </label>
                      <div className="col-md-6 col-sm-6 col-xs-12">
                        <input className="form-control col-md-7 col-xs-12"
                               type="text" name="agentChannel" readOnly
                               value={ this.state.agent.agentChannel } />
                      </div>
                    </div>

                    <div className="form-group">
                      <label htmlFor="teamLeader" className="control-label col-md-3 col-sm-3 col-xs-12">
                        Team Leader
                      </label>
                      <div className="col-md-6 col-sm-6 col-xs-12">
                        <input id="teamLeader" className="form-control col-md-7 col-xs-12"
                               name="teamLeader" type="text" readOnly
                               value={ this.state.agent.teamLeader } />
                      </div>
                    </div>
                    <div className="form-group">
                      <label className="control-label col-md-3 col-sm-3 col-xs-12">Gender</label>
                      <div className="col-md-6 col-sm-6 col-xs-12">
                        <select value={this.state.agent.gender} readOnly>
                          <option value="MALE">Pria</option>
                          <option value="FEMALE">Wanita</option>
                          <option value="UNSPECIFIED">-</option>
                        </select>
                      </div>
                    </div>
                    <div className="form-group">
                      <label className="control-label col-md-3 col-sm-3 col-xs-12">
                        Date Of Birth <span className="required">*</span>
                      </label>
                      <div className="col-md-6 col-sm-6 col-xs-12">
                        <input className="date-picker form-control col-md-7 col-xs-12" required="required"
                               name="dateOfBirth" type="text" readOnly
                               value={ this.state.agent.dateOfBirth } />
                      </div>
                    </div>
                    <div className="ln_solid"></div>
                    <div className="form-group">
                      <div className="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                        <Link to="/view/agent-list" className="btn btn-default" >
                          Back
                        </Link>
                        <button type="submit" className="btn btn-warning">Edit</button>
                        <button type="submit" className="btn btn-danger">Delete</button>
                      </div>
                    </div>

                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default AgentDetail;
