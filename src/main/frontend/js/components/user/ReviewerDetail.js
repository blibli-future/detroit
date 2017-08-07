import React from 'react';
import { Link } from 'react-router-dom';
import Select from 'react-select';
import { withRouter } from 'react-router';

import BaseDetroitComponent from '../BaseDetroitComponent';
import UserDetail from './UserDetail';
import {InputCheckbox} from "../../containers/GantellelaTheme";

class ReviewerDetail extends BaseDetroitComponent {

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
        userType: '',
        teamLeader: '',
        reviewerRole: [''],
        superAdmin: false,
      },
      editMode: false,
      createMode: false,
      roleList: [],
    };
    this.handleInputChange = this.handleInputChange.bind(this);
    this.switchEditMode = this.switchEditMode.bind(this);
    this.saveEditData = this.saveEditData.bind(this);
    this.deleteUser = this.deleteUser.bind(this);
    this.createReviewer = this.createReviewer.bind(this);
  }

  componentDidMount(){
    this.getRoleList();

    if (this.props.match.params.reviewerId === 'create') {
      this.setState({editMode:true, createMode:true});
    } else {
      this.getUserData();
    }
  }

  getUserData() {
    let component = this;
    return this.auth.apiCall('/api/v1/users/' + this.props.match.params.reviewerId, {
      method: 'GET',
    }).then((response) => response.json())
      .then((json) => {
        component.setState({user: json.content})
      });
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
    let component = this;
    return this.auth.apiCall('/api/v1/users/reviewer/' + this.state.user.id, {
      method: 'PATCH',
      body: JSON.stringify(this.state.user)
    }).then((response) => response.json())
      .then((json) => {
        if (json.success) {
          swal("Success", "User data has been edited.", "success");
          component.setState({editMode: false});
          component.getUserData();
        } else {
          swal('error', json.errorMessage, 'error');
        }
      });
  }

  deleteUser(event) {
    event.preventDefault();
    swal({
      title: 'Warning',
      text: 'Are you sure want to delete this user?',
      type: 'warning',
    }, () => {
      this.auth.apiCall('/api/v1/users/' + this.state.user.id, {
        method: 'DELETE'
      }).then((response) => response.json())
        .then(json => {
          if (json.success) {
            this.props.history.push('/view/reviewer-list');
          } else {
            swal('Error', json.errorMessage, 'error');
          }
        });
    });
  }

  createReviewer(event) {
    event.preventDefault();
    let component = this;
    return this.auth.apiCall('/api/v1/users/reviewer/', {
      method: 'POST',
      body: JSON.stringify(component.state.user)
    }).then((response) => response.json())
      .then((json) => {
        if (json.success) {
          swal("Success", "New user has been created", "success");
          component.setState({editMode: false, createMode: false});
          component.props.history.push('/view/reviewer-list');
        } else {
          swal('error', json.errorMessage, 'error');
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
                  createMode={this.state.createMode}
                  createUser={this.createReviewer}
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

        <InputCheckbox name="superAdmin"
                       label="Super Admin status"
                       value={this.state.user.superAdmin}
                       onChange={this.handleInputChange}
                       disabled={!this.props.editMode} />

      </UserDetail>
    )
  }
}

export default withRouter(ReviewerDetail);
