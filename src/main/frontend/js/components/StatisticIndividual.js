import React from 'react';

import BaseDetroitComponent from './BaseDetroitComponent';
import { Link } from 'react-router-dom'
import {Bar, Line, Pie} from 'react-chartjs-2';

class StatisticIndividual extends BaseDetroitComponent {
  constructor(props) {
    super(props);
    this.state = {
      individualStatistic:[],
      individualInfo: {
        finalScore: 0,
        diffFinalScore : 0,
        parameters: []
      },
      reviewNote: [],
      agentProfile: []
    }
  };

  componentDidMount() {
    this.getStatisticIndividualDiagram();
    this.getStatisticInfo();
    this.getReviewNote();
    this.getAgentProfile();
  }

  getStatisticIndividualDiagram() {
    let component = this;
    return this.auth.apiCall('/api/v1/statistic/'+this.props.match.params.agentId, {
      method: 'GET'
    }).then((response) => response.json())
      .then((json) => {
        component.setState({
          individualStatistic: json.content
        });
      });
  }

  getStatisticInfo() {
    let component = this;
    return this.auth.apiCall('/api/v1/statistic/info/'+this.props.match.params.agentId, {
      method: 'GET'
    }).then((response) => response.json())
      .then((json) => {
        component.setState({
          individualInfo: json.content
        });
      });
  }

  getReviewNote() {
    let component = this;
    return this.auth.apiCall('/api/v1/statistic/review/note/'+this.props.match.params.agentId, {
      method: 'GET'
    }).then((response) => response.json())
      .then((json) => {
        component.setState({
          reviewNote: json.content
        });
      });
  }

  getAgentProfile() {
    let component = this;
    return this.auth.apiCall('/api/v1/users/'+this.props.match.params.agentId, {
      method: 'GET'
    }).then((response) => response.json())
      .then((json) => {
        component.setState({
          agentProfile: json.content
        });
      });
  }

  hashCode(str) { // java String#hashCode
    var hash = 0;
    for (var i = 0; i < str.length; i++) {
      hash = str.charCodeAt(i) + ((hash << 5) - hash);
    }
    return hash;
  }

  intToRGB(i){
    var c = (i & 0x00FFFFFF)
      .toString(16)
      .toUpperCase();

    return "00000".substring(0, 6 - c.length) + c;
  }

  hexToRgbA(hex){
    var c;
    if(/^([A-Fa-f0-9]{3}){1,2}$/.test(hex)){
      c= hex.substring(1).split('');
      if(c.length== 3){
        c= [c[0], c[0], c[1], c[1], c[2], c[2]];
      }
      c= '0x'+c.join('');
      return 'rgba('+[(c>>16)&255, (c>>8)&255, c&255].join(',')+',0.6)';
    }
    throw new Error('Bad Hex');
  }

  generateRandomColor(str) {
    return this.hexToRgbA(this.intToRGB(this.hashCode(str)))
  }

  generateRandomNumber() {
    return Math.random();
  }

