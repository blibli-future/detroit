import React from 'react';
import renderHTML from 'react-render-html';

import BaseDetroitComponent from './BaseDetroitComponent';
import { InputText, InputTextArea } from '../containers/GantellelaTheme';

export default class CategoryForm extends BaseDetroitComponent {
  constructor(props) {
    super(props);
    this.handleInputChange = this.handleInputChange.bind(this);
  }

  handleInputChange(event) {
    this.props.handleInputChange(event, this.props.id);
  }

  render() {
    return(
      <div className="form-group">
        <div className="col-md-offset-3 col-md-6 col-sm-6 col-xs-12">
          <h2>Category : { this.props.category.name }</h2>
        </div>
        <div className="col-md-offset-3 col-md-6 col-sm-6 col-xs-12">
          <h2>Weight : { this.props.category.weight }%</h2>
        </div>
        <div className="col-md-offset-3 col-md-6 col-sm-6 col-xs-12">
          <h2>Description : </h2>
          <div className="panel panel-default">
            <div class="panel-body">
              { renderHTML(this.props.category.description) }
            </div>
          </div>
        </div>
        <div className="col-md-12 col-sm-12 col-xs-12">
          <InputText data={this.props.category.score}
                     onChange={this.handleInputChange}
                     name="score"
                     label="Score"
                     inputType="number"
          />
        </div>
        <div className="col-md-12 col-sm-12 col-xs-12">
          <h2>Notes</h2>
          <InputTextArea
            content={this.props.category.note}
            onChange={this.handleInputChange}
            name="note"
            height={ 350 }
          />
        </div>
        <div className="clearfix"></div>
        <hr/>
      </div>

    )
  }
}
