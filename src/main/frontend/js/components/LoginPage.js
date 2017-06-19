import React from 'react';
import AuthenticationService from '../services/AuthenticationService';

class LoginPage extends React.Component {

  constructor(props) {
    super(props);

    this.auth = AuthenticationService.instance;
    this.handleInputChange = this.handleInputChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);

    this.state = {
      email: '',
      password: ''
    }
  }

  handleInputChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;

    this.setState({
      [name]: value
    })
  }

  handleSubmit(event) {
    event.preventDefault();
    this.auth.authenticate(this.state.email, this.state.password).then((success) => {
      if(success) {
        window.location.assign("agent-list"); // TODO maybe we dont want to redirect here?
      } else {
        alert("Maaf, email atau password Anda salah."); // TODO Much nicer error message via html
      }
    });
  }

  render() {
    return (
      <div className="right_col" role="main">
        <div className="">
          <div className="page-title">
            <div className="title_left">
              <h3>Login Page</h3>
            </div>
          </div>
        </div>

        <div className="row">
          <div className="col-md-12 col-sm-12 col-xs-12">
            <div className="x_panel">
              <div className="x_content">
                <br />
                <form data-parsley-validate className="form-horizontal form-label-left" onSubmit={ this.handleSubmit }>
                  <div className="form-group">
                    <label className="control-label col-md-3 col-sm-3 col-xs-12" htmlFor="email">
                      Email <span className="required">*
                      </span>
                    </label>
                    <div className="col-md-6 col-sm-6 col-xs-12">
                      <input type="text" required="required" name="email"
                             onChange={ this.handleInputChange } value={ this.state.email }
                             className="form-control col-md-7 col-xs-12" />
                    </div>
                  </div>

                  <div className="form-group">
                    <label className="control-label col-md-3 col-sm-3 col-xs-12" htmlFor="password">
                      Password <span className="required">*
                        </span>
                    </label>
                    <div className="col-md-6 col-sm-6 col-xs-12">
                      <input type="password" required="required" name="password"
                             onChange={ this.handleInputChange } value={ this.state.password }
                             className="form-control col-md-7 col-xs-12" />
                    </div>
                  </div>
                  <div className="ln_solid"></div>
                  <div className="form-group">
                    <div className="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                      <button type="submit" className="btn btn-success">Login</button>
                    </div>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default LoginPage;