  render() {
    let component = this;

    let totalStatistic = [];
    let parameterStatistic = [];
    let categoryStatistic = [];

    var count = 0;

    if(this.state.individualStatistic.length != 0) {
      let totalDatasets = [{
        label: "Total Review Score",
        data: component.state.individualStatistic['scores'],
        backgroundColor: [
          component.generateRandomColor("Total Review Score")
        ]
      }]

      let xAxis = component.state.individualStatistic['dateInISOFormat'];

      totalStatistic.push(
        <IndividualStatistic key="Total" title="Total" labels={ xAxis } datasets={ totalDatasets } />
      )

      let parameterDatasets = [];
      component.state.individualStatistic['parameters'].forEach((item,index)=> {
        count++;
        let categoryDatasets = [];

        parameterDatasets.push({
          label : item['name'],
          data: item['scores'],
          backgroundColor: [
            component.generateRandomColor(item['name'])
          ]
        });

        item['categories'].forEach((item,index)=> {
          count++;
          categoryDatasets.push({
            label : item['name'],
            data: item['scores'],
            backgroundColor: [
              component.generateRandomColor(item['name'])
            ]
          });
        });

        categoryStatistic.push(
          <IndividualStatistic key={ count } title={ item['name'] } labels={ xAxis } datasets={ categoryDatasets } />
        )

      });
      parameterStatistic.push(
        <IndividualStatistic key={ count } title="All" labels={ xAxis } datasets={ parameterDatasets } />
      )
    }

    let parameterInfoIndividual = [];
    if(this.state.individualInfo.length != 0) {
      count = 0;
      this.state.individualInfo['parameters'].forEach((item, index)=> {
        count++;
        parameterInfoIndividual.push(
          <StatisticInfoIndividual key={ count } title={ item['name'] } score={ item['score'] } diff={ item['diffScore'] }  />
        )
      })
    }

    let individualReviewNote = [];
    if(this.state.reviewNote.length != 0) {
      count = 0;
      this.state.reviewNote.forEach((item,index) => {
        count++;
        individualReviewNote.push(
          <IndividualReviewNote key={ count } title={ item['category'] } score={ item['score'] }  note={ item['note'] } />
        )
      });
    }


    return(
      <div className="right_col" role="main">
        <div className="">
          <div className="page-title">
            <div className="title_left">
              <h3>Agent Profile</h3>
            </div>
          </div>

          <div className="clearfix"></div>

          <div className="row">
            <div className="col-md-12 col-sm-12 col-xs-12">
              <div className="x_panel">
                <div className="x_content">
                  <div className="x_title">
                    <h2>Agent Report <small>Activity report</small></h2>
                    <div className="clearfix"></div>
                  </div>
                  <div className="col-md-3 col-sm-3 col-xs-12 profile_left">
                    <div className="profile_img">
                      <div id="crop-avatar">
                        <img className="img-responsive avatar-view" src="https://qph.ec.quoracdn.net/main-qimg-220ae86dafbf81b8227586161b2aee61-c?convert_to_webp=true" alt="Avatar" title="Change the avatar"/>
                      </div>
                    </div>
                    <h3> { this.state.agentProfile['fullname']} </h3>

                    <ul className="list-unstyled user_data">
                      <li>
                        <i className="fa fa-address-card user-profile-icon"/>  <b>Nickname :</b> { this.state.agentProfile['nickname']}
                      </li>
                      <li>
                        <i className="fa fa-users user-profile-icon"/> <b>Position :</b> { this.state.agentProfile['agentPosition']}
                      </li>
                      <li>
                        <i className="fa fa-user user-profile-icon"/> <b>Channel :</b> { this.state.agentProfile['agentChannel']}
                      </li>
                      <li>
                        <i className="fa fa-user-circle-o user-profile-icon"/> <b>Team Leader :</b> { this.state.agentProfile['teamLeader']}
                      </li>
                      <li>
                        <i className="fa fa-envelope user-profile-icon"/> <b>Email :</b> { this.state.agentProfile['email']}
                      </li>
                      <li>
                        <i className="fa fa-phone-square user-profile-icon"/> <b>Phone Number :</b> { this.state.agentProfile['phoneNumber']}
                      </li>
                      <li>
                        <i className="fa fa-birthday-cake user-profile-icon"/> <b>Date of Birth :</b> { this.state.agentProfile['dateOfBirth']}
                      </li>
                      <li>
                        <i className="fa fa-venus-mars user-profile-icon"/> <b>Gender :</b> { this.state.agentProfile['gender']}
                      </li>
                      <li>
                        <i className="fa fa-map-marker user-profile-icon"/> <b>Address :</b> { this.state.agentProfile['location']}
                      </li>
                    </ul>
                    <br />



                  </div>

                  <div className="col-md-9 col-sm-9 col-xs-12">

                    <div className="profile_title">
                      <div className="col-md-6">
                        <h2>Agent Activity</h2>
                      </div>
                    </div>
                    <div className="row tile_count">
                      <StatisticInfoIndividual key="Total Review Score" title="Total Review Score" score={ this.state.individualInfo['finalScore'] } diff={ this.state.individualInfo['diffFinalScore'] }  />
                      { parameterInfoIndividual }
                    </div>

                    { totalStatistic }

                    { parameterStatistic }

                    { categoryStatistic }

                    <div className="row">
                      <div className="col-md-12">
                        <div className="x_panel">
                          <div className="x_title">
                            <h2>Review Note
                              <small>All note from reviewer</small>
                            </h2>
                            <div className="clearfix"></div>
                          </div>
                          <br/>
                          <div className="x_content">
                            <ul className="messages">
                              { individualReviewNote }
                            </ul>
                          </div>
                        </div>
                      </div>
                    </div>

        </div>
      </div>
  </div>
  </div>
  </div>
  </div>
  </div>
    )
  }
}

class IndividualStatistic extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      chartData: {
        labels: this.props.labels,
        datasets: this.props.datasets
      }
    }
  }

  render() {
    return (
      <div className="row">
        <div className="col-md-12">
          <div className="x_panel">
            <div className="x_title">
              <h2>{this.props.title} Review Summary
                <small>Annual progress</small>
              </h2>
              {/*<div className="filter">*/}
              {/*<div id="reportrange" className="pull-right">*/}
              {/*<i className="glyphicon glyphicon-calendar fa fa-calendar"></i>*/}
              {/*<span>December 30, 2014 - January 28, 2015</span> <b className="caret"></b>*/}
              {/*</div>*/}
              {/*</div>*/}
              <div className="clearfix"></div>
            </div>
            <br/>
            <div className="x_content">
              <Line
                data={this.state.chartData}
                options={{
                  maintainAspectRatio: false
                }}
              />
            </div>
          </div>
        </div>
      </div>
    );
  }
}

class StatisticInfoIndividual extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    var status;
    if(this.props.diff !== null) {
      if(this.props.diff > 0) {
        status = (
          <span className="count_bottom"><i className="green"><i className="fa fa-sort-asc"></i> +{ this.props.diff } </i>From last Month</span>
        );
      } else if(this.props.diff < 0) {
        status = (
          <span className="count_bottom"><i className="red"><i className="fa fa-sort-desc"></i> { this.props.diff } </i>From last Month</span>
        );
      } else {
        status = (
          <span className="count_bottom">Same with last Month</span>
        );
      }
    }
    return (
      <div className="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
        <span className="count_top"><i className="fa fa-user"></i> { this.props.title } </span>
        <div className="count">{ this.props.score }</div>
        { status }
      </div>
    );
  }
}

class IndividualReviewNote extends  React.Component {
  constructor(props) {
    super(props)
  }

  render() {
    return (
      <li>
        <div className="message_wrapper">
          <h4 className="heading">{this.props.title} (Score : {this.props.score})</h4>
          <p className="message">{this.props.note}</p>
          <br/>
        </div>
      </li>
    )
  }
}

export default StatisticIndividual;
