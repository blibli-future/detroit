import React from 'react';
import { Link } from 'react-router-dom';

import BaseDetroitComponent from '../BaseDetroitComponent';
import UserDetail from './UserDetail';

class ReviewerDetail extends BaseDetroitComponent {

  constructor(props) {
    super(props);
    this.state = {
      user: {}
    };
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

  render() {
    return (
      <UserDetail title="Reviewer Detail"
                  backLink="/view/reviewer-list"
                  user={this.state.user}
                  {...this.props}>
      </UserDetail>
    )
  }
}

export default ReviewerDetail;
