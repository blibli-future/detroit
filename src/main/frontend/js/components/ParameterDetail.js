import React from 'react';
import swal from 'sweetalert';

import BaseDetroitComponent from './BaseDetroitComponent';
import { InputText, InputSelect } from '../containers/GantellelaTheme';
import CategoryDetail from './CategoryDetail';

class ParameterDetail extends BaseDetroitComponent {

  constructor(props) {
    super(props);
    this.state = {
      parameter: {
        id: 0,
        name: '',
        weight: 0,
        description: '',
        agentChannelId: 0,
        categories: [{
          id: 0,
          name: '',
          weight: 0,
          description: '',
        }],
      },
      agentChannelPosition: [{
        name: '',
        value: '',
      }],
    };
    this.handleInputChange = this.handleInputChange.bind(this);
    this.handleCategoryInputChange = this.handleCategoryInputChange.bind(this);
    this.handleSave = this.handleSave.bind(this);
    this.getParameterData();
    this.getAgentChannelPositionData();
  }

  getParameterData() {
    let component = this;
    this.auth.apiCall('/api/v1/parameters/' + this.props.match.params.parameterId, {
      method: 'GET'
    }).then((response) => response.json())
      .then((json) => {
        component.setState({
          parameter: json.content
        });
      });
  }

  getAgentChannelPositionData() {
    let component = this;
    this.auth.apiCall('/api/v1/agent-channels/with-position', {
      method: 'GET'
    }).then((response) => response.json())
      .then((json) => {
        component.setState({
          agentChannelPosition: json.content.map((data) => {
            return {value: data.id, label: data.name}})
        });
      });
  }

  handleInputChange(event) {
    const target = event.target;
    const value = target.type === 'checkbox' ? target.checked : target.value;
    const name = target.name;

    const parameter = this.state.parameter;
    parameter[name] = value;
    this.setState({
      parameter
    });
  }

  handleChannelChange(data) {
    const parameter = this.state.parameter;
    parameter.agentChannelId = data.value;
    this.setState({
      parameter
    })
  }

  handleCategoryInputChange(event, categoryCounter) {
    const target = event.target;
    const value = target.type === 'checkbox' ? target.checked : target.value;
    const name = target.name;

    const parameter = this.state.parameter;
    parameter.categories[categoryCounter][name] = value;

    this.setState({
      parameter
    });
  }

  handleSave(event) {
    event.preventDefault();
    // validate
    let totalWeight = this.state.parameter.categories.reduce((sum, category) => {
      return sum + Number(category.weight);
    }, 0);
    if (Math.abs(totalWeight - 100) > 0.001) {
      return swal("Error", "Total weight of categories is not 100%.", "error");
    }

    // Save to API
    let component = this;
    this.auth.apiCall('/api/v1/parameters/' + this.state.parameter.id, {
      method: 'PUT',
      body: JSON.stringify(component.state.parameter),
    }).then((response) => response.json())
      .then((json) => {
        if (json.success) {
          swal("Success", "Parameter data has been saved.", "success");
        } else {
          return swal("Error", json.errorMessage, "error");
        }
      });
  }

  render() {
    let categoryComponents = this.state.parameter.categories.map((category, index) => {
      return <CategoryDetail key={index}
                             index={index}
                             category={category}
                             handleInputChange={this.handleCategoryInputChange} />
    });
    return (
      <div className="right_col" role="main">
        <div className="">
          <div className="page-title">
            <div className="title_left">
              <h3>Parameter detail</h3>
            </div>
          </div>

          <div className="clearfix" />
          <div className="row">
            <div className="col-md-12 col-sm-12 col-xs-12">
              <div className="x_panel">
                <div className="x_content">
                  <br />
                  <form id="form" data-parsley-validate className="form-horizontal form-label-left">
                    <InputText data={this.state.parameter.name}
                               onChange={this.handleInputChange}
                               name="name"
                               label="Name" />
                    <InputText data={this.state.parameter.weight}
                               name="weight"
                               label="Weight"/>
                    <InputText data={this.state.parameter.description}
                               onChange={this.handleInputChange}
                               name="description"
                               label="Description" />
                    <InputSelect name="agentChannelId"
                                 label="Position / Channel"
                                 value={this.state.parameter.agentChannelId}
                                 options={this.state.agentChannelPosition}
                                 onChange={this.handleChannelChange.bind(this)} />

                    <div className="ln_solid" />

                    <div className="form-group">
                      <div className="col-md-offset-3 col-md-6 col-sm-6 col-xs-12">
                        <h2>Categories</h2>
                      </div>
                    </div>

                    {categoryComponents}

                    <div className="ln_solid" />
                    <div className="form-group">
                      <button className="btn btn-success"
                              onClick={this.handleSave}>
                        Save Changes
                      </button>
                    </div>

                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    )
  }
}

export default ParameterDetail;
