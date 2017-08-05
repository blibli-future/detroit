import React from 'react';
import swal from 'sweetalert';

import BaseDetroitComponent from './BaseDetroitComponent';
import CategoryForm from './CategoryForm';
import {InputText} from "../containers/GantellelaTheme";


class ReviewForm extends BaseDetroitComponent {

  constructor(props) {
    super(props);
    this.state = {
      reviewRequest: {
        id: 0,
        casemgnt: '',
        interactionType: '',
        customerName: '',
        parameter: 0,
        agent: '',
        detailReviews: [
          {
            id: 0,
            score: 0,
            note: '',
            category: 0
          }
        ]
      },
      parameter: {
        name: '',
        description: '',
        categories: [
          {
            id: 0,
            name: '',
            weight: 0,
            description: ''
          }
        ]
      }
    }

    this.handleInputChange = this.handleInputChange.bind(this);
    this.handleInputChangeCategoryForm = this.handleInputChangeCategoryForm.bind(this);
  }

  componentDidMount() {
    this.getCategories();
  }

  getCategories() {
    let component = this;
    return this.auth.apiCall('/api/v1/parameters/'+this.props.match.params.parameterId, {
      method: 'GET'
    }).then((response) => response.json())
      .then((json) => {
        let category = [];
        for(var i=0; i<json.content.categories.length ; i++) {
          category.push({
            score: 0,
            note: '',
            category: json.content.categories[i].id
          });
        }
        const reviewRequest = this.state.reviewRequest;
        reviewRequest.detailReviews = category;
        reviewRequest.parameter = this.props.match.params.parameterId;
        component.setState({
          parameter: json.content,
          reviewRequest
        });
      });
  }

  handleInputChange(event) {
    const target = event.target;
    let value;
    if(target.type == 'checkbox') {
      value = target.checked;
    } else {
      value = target.value;
    }
    const name = target.name;

    const review = this.state.reviewRequest;
    review[name] = value;
    this.setState({
      review
    });
  }

  handleInputChangeCategoryForm(event, detailReviewIndex) {
    const target = event.target;
    let value;
    if(target.type == 'checkbox') {
      value = target.checked;
    } else if(target.type == 'summernote') {
      value = target.value;
    } else {
      value = target.value;
    }
    const name = target.name;

    const detailReview = this.state.reviewRequest.detailReviews;
    detailReview[detailReviewIndex][name] = value;
    this.setState({
      detailReview
    });
  }

  render() {
    let categoryComponent =  [];

    this.state.parameter.categories.forEach((item, index)=> {
      categoryComponent.push(
        <CategoryForm key={ index } id={ index } category={ item } handleInputChange={this.handleInputChangeCategoryForm} />
      )
    })


    return (
      <div className="right_col" role="main">
        <div className="">
          <div className="page-title">
            <div className="title_left">
              <h3>Review Form</h3>
            </div>
          </div>

          <div className="clearfix"></div>
          <div className="row">
            <div className="col-md-12 col-sm-12 col-xs-12">
              <div className="x_panel">
                <div className="x_title">
                  <h2> General Form</h2>
                  <div className="clearfix"></div>
                </div>
                <div className="x_content">
                  <form id="form" className="form-horizontal form-label-left">
                    <InputText data={this.state.reviewRequest.casemgnt}
                               onChange={this.handleInputChange}
                               name="casemgnt"
                               label="Case Management"
                    />
                    <InputText data={this.state.reviewRequest.interactionType}
                               onChange={this.handleInputChange}
                               name="interactionType"
                               label="Interaction Type"
                    />
                    <InputText data={this.state.reviewRequest.customerName}
                               onChange={this.handleInputChange}
                               name="customerName"
                               label="Customer Name"
                    />
                  </form>
                </div>
              </div>
            </div>
          </div>

          <div className="clearfix"></div>
          <div className="row">
            <div className="col-md-12 col-sm-12 col-xs-12">
              <div className="x_panel">
                <div className="x_title">
                  <h2> Categories</h2>
                  <div className="clearfix"></div>
                </div>
                <div className="x_content">
                  <form id="form" className="form-horizontal form-label-left">

                    { categoryComponent }

                    <div className="clearfix"></div>
                    <div className="form-group">
                      Button here
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

export default ReviewForm;
