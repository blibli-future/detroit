import React from 'react';

import BaseDetroitComponent from './BaseDetroitComponent';
import { InputText } from '../containers/GantellelaTheme';

export default class CategoryDetail extends BaseDetroitComponent {

  constructor(props) {
    super(props);
    this.handleInputChange = this.handleInputChange.bind(this);
  }

  handleInputChange(event) {
    this.props.handleInputChange(event, this.props.index);
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
        <InputText data={this.props.category.description}
                   onChange={this.handleInputChange}
                   name="description"
                   label="Description" />

        <div className="form-group">
          <div className="col-md-offset-3 col-md-6 col-sm-6 col-xs-12">
            <button className="btn btn-small btn-danger">Delete this category</button>
          </div>
        </div>
        <hr />
      </div>
    );
  }
}
