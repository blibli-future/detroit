import React from 'react';
import swal from 'sweetalert';
import { Link } from 'react-router-dom'
import { withRouter } from 'react-router';

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
        tlName: '',
        parameter: 0,
        agent: 0,
        reviewer: 0,
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
    this.saveNewReview = this.saveNewReview.bind(this);
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
        reviewRequest.agent = this.props.match.params.agentId;
        component.setState({
          parameter: json.content,
          reviewRequest
        });
      });
  }

  validateDetailReviewScore() {
    let component = this;
    var check = true;
    component.state.reviewRequest.detailReviews.forEach((item)=> {
      if(item['score'] > 100 || item['score'] <0) {
        check = false;
      }
    })
    return check;
  }

  saveNewReview(event) {
    event.preventDefault();
    let component = this;

    if(!this.validateDetailReviewScore()) {
      return swal("Error", "Score cannot more than 100 or less than 0", "error");
    }

    this.auth.apiCall("/api/v1/reviews", {
      method: 'POST',
      body: JSON.stringify(component.state.reviewRequest),
    }).then(response => response.json())
      .then(json => {
        if (json.success) {
          swal({
            title: "Success",
            text: "New review has been created.",
            type: "success",
          });
          component.props.history.push('/view/review/overview')
        } else {
          swal("Error", json.errorMessage || json.message, "error");
        }
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

          <form id="form" onSubmit={ this.saveNewReview } className="form-horizontal form-label-left">
          <div className="clearfix"></div>
          <div className="row">
            <div className="col-md-12 col-sm-12 col-xs-12">
              <div className="x_panel">
                <div className="x_title">
                  <h2> General Form</h2>
                  <div className="clearfix"></div>
                </div>
                <div className="x_content">

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
                               required="required"
                    />
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

                    { categoryComponent }

                    <div className="clearfix"></div>
                    <div className="form-group">
                      <Link to={'/view/review/overview'}
                            className="btn btn-default">
                        Cancel
                      </Link>
                      <button type="submit" className="btn btn-success" value="submit">
                        Submit Review
                      </button>
                    </div>

                </div>
              </div>
            </div>
          </div>
          </form>

        </div>
      </div>
    )
  }
}

export default withRouter(ReviewForm);
