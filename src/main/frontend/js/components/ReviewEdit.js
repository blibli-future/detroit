import React from 'react';
import swal from 'sweetalert';
import { Link } from 'react-router-dom'
import { withRouter } from 'react-router';

import BaseDetroitComponent from './BaseDetroitComponent';
import CategoryForm from './CategoryForm';
import {InputText} from "../containers/GantellelaTheme";

class ReviewEdit extends BaseDetroitComponent {
  constructor(props) {
    super(props);
    this.state= {
      review: {
        id: 0,
        casemgnt: '',
        interactionType: '',
        customerName: '',
        tlName: '',
        score: 0,
        parameter: 0,
        agent: 0,
        reviewer: 0,
        cutOff: 0,
        detailReviews: [
          {
            id: 0,
            score: 0,
            note: '',
            name: '',
            weight: 0,
            description: ''
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
    this.updateReview = this.updateReview.bind(this);
  }

  componentDidMount() {
    this.getReview();
  }

  getReview() {
    let component = this;
    return this.auth.apiCall('/api/v1/reviews/'+this.props.match.params.reviewId, {
      method: 'GET'
    }).then((response) => response.json())
      .then((json) => {
        component.setState({
          review: json.content
        });
      });
  }

  validateDetailReviewScore() {
    let component = this;
    var check = true;
    component.state.review.detailReviews.forEach((item)=> {
      if(item['score'] > 100 || item['score'] <0) {
        check = false;
      }
    })
    return check;
  }

  updateReview(event) {
    event.preventDefault();
    let component = this;

    if(!this.validateDetailReviewScore()) {
      return swal("Error", "Score cannot more than 100 or less than 0", "error");
    }

    this.auth.apiCall("/api/v1/reviews/"+this.props.match.params.reviewId, {
      method: 'PATCH',
      body: JSON.stringify(component.state.review),
    }).then(response => response.json())
      .then(json => {
        if (json.success) {
          swal({
            title: "Success",
            text: "Review has been updated.",
            type: "success",
          });
          component.props.history.push('/view/review/history');
        } else {
          swal("Error", json.errorMessage, "error");
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

    const review = this.state.review;
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

    const detailReview = this.state.review.detailReviews;
    detailReview[detailReviewIndex][name] = value;
    this.setState({
      detailReview
    });
  }

  render() {
    let categoryComponent =  [];

    this.state.review.detailReviews.forEach((item, index)=> {
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

          <form id="form" onSubmit={ this.updateReview } className="form-horizontal form-label-left">
            <div className="clearfix"></div>
            <div className="row">
              <div className="col-md-12 col-sm-12 col-xs-12">
                <div className="x_panel">
                  <div className="x_title">
                    <h2> General Form</h2>
                    <div className="clearfix"></div>
                  </div>
                  <div className="x_content">

                    <InputText data={this.state.review.casemgnt}
                               onChange={this.handleInputChange}
                               name="casemgnt"
                               label="Case Management"
                    />
                    <InputText data={this.state.review.interactionType}
                               onChange={this.handleInputChange}
                               name="interactionType"
                               label="Interaction Type"
                    />
                    <InputText data={this.state.review.customerName}
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
                      <Link to={'/view/review/history'}
                            className="btn btn-default">
                        Cancel
                      </Link>
                      <button type="submit" className="btn btn-success" value="submit">
                        Update Review
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

export default withRouter(ReviewEdit);
