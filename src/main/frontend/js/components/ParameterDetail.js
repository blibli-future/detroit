import React from 'react';
import { Modal, Form, FormControl, FormGroup, Button, Col, ControlLabel, HelpBlock } from 'react-bootstrap';
import swal from 'sweetalert';

import BaseDetroitComponent from './BaseDetroitComponent';
import {InputText, InputSelect, InputTextArea} from '../containers/GantellelaTheme';
import CategoryDetail from './CategoryDetail';
import {withRouter} from "react-router-dom";

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
      newCategory: {
        name: '',
        weight: 0,
        description: '',
      },
      unsavedChanges: false,
      showAddModal: false,
      createMode: false,
    };
    this.handleInputChange = this.handleInputChange.bind(this);
    this.handleCategoryInputChange = this.handleCategoryInputChange.bind(this);
    this.handleSave = this.handleSave.bind(this);
    this.onDeleteCategoryClick = this.onDeleteCategoryClick.bind(this);
  }

  componentDidMount() {
    this.getAgentChannelPositionData();
    if (this.props.match.params.parameterId === 'create') {
      this.setState({createMode:true});
    } else {
      this.getParameterData();
    }
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
      parameter,
      unsavedChanges: true,
    });
  }

  handleChannelChange(data) {
    const parameter = this.state.parameter;
    parameter.agentChannelId = data.value;
    this.setState({
      parameter,
      unsavedChanges: true,
    })
  }

  handleCategoryInputChange(event, categoryCounter) {
    const target = event.target;
    const value = target.type === 'checkbox' ? target.checked : target.value;
    const name = target.name;

    const parameter = this.state.parameter;
    parameter.categories[categoryCounter][name] = value;
    this.setState({
      parameter,
      unsavedChanges: true,
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
    if(this.state.createMode) {
      this.auth.apiCall('/api/v1/parameters/', {
        method: 'POST',
        body: JSON.stringify(component.state.parameter),
      }).then((response) => response.json())
        .then((json) => {
          if (json.success) {
            component.setState({
              unsavedChanges: false,
            });
            swal("Success", "Parameter data has been saved.", "success");
            this.props.history.push('/view/parameter-management');
          } else {
            return swal("Error", json.errorMessage || json.message, "error");
          }
        });
    } else {
      this.auth.apiCall('/api/v1/parameters/' + this.state.parameter.id, {
        method: 'PUT',
        body: JSON.stringify(component.state.parameter),
      }).then((response) => response.json())
        .then((json) => {
          if (json.success) {
            component.setState({
              unsavedChanges: false,
            });
            swal("Success", "Parameter data has been saved.", "success");
          } else {
            return swal("Error", json.errorMessage || json.message, "error");
          }
        });
    }
  }

  onDeleteCategoryClick(event, categoryId) {
    event.preventDefault();
    swal({
      title: 'Are you sure?',
      text: 'Deleting a category may make statistics behave incorrectly. Do this with precaution!',
      type: 'warning',
      confirmButtonColor: "#DD6B55",
      confirmButtonText: "Yes, delete it!",
      closeOnConfirm: false,
      showCancelButton: true,
      showLoaderOnConfirm: true,
    }, () => {
      this.deleteCategory(categoryId)
    });
  }

  deleteCategory(categoryId) {
    let component = this;
    this.auth.apiCall('/api/v1/parameters/' + this.state.parameter.id + '/categories/' + categoryId, {
      method: 'DELETE'
    }).then(response => response.json())
      .then(json => {
        if (json.success) {
          component.getParameterData();
          swal('Success', 'Category has been deleted.', 'success');
        } else {
          return swal('Error', json.errorMessage || json.message, 'error');
        }
      });
  }

  handleNewCategoryChange(event) {
    const target = event.target;
    const value = target.type === 'checkbox' ? target.checked : target.value;
    const name = target.id.split("-").pop();

    const newCategory = this.state.newCategory;
    newCategory[name] = value;
    this.setState({newCategory});
  }

  saveNewCategory(event) {
    event.preventDefault();
    let component = this;
    this.auth.apiCall("/api/v1/parameters/" + this.state.parameter.id + "/categories", {
      method: 'POST',
      body: JSON.stringify(component.state.newCategory),
    }).then(response => response.json())
      .then(json => {
        if (json.success) {
          component.getParameterData();
          swal({
            title: "Success",
            text: "New category has been created.",
            type: "success",
          }, () => {
            component.setState({showAddModal:false});
          });
        } else {
          swal("Error", json.errorMessage || json.message, "error");
        }
      });
  }

  closeAddModal() {
    this.setState({ showAddModal: false });
  }

  openAddModal(event) {
    event.preventDefault();
    this.setState({ showAddModal: true });
  }

  render() {
    let categoryComponents = this.state.parameter.categories.map((category, index) => {
      return <CategoryDetail key={index}
                             index={index}
                             category={category}
                             handleInputChange={this.handleCategoryInputChange}
                             onDelete={this.onDeleteCategoryClick} />
    });
    if (categoryComponents.length < 1) {
      categoryComponents =
        <div className="row">
          <div className="col-md-offset-3 col-md-9">
            <div className="alert alert-warning">This parameter doesn't have any category.</div>
          </div>
        </div>
    }
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
                    <FormGroup controlId="newCategory-description">
                      <Col componentClass={ControlLabel} sm={3}>
                        Description
                      </Col>
                      <Col sm={6}>
                        <InputTextArea
                          content={this.state.parameter.description}
                          onChange={this.handleInputChange}
                          name="description"
                          height={ 350 }
                        />
                      </Col>
                    </FormGroup>
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

                    {!this.state.createMode && categoryComponents}

                    <div className="clearfix" />
                    <div className="ln_solid" />
                    { this.state.unsavedChanges &&
                    <p className="text-warning">You have unsaved changes</p>
                    }
                    <div className="form-group">
                      <button className="btn btn-success"
                              onClick={this.handleSave}
                              disabled={!this.state.unsavedChanges}>
                        Save Changes
                      </button>
                      { !this.state.createMode &&
                      <button className="btn"
                              onClick={this.openAddModal.bind(this)}>
                        Add new category
                      </button>
                      }
                    </div>
                  </form>

                  <AddNewCategoryModal show={this.state.showAddModal}
                                       newCategory={this.state.newCategory}
                                       onChange={this.handleNewCategoryChange.bind(this)}
                                       onSubmit={this.saveNewCategory.bind(this)}
                                       close={this.closeAddModal.bind(this)}
                                       />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

class AddNewCategoryModal extends React.Component {
  render() {
    return (
      <Modal show={this.props.show}>
        <Modal.Header closeButton onClick={this.props.close}>
          <Modal.Title>Add new category</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form horizontal>

            <FormGroup controlId="newCategory-name">
              <Col componentClass={ControlLabel} sm={2}>
                Name
              </Col>
              <Col sm={10}>
                <FormControl type="text"
                             placeholder="Category name"
                             value={this.props.newCategory.name}
                             onChange={this.props.onChange}
                />
                <FormControl.Feedback />
              </Col>
            </FormGroup>

            <FormGroup controlId="newCategory-weight">
              <Col componentClass={ControlLabel} sm={2}>
                Weight
              </Col>
              <Col sm={10}>
                <FormControl type="text"
                             placeholder="Category name"
                             value={this.props.newCategory.weight}
                             disabled={true}/>
                <FormControl.Feedback />
                <HelpBlock>Please change weight after creating category.</HelpBlock>
              </Col>
            </FormGroup>

            <FormGroup controlId="newCategory-description">
              <Col componentClass={ControlLabel} sm={2}>
                Description
              </Col>
              <Col sm={10}>
                <InputTextArea
                  content={this.props.newCategory.description}
                  onChange={this.props.onChange}
                  name="description"
                  height={ 200 }
                />
              </Col>
            </FormGroup>

            <FormGroup>
              <Col smOffset={2} sm={10}>
                <Button bsStyle="primary" type="submit" onClick={this.props.onSubmit}>
                  Create category
                </Button>
              </Col>
            </FormGroup>

          </Form>
        </Modal.Body>
      </Modal>
    );
  }
}

export default withRouter(ParameterDetail);
