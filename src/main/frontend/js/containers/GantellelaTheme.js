import React from 'react';
import Select from 'react-select';

export class InputText extends React.Component {
  render() {
    return (
      <div className="form-group">
        <label htmlFor={this.props.name} className="control-label col-md-3 col-sm-3 col-xs-12">
          {this.props.label}
        </label>
        <div className="col-md-6 col-sm-6 col-xs-12">
          <input id={this.props.name} className="form-control col-md-7 col-xs-12"
                 type="text" name={this.props.name}
                 onChange={ this.props.onChange }
                 value={ this.props.data }/>
        </div>
      </div>
    );
  }
}

export class InputSelect extends React.Component {
  render() {
    return (
      <div className="form-group">
        <label htmlFor={this.props.name} className="control-label col-md-3 col-sm-3 col-xs-12">
          {this.props.label}
        </label>
        <Select name="categories" className="col-md-6 col-sm-6 col-xs-12"
                value={this.props.value}
                options={this.props.options}
                onChange={this.props.onChange} />
      </div>
    );
  }
}
