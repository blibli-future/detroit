import React from 'react';
import Select from 'react-select';
import ReactSummernote from 'react-summernote';
import 'react-summernote/dist/react-summernote.css';

export class InputText extends React.Component {
  constructor(props) {
    super(props);
    this.state = {}
  }

  render() {
    return (
      <div className="form-group">
        <label htmlFor={this.props.name} className="control-label col-md-3 col-sm-3 col-xs-12">
          {this.props.label}
        </label>
        <div className="col-md-6 col-sm-6 col-xs-12">
          <input id={this.props.name} className="form-control col-md-7 col-xs-12"
                 type={ this.props.inputType? this.props.inputType :"text" } name={this.props.name}
                 onChange={ this.props.onChange }
                 value={ this.props.data }
                 required= { this.props.required }
          />
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
        <Select name={this.props.name} className="col-md-6 col-sm-6 col-xs-12"
                value={this.props.value}
                options={this.props.options}
                onChange={this.props.onChange}
                disabled={this.props.disabled? this.props.disabled : false}/>
      </div>
    );
  }
}

export class InputTextArea extends React.Component {

  handleInputChange(contents) {
    let event = {
      target: {
        type: 'summernote',
        name: this.props.name,
        value: contents
      }
    }
    this.props.onChange(event);
  }

  render() {
    return (
      <ReactSummernote
        value={ this.props.content? this.props.content :"" }
        options={{
          height: this.props.height,
          dialogsInBody: true,
          toolbar: [
            ['style', ['style']],
            ['font', ['bold', 'underline', 'clear']],
            ['fontname', ['fontname']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['table', ['table']],
            ['insert', ['link', 'picture', 'video']],
            ['view', ['fullscreen', 'codeview']]
          ]
        }}
        onChange={ this.handleInputChange.bind(this) }
      />
    )
  }
}
