import React from 'react';

import BaseDetroitComponent from './BaseDetroitComponent';
import {InputText, InputTextArea} from '../containers/GantellelaTheme';
import {Col, ControlLabel, FormGroup} from "react-bootstrap";

export default class CategoryDetail extends BaseDetroitComponent {

  constructor(props) {
    super(props);
    this.handleInputChange = this.handleInputChange.bind(this);
  }

  handleInputChange(event) {
    this.props.handleInputChange(event, this.props.index);
  }

  onDelete(event) {
    this.props.onDelete(event, this.props.category.id);
  }

  render() {
    return (
      <div>
        <InputText data={this.props.category.name}
                   onChange={this.handleInputChange}
                   name="name"
                   label="Name" />
        <InputText data={this.props.category.weight}
                   onChange={this.handleInputChange}
                   name="weight"
                   label="Weight" />

        <FormGroup controlId="newCategory-description">
          <Col componentClass={ControlLabel} sm={3}>
            Description
          </Col>
          <Col sm={6}>
            <InputTextArea
              content={this.props.category.description}
              onChange={this.handleInputChange}
              name="description"
              height={ 350 }
            />
          </Col>
        </FormGroup>

        <div className="form-group">
          <div className="col-md-offset-3 col-md-6 col-sm-6 col-xs-12">
            <button className="btn btn-small btn-danger"
                    onClick={this.onDelete.bind(this)}>
              Delete this category
            </button>
          </div>
        </div>
        <hr />
      </div>
    );
  }
}
