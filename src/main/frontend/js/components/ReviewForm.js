import React from 'react';
import swal from 'sweetalert';

import BaseDetroitComponent from './BaseDetroitComponent';
import CategoryForm from './CategoryForm';


class ReviewForm extends BaseDetroitComponent {

  constructor(props) {
    super(props);
    this.state = {
      category: [
        {
          score: 0,
          note: ''
        }
      ],
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
            note: ''
          });
        }
        component.setState({
          parameter: json.content,
          category: category
        });
      });
  }

  handleInputChange(event, categoryIndex) {
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

    const category = this.state.category;
    category[categoryIndex][name] = value;
    this.setState({
      category
    });
  }

  render() {
    let categoryComponent =  [];

    this.state.parameter.categories.forEach((item, index)=> {
      categoryComponent.push(
        <CategoryForm key={ index } id={ index } category={ item } handleInputChange={this.handleInputChange} />
      )
    })


    return (
      <div className="right_col" role="main">
        <div className="">
          <div className="page-title">
            <div className="title_left">
              <h3>All Statistic</h3>
            </div>
          </div>

          <div className="clearfix"></div>
          <div className="row">
            <div className="col-md-12 col-sm-12 col-xs-12">
              <div className="x_panel">
                <div className="x_title">
                  <h2> Review Table</h2>
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
