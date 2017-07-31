import React from 'react';
import { Link } from 'react-router-dom';

import BaseDetroitComponent from '../BaseDetroitComponent';
import UserDetail from './UserDetail';

class AgentDetail extends BaseDetroitComponent {

  constructor(props) {
    super(props);
    this.state = {
      user: {},
      editMode: false
    };
    this.handleInputChange = this.handleInputChange.bind(this);
    this.switchEditMode = this.switchEditMode.bind(this);
    this.saveEditData = this.saveEditData.bind(this);
    this.deleteUser = this.deleteUser.bind(this);
  }

  componentDidMount(){
    let component = this;
    this.getUserData(this.props.match.params.userId)
      .then(data => {
        component.setState({user: data})
      });
  }

  getUserData(id) {
    return this.auth.apiCall('/api/v1/users/' + id, {
      method: 'GET',
    }).then((response) => response.json())
      .then((json) => {
        return json.content;
      })
  }

  saveEditData(event) {
    event.preventDefault();
    return this.auth.apiCall('/api/v1/users/reviewer/' + this.state.user.id, {
      method: 'PATCH',
      body: JSON.stringify(this.state.user)
    }).then((response) => response.json())
      .then((json) => {
        return json.content;
      }).bind(this)
  }

  deleteUser(event) {
    event.preventDefault();
    this.auth.apiCall('/api/v1/users/' + this.state.user.id, {
      method: 'DELETE'
    }).then((response) => {
      if (response.status) {
        window.location.assign("/view/agent-list");
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
    var opts = {};
    if(!this.state.editMode) {
      opts['readOnly'] = 'readOnly';
    }

    return (
      <UserDetail title="Reviewer Detail"
                  backLink="/view/reviewer-list"
                  user={ this.state.user }
                  editMode={ this.state.editMode }
                  switchEditMode={ this.switchEditMode }
                  onChange={ this.handleInputChange }
                  saveEditData={ this.saveEditData }
                  deleteUser={ this.deleteUser }
                  { ...this.props }>
        <div className="form-group">
          <label htmlFor="agentPosition" className="control-label col-md-3 col-sm-3 col-xs-12">
            Position
          </label>
          <div className="col-md-6 col-sm-6 col-xs-12">
            <input className="form-control col-md-7 col-xs-12"
                   type="text" name="agentPosition" {...opts}
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
                   type="text" name="agentChannel" {...opts}
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
                   name="teamLeader" type="text" {...opts}
                   value={ this.state.user.teamLeader }
                   onChange={ this.handleInputChange } />
          </div>
        </div>
      </UserDetail>
    )
  }
}

export default AgentDetail;
