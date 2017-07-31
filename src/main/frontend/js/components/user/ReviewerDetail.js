import React from 'react';
import { Link } from 'react-router-dom';

import BaseDetroitComponent from '../BaseDetroitComponent';
import UserDetail from './UserDetail';

class ReviewerDetail extends BaseDetroitComponent {

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
      </UserDetail>
    )
  }
}

export default ReviewerDetail;
