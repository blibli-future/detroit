import React from 'react';
import { Link } from 'react-router-dom';

import BaseDetroitComponent from '../BaseDetroitComponent';
import {InputSelect} from "../../containers/GantellelaTheme";

class UserDetail extends BaseDetroitComponent {

  constructor(props) {
    super(props);

    this.buttonsInEditMode = (
      <div className="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
        <Link to={ this.props.backLink } className="btn btn-default" >
          Back
        </Link>
        <button className="btn btn-warning"
                onClick={this.props.switchEditMode}>Cancel Edit</button>
        <button type="submit" className="btn btn-success"
                onClick={ this.props.saveEditData }>
          Save Change</button>
      </div>
    );

    this.buttonsInViewMode = (
      <div className="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
        <Link to={ this.props.backLink } className="btn btn-default">
          Back
        </Link>
        <button className="btn btn-warning"
                onClick={this.props.switchEditMode}>
          Edit
        </button>
        <button type="submit" className="btn btn-danger"
                onClick={ this.props.deleteUser }>
          Delete User
        </button>
      </div>
    );

    this.buttonsInCreateMode = (
      <div className="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
        <button className="btn btn-warning"
                onClick={this.props.createUser}>
          Create user
        </button>
      </div>
    );

    this.genderOptions = [
      {label: 'Male', value: 'PRIA'},
      {label: 'Female', value: 'WANITA'},
      {label: '-', value: 'UNSPECIFIED'},
    ];

    this.buttons = this.buttonsInViewMode
  }

  handleGenderChange(data) {
    let event = {
      target: {
        type: 'select',
        name: 'gender',
        value: data.value,
      }
    };
    this.props.onChange(event);
  }

  render() {
    if (this.props.editMode && !this.props.createMode) {
      this.buttons = this.buttonsInEditMode
    } else if (this.props.editMode && this.props.createMode) {
      this.buttons = this.buttonsInCreateMode;
    } else {
      this.buttons = this.buttonsInViewMode
    }

    return (
      <div className="right_col" role="main">
        <div className="">
          <div className="page-title">
            <div className="title_left">
              <h3>{ this.props.title }</h3>
            </div>
          </div>

          <div className="clearfix" />
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
                               className="form-control col-md-7 col-xs-12"
                               readOnly={!this.props.editMode}
                               onChange={ this.props.onChange }
                               value={ this.props.user.fullname } />
                      </div>
                    </div>

                    <div className="form-group">
                      <label className="control-label col-md-3 col-sm-3 col-xs-12" htmlFor="nickname">
                        Nickname <span className="required">*</span>
                      </label>
                      <div className="col-md-6 col-sm-6 col-xs-12">
                        <input type="text" name="nickname" required="required"
                               className="form-control col-md-7 col-xs-12"
                               readOnly={!this.props.editMode}
                               onChange={ this.props.onChange }
                               value={ this.props.user.nickname }/>
                      </div>
                    </div>

                    <div className="form-group">
                      <label htmlFor="email" className="control-label col-md-3 col-sm-3 col-xs-12">
                        Email
                      </label>
                      <div className="col-md-6 col-sm-6 col-xs-12">
                        <input className="form-control col-md-7 col-xs-12" type="email"
                               name="email"
                               readOnly={!this.props.editMode}
                               onChange={ this.props.onChange }
                               value={ this.props.user.email } />
                      </div>
                    </div>

                    <div className="form-group">
                      <label htmlFor="phoneNumber" className="control-label col-md-3 col-sm-3 col-xs-12">
                        Phone Number
                      </label>
                      <div className="col-md-6 col-sm-6 col-xs-12">
                        <input id="phoneNumber" className="form-control col-md-7 col-xs-12"
                               type="text" name="phoneNumber"
                               readOnly={!this.props.editMode}
                               onChange={ this.props.onChange }
                               value={ this.props.user.phoneNumber }/>
                      </div>
                    </div>

                    <div className="form-group">
                      <label htmlFor="location" className="control-label col-md-3 col-sm-3 col-xs-12">
                        Location
                      </label>
                      <div className="col-md-6 col-sm-6 col-xs-12">
                        <input id="location" className="form-control col-md-7 col-xs-12"
                               type="text" name="location"
                               readOnly={!this.props.editMode}
                               onChange={ this.props.onChange }
                               value={ this.props.user.location }/>
                      </div>
                    </div>

                    <InputSelect name="gender"
                                 label="Gender"
                                 value={this.props.user.gender}
                                 options={this.genderOptions}
                                 onChange={this.handleGenderChange.bind(this)}
                                 disabled={!this.props.editMode} />

                    <div className="form-group">
                      <label className="control-label col-md-3 col-sm-3 col-xs-12">
                        Date Of Birth
                      </label>
                      <div className="col-md-6 col-sm-6 col-xs-12">
                        <input className="date-picker form-control col-md-7 col-xs-12"
                               name="dateOfBirth" type="text"
                               readOnly={!this.props.editMode}
                               onChange={ this.props.onChange }
                               value={ this.props.user.dateOfBirth } />
                      </div>
                    </div>

                    { this.props.children }

                    <div className="ln_solid" />
                    <div className="form-group">
                      { this.buttons }
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

export default UserDetail;
