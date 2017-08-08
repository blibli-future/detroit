import React from 'react';
import { withRouter } from 'react-router';
import { Link } from 'react-router-dom';
import swal from 'sweetalert';

import BaseDetroitComponent from '../BaseDetroitComponent';
import UserDetail from './UserDetail';

class AgentDetail extends BaseDetroitComponent {

  constructor(props) {
    super(props);
    this.state = {
      user: {
        id: 0,
        fullname: '',
        nickname: '',
        email: '',
        dateOfBirth: '',
        gender: '',
        location: '',
        phoneNumber: '',
        userType: 'AGENT',
        teamLeader: '',
        agentChannel: '',
        agentPosition: '',
      },
      editMode: false,
      createMode: false,
    };
    this.handleInputChange = this.handleInputChange.bind(this);
    this.switchEditMode = this.switchEditMode.bind(this);
    this.saveEditData = this.saveEditData.bind(this);
    this.deleteUser = this.deleteUser.bind(this);
    this.createAgent = this.createAgent.bind(this);
  }

  componentDidMount(){
    if (this.props.match.params.userId === 'create') {
      this.setState({editMode:true, createMode:true});
    } else {
      this.getUserData();
    }
  }

  getUserData() {
    let component = this;
    return this.auth.apiCall('/api/v1/users/' + this.props.match.params.userId, {
      method: 'GET',
    }).then((response) => response.json())
      .then((json) => {
        component.setState({user: json.content})
      });
  }

  saveEditData(event) {
    event.preventDefault();
    let component = this;
    return this.auth.apiCall('/api/v1/users/agent/' + this.state.user.id, {
      method: 'PATCH',
      body: JSON.stringify(this.state.user)
    }).then((response) => response.json())
      .then((json) => {
        if (json.success) {
          swal("Success", "User data has been edited.", "success");
          component.setState({editMode: false});
          component.getUserData();
        } else {
          swal('error', json.errorMessage || json.message, 'error');
        }
      });
  }

  deleteUser(event) {
    event.preventDefault();
    swal({
      title: 'Warning',
      text: 'Are you sure want to delete this agent?',
      type: 'warning',
    }, () => {
      return this.auth.apiCall('/api/v1/users/' + this.state.user.id, {
        method: 'DELETE'
      }).then(response => response.json())
        .then(json => {
        if (json.success) {
          this.props.history.push('/view/agent-list');
        } else {
          swal('Error', json.errorMessage || json.message, 'error');
        }
      });
    });
  }

  createAgent(event) {
    event.preventDefault();
    let component = this;
    // Make sure id is not set
    const user = this.state.user;
    user.id = '';
    this.setState({user});
    return this.auth.apiCall('/api/v1/users/agent/', {
      method: 'POST',
      body: JSON.stringify(this.state.user)
    }).then((response) => response.json())
      .then((json) => {
        if (json.success) {
          swal("Success", "New agent has been created", "success");
          component.setState({editMode: false, createMode: false});
          this.props.history.push('/view/agent-list');
        } else {
          swal('error', json.errorMessage || json.message, 'error');
        }
      });
  }

  handleInputChange(event) {
    const target = event.target;
    const value = target.type === 'checkbox' ? target.checked : target.value;
    const name = target.name;

    const user = this.state.user;
    user[name] = value;
    this.setState({
      user
    });
  }

  switchEditMode(event) {
    event.preventDefault();
    this.setState({
      editMode: !this.state.editMode
    })
  }

  render() {
    return (
      <UserDetail title="Agent Detail"
                  backLink="/view/agent-list"
                  user={ this.state.user }
                  editMode={ this.state.editMode }
                  switchEditMode={ this.switchEditMode }
                  onChange={ this.handleInputChange }
                  saveEditData={ this.saveEditData }
                  deleteUser={ this.deleteUser }
                  createMode={this.state.createMode}
                  createUser={this.createAgent}
                  { ...this.props }>
        <div className="form-group">
          <label htmlFor="agentPosition" className="control-label col-md-3 col-sm-3 col-xs-12">
            Position
          </label>
          <div className="col-md-6 col-sm-6 col-xs-12">
            <input className="form-control col-md-7 col-xs-12"
                   type="text" name="agentPosition" readOnly={!this.state.editMode}
                   value={ this.state.user.agentPosition }
                   onChange={ this.handleInputChange } />
          </div>
        </div>

        <div className="form-group">
          <label htmlFor="agentChannel" className="control-label col-md-3 col-sm-3 col-xs-12">
            Channel
          </label>
          <div className="col-md-6 col-sm-6 col-xs-12">
            <input className="form-control col-md-7 col-xs-12"
                   type="text" name="agentChannel" readOnly={!this.state.editMode}
                   value={ this.state.user.agentChannel }
                   onChange={ this.handleInputChange } />
          </div>
        </div>

        <div className="form-group">
          <label htmlFor="teamLeader" className="control-label col-md-3 col-sm-3 col-xs-12">
            Team Leader
          </label>
          <div className="col-md-6 col-sm-6 col-xs-12">
            <input id="teamLeader" className="form-control col-md-7 col-xs-12"
                   name="teamLeader" type="text" readOnly={!this.state.editMode}
                   value={ this.state.user.teamLeader }
                   onChange={ this.handleInputChange } />
          </div>
        </div>
      </UserDetail>
    )
  }
}

export default withRouter(AgentDetail);
