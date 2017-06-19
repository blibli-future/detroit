import React from 'react';
import AuthenticationService from '../services/AuthenticationService'

class BaseDetroitComponent extends React.Component {
  constructor(props) {
    super(props);
    this.auth = AuthenticationService.instance;
  }
}

export default BaseDetroitComponent;
