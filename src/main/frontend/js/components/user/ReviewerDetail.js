import React from 'react';
import { Link } from 'react-router-dom';
import Select from 'react-select';

import BaseDetroitComponent from '../BaseDetroitComponent';
import UserDetail from './UserDetail';

class ReviewerDetail extends BaseDetroitComponent {

  constructor(props) {
    super(props);
    this.state = {
      user: {},
      editMode: false,
      roleList: [],
    };
    this.handleInputChange = this.handleInputChange.bind(this);
    this.switchEditMode = this.switchEditMode.bind(this);
    this.saveEditData = this.saveEditData.bind(this);
    this.deleteUser = this.deleteUser.bind(this);
  }

  componentDidMount(){
    let component = this;
    this.getRoleList();
    this.getUserData(this.props.match.params.reviewerId)
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

  getRoleList() {
    let component = this;
    return this.auth.apiCall('/api/v1/users/role-list', {
      method: 'GET',
    }).then((response) => response.json())
      .then((json) => {
        component.setState({
          roleList: json.content.map((roleName) => {
            return {value: "PARAM " + roleName, label: roleName}
          })
        });
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
        window.location.assign("/view/reviewer-list");
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

  handleRoleChange(roleList) {
    const user = this.state.user;
    user.reviewerRole = roleList.map((item) => {return item.value});
    this.setState({
      user
    })
  }

  switchEditMode(event) {
    event.preventDefault();
    this.setState({
      editMode: !this.state.editMode
    })
  }

  render() {
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
          <label htmlFor="reviewerRole" className="control-label col-md-3 col-sm-3 col-xs-12">
            Reviewer Role
          </label>
          <div className="col-md-6 col-sm-6 col-xs-12">
            <Select name="reviewerRole"
                    value={this.state.user.reviewerRole}
                    multi={true}
                    options={this.state.roleList}
                    onChange={this.handleRoleChange.bind(this)}
                    disabled={!this.state.editMode} />
          </div>
        </div>
      </UserDetail>
    )
  }
}

export default ReviewerDetail;
