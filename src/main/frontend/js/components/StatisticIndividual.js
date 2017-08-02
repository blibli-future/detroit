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
      }
    }
  };

  componentDidMount() {
    this.getStatisticIndividualDiagram();
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

  render() {
    let component = this;

    let totalStatistic = [];
    let parameterStatistic = [];
    let categoryStatistic = [];

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
        let categoryDatasets = [];

        parameterDatasets.push({
          label : item['name'],
          data: item['scores'],
          backgroundColor: [
            component.generateRandomColor(item['name'])
          ]
        });
        parameterStatistic.push(
          <IndividualStatistic key="All" title="All" labels={ xAxis } datasets={ parameterDatasets } />
        )

        item['categories'].forEach((item,index)=> {
          categoryDatasets.push({
            label : item['name'],
            data: item['scores'],
            backgroundColor: [
              component.generateRandomColor(item['name'])
            ]
          });
        });

        categoryStatistic.push(
          <IndividualStatistic key={ item['name'] } title={ item['name'] } labels={ xAxis } datasets={ categoryDatasets } />
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
                  <div className="col-md-3 col-sm-3 col-xs-12 profile_left">
                    <div className="profile_img">
                      <div id="crop-avatar">
                        <img className="img-responsive avatar-view" src="https://qph.ec.quoracdn.net/main-qimg-220ae86dafbf81b8227586161b2aee61-c?convert_to_webp=true" alt="Avatar" title="Change the avatar"/>
                      </div>
                    </div>
                    <h3>Samuel Doe</h3>

                    <ul className="list-unstyled user_data">
                      <li>Nickname : John
                      </li>

                      <li>
                        Phone Number : 21309123809
                      </li>

                      <li className="m-top-xs">
                        Email : samuel.doe@gmail.com
                      </li>

                      <li className="m-top-xs">
                        etc.
                      </li>
                    </ul>
                    <br />



                  </div>
                  <div className="col-md-9 col-sm-9 col-xs-12">

                    <div className="profile_title">
                      <div className="col-md-6">
                        <h2>Agent Activity Report</h2>
                      </div>
                    </div>
                    <div className="row tile_count">
                      <div className="col-md-3 col-sm-4 col-xs-6 tile_stats_count">
                        <span className="count_top"><i className="fa fa-clock-o"></i> Total Value Avg</span>
                        <div className="count green">92</div>
                        <span className="count_bottom"><i className="green"><i className="fa fa-sort-asc"></i>5% </i> From last Month</span>
                      </div>
                      <div className="col-md-3 col-sm-4 col-xs-6 tile_stats_count">
                        <span className="count_top"><i className="fa fa-user"></i> Technical Review Avg</span>
                        <div className="count red">68</div>
                        <span className="count_bottom"><i className="red"><i className="fa fa-sort-desc"></i>8% </i> From last Month</span>
                      </div>
                      <div className="col-md-3 col-sm-4 col-xs-6 tile_stats_count">
                        <span className="count_top"><i className="fa fa-user"></i> Services Review Avg</span>
                        <div className="count">85</div>
                        <span className="count_bottom">Same with last Month</span>
                      </div>
                      <div className="col-md-3 col-sm-4 col-xs-6 tile_stats_count">
                        <span className="count_top"><i className="fa fa-user"></i> Other Review Avg</span>
                        <div className="count green">78</div>
                        <span className="count_bottom"><i className="green"><i className="fa fa-sort-asc"></i>2% </i> From last Month</span>
                      </div>
                      <div className="col-md-3 col-sm-4 col-xs-6 tile_stats_count">
                        <span className="count_top"><i className="fa fa-user"></i> Attendance</span>
                        <div className="count">99%</div>
                        <span className="count_bottom"><i className="green"><i className="fa fa-sort-asc"></i>2% </i> From last Month</span>
                      </div>
                      <div className="col-md-3 col-sm-4 col-xs-6 tile_stats_count">
                        <span className="count_top"><i className="fa fa-user"></i> Custom Review Count</span>
                        <div className="count">16</div>
                        <span className="count_bottom"><i className="green"><i className="fa fa-sort-asc"></i>2 </i> From last Month</span>
                      </div>
                    </div>

                    { totalStatistic }

                    { parameterStatistic }

                    { categoryStatistic }

          <div className="" role="tabpanel" data-example-id="togglable-tabs">
            <ul id="myTab" className="nav nav-tabs bar_tabs" role="tablist">
              <li role="presentation" className="active"><a href="#tab_content1" id="home-tab" role="tab" data-toggle="tab" aria-expanded="true">Technical Review</a>
              </li>
              <li role="presentation" className=""><a href="#tab_content2" role="tab" id="profile-tab" data-toggle="tab" aria-expanded="false">Services Review</a>
              </li>
              <li role="presentation" className=""><a href="#tab_content3" role="tab" id="profile-tab2" data-toggle="tab" aria-expanded="false">Other Review</a>
              </li>
            </ul>
            <div id="myTabContent" className="tab-content">
              <div role="tabpanel" className="tab-pane fade active in" id="tab_content1" aria-labelledby="home-tab">
                <div className="" role="tabpanel" data-example-id="togglable-tabs">
                  <ul id="myTab" className="nav nav-tabs bar_tabs" role="tablist">
                    <li role="presentation" className="active"><a href="#tab_content111" id="home-tab" role="tab" data-toggle="tab" aria-expanded="true">Reactiveness</a>
                    </li>
                    <li role="presentation" className=""><a href="#tab_content222" role="tab" id="profile-tab" data-toggle="tab" aria-expanded="false">Accuracy</a>
                    </li>
                  </ul>
                  <div id="myTabContent" className="tab-content">
                    <div role="tabpanel" className="tab-pane fade active in" id="tab_content111" aria-labelledby="home-tab">

                      <ul className="messages">
                        <li>
                          <div className="message_date">
                            <h3 className="date text-info">24</h3>
                            <p className="month">May</p>
                          </div>
                          <div className="message_wrapper">
                            <h4 className="heading">Value : 55</h4>
                            <p className="message">Raw denim you probably haven't heard of them jean shorts Austin. Nesciunt tofu stumptown aliqua butcher retro keffiyeh dreamcatcher synth.
                            </p>
                            <br />
                          </div>
                        </li>
                        <li>
                          <div className="message_date">
                            <h3 className="date text-info">24</h3>
                            <p className="month">May</p>
                          </div>
                          <div className="message_wrapper">
                            <h4 className="heading">Value : 55</h4>
                            <p className="message">Raw denim you probably haven't heard of them jean shorts Austin. Nesciunt tofu stumptown aliqua butcher retro keffiyeh dreamcatcher synth.
                            </p>
                            <br />
                          </div>
                        </li>
                        <li>
                          <div className="message_date">
                            <h3 className="date text-info">24</h3>
                            <p className="month">May</p>
                          </div>
                          <div className="message_wrapper">
                            <h4 className="heading">Value : 55</h4>
                            <p className="message">Raw denim you probably haven't heard of them jean shorts Austin. Nesciunt tofu stumptown aliqua butcher retro keffiyeh dreamcatcher synth.
                            </p>
                            <br />
                          </div>
                        </li>
                        <li>
                          <div className="message_date">
                            <h3 className="date text-info">24</h3>
                            <p className="month">May</p>
                          </div>
                          <div className="message_wrapper">
                            <h4 className="heading">Value : 55</h4>
                            <p className="message">Raw denim you probably haven't heard of them jean shorts Austin. Nesciunt tofu stumptown aliqua butcher retro keffiyeh dreamcatcher synth.
                            </p>
                            <br />
                          </div>
                        </li>

                      </ul>

                    </div>
                    <div role="tabpanel" className="tab-pane fade" id="tab_content222" aria-labelledby="profile-tab">

                      <ul className="messages">
                        <li>
                          <img src="images/img.jpg" className="avatar" alt="Avatar"/>
                            <div className="message_date">
                              <h3 className="date text-info">24</h3>
                              <p className="month">May</p>
                            </div>
                            <div className="message_wrapper">
                              <h4 className="heading">Value : 55</h4>
                              <p className="message">Raw denim you probably haven't heard of them jean shorts Austin. Nesciunt tofu stumptown aliqua butcher retro keffiyeh dreamcatcher synth.
                              </p>
                              <br />
                            </div>
                        </li>
                        <li>
                          <img src="images/img.jpg" className="avatar" alt="Avatar"/>
                            <div className="message_date">
                              <h3 className="date text-error">21</h3>
                              <p className="month">May</p>
                            </div>
                            <div className="message_wrapper">
                              <h4 className="heading">Value : 55</h4>
                              <p className="message">Raw denim you probably haven't heard of them jean shorts Austin. Nesciunt tofu stumptown aliqua butcher retro keffiyeh dreamcatcher synth.
                              </p>
                              <br />
                            </div>
                        </li>
                        <li>
                          <img src="images/img.jpg" className="avatar" alt="Avatar"/>
                            <div className="message_date">
                              <h3 className="date text-info">24</h3>
                              <p className="month">May</p>
                            </div>
                            <div className="message_wrapper">
                              <h4 className="heading">Desmond Davison</h4>
                              <blockquote className="message">Raw denim you probably haven't heard of them jean shorts Austin. Nesciunt tofu stumptown aliqua butcher retro keffiyeh dreamcatcher synth.</blockquote>
                              <br />
                              <p className="url">
                                <span className="fs1 text-info" aria-hidden="true" data-icon=""></span>
                                <a href="#"><i className="fa fa-paperclip"></i> User Acceptance Test.doc </a>
                              </p>
                            </div>
                        </li>
                        <li>
                          <img src="images/img.jpg" className="avatar" alt="Avatar"/>
                            <div className="message_date">
                              <h3 className="date text-error">21</h3>
                              <p className="month">May</p>
                            </div>
                            <div className="message_wrapper">
                              <h4 className="heading">Brian Michaels</h4>
                              <blockquote className="message">Raw denim you probably haven't heard of them jean shorts Austin. Nesciunt tofu stumptown aliqua butcher retro keffiyeh dreamcatcher synth.</blockquote>
                              <br />
                              <p className="url">
                                <span className="fs1" aria-hidden="true" data-icon=""></span>
                                <a href="#" data-original-title="">Download</a>
                              </p>
                            </div>
                        </li>

                      </ul>

                    </div>
                  </div>
                </div>

              </div>
              <div role="tabpanel" className="tab-pane fade" id="tab_content2" aria-labelledby="profile-tab">

                <ul className="messages">
                  <li>
                    <img src="images/img.jpg" className="avatar" alt="Avatar"/>
                      <div className="message_date">
                        <h3 className="date text-info">24</h3>
                        <p className="month">May</p>
                      </div>
                      <div className="message_wrapper">
                        <h4 className="heading">Desmond Davison</h4>
                        <blockquote className="message">Raw denim you probably haven't heard of them jean shorts Austin. Nesciunt tofu stumptown aliqua butcher retro keffiyeh dreamcatcher synth.</blockquote>
                        <br />
                        <p className="url">
                          <span className="fs1 text-info" aria-hidden="true" data-icon=""></span>
                          <a href="#"><i className="fa fa-paperclip"></i> User Acceptance Test.doc </a>
                        </p>
                      </div>
                  </li>
                  <li>
                    <img src="images/img.jpg" className="avatar" alt="Avatar"/>
                      <div className="message_date">
                        <h3 className="date text-error">21</h3>
                        <p className="month">May</p>
                      </div>
                      <div className="message_wrapper">
                        <h4 className="heading">Brian Michaels</h4>
                        <blockquote className="message">Raw denim you probably haven't heard of them jean shorts Austin. Nesciunt tofu stumptown aliqua butcher retro keffiyeh dreamcatcher synth.</blockquote>
                        <br />
                        <p className="url">
                          <span className="fs1" aria-hidden="true" data-icon=""></span>
                          <a href="#" data-original-title="">Download</a>
                        </p>
                      </div>
                  </li>
                  <li>
                    <img src="images/img.jpg" className="avatar" alt="Avatar"/>
                      <div className="message_date">
                        <h3 className="date text-info">24</h3>
                        <p className="month">May</p>
                      </div>
                      <div className="message_wrapper">
                        <h4 className="heading">Desmond Davison</h4>
                        <blockquote className="message">Raw denim you probably haven't heard of them jean shorts Austin. Nesciunt tofu stumptown aliqua butcher retro keffiyeh dreamcatcher synth.</blockquote>
                        <br />
                        <p className="url">
                          <span className="fs1 text-info" aria-hidden="true" data-icon=""></span>
                          <a href="#"><i className="fa fa-paperclip"></i> User Acceptance Test.doc </a>
                        </p>
                      </div>
                  </li>
                  <li>
                    <img src="images/img.jpg" className="avatar" alt="Avatar"/>
                      <div className="message_date">
                        <h3 className="date text-error">21</h3>
                        <p className="month">May</p>
                      </div>
                      <div className="message_wrapper">
                        <h4 className="heading">Brian Michaels</h4>
                        <blockquote className="message">Raw denim you probably haven't heard of them jean shorts Austin. Nesciunt tofu stumptown aliqua butcher retro keffiyeh dreamcatcher synth.</blockquote>
                        <br />
                        <p className="url">
                          <span className="fs1" aria-hidden="true" data-icon=""></span>
                          <a href="#" data-original-title="">Download</a>
                        </p>
                      </div>
                  </li>

                </ul>

              </div>
              <div role="tabpanel" className="tab-pane fade" id="tab_content3" aria-labelledby="profile-tab">

                <ul className="messages">
                  <li>
                    <img src="images/img.jpg" className="avatar" alt="Avatar"/>
                      <div className="message_date">
                        <h3 className="date text-info">24</h3>
                        <p className="month">May</p>
                      </div>
                      <div className="message_wrapper">
                        <h4 className="heading">Desmond Davison</h4>
                        <blockquote className="message">Raw denim you probably haven't heard of them jean shorts Austin. Nesciunt tofu stumptown aliqua butcher retro keffiyeh dreamcatcher synth.</blockquote>
                        <br />
                        <p className="url">
                          <span className="fs1 text-info" aria-hidden="true" data-icon=""></span>
                          <a href="#"><i className="fa fa-paperclip"></i> User Acceptance Test.doc </a>
                        </p>
                      </div>
                  </li>
                  <li>
                    <img src="images/img.jpg" className="avatar" alt="Avatar"/>
                      <div className="message_date">
                        <h3 className="date text-error">21</h3>
                        <p className="month">May</p>
                      </div>
                      <div className="message_wrapper">
                        <h4 className="heading">Brian Michaels</h4>
                        <blockquote className="message">Raw denim you probably haven't heard of them jean shorts Austin. Nesciunt tofu stumptown aliqua butcher retro keffiyeh dreamcatcher synth.</blockquote>
                        <br />
                        <p className="url">
                          <span className="fs1" aria-hidden="true" data-icon=""></span>
                          <a href="#" data-original-title="">Download</a>
                        </p>
                      </div>
                  </li>
                  <li>
                    <img src="images/img.jpg" className="avatar" alt="Avatar"/>
                      <div className="message_date">
                        <h3 className="date text-info">24</h3>
                        <p className="month">May</p>
                      </div>
                      <div className="message_wrapper">
                        <h4 className="heading">Desmond Davison</h4>
                        <blockquote className="message">Raw denim you probably haven't heard of them jean shorts Austin. Nesciunt tofu stumptown aliqua butcher retro keffiyeh dreamcatcher synth.</blockquote>
                        <br />
                        <p className="url">
                          <span className="fs1 text-info" aria-hidden="true" data-icon=""></span>
                          <a href="#"><i className="fa fa-paperclip"></i> User Acceptance Test.doc </a>
                        </p>
                      </div>
                  </li>
                  <li>
                    <img src="images/img.jpg" className="avatar" alt="Avatar"/>
                      <div className="message_date">
                        <h3 className="date text-error">21</h3>
                        <p className="month">May</p>
                      </div>
                      <div className="message_wrapper">
                        <h4 className="heading">Brian Michaels</h4>
                        <blockquote className="message">Raw denim you probably haven't heard of them jean shorts Austin. Nesciunt tofu stumptown aliqua butcher retro keffiyeh dreamcatcher synth.</blockquote>
                        <br />
                        <p className="url">
                          <span className="fs1" aria-hidden="true" data-icon=""></span>
                          <a href="#" data-original-title="">Download</a>
                        </p>
                      </div>
                  </li>

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

export default StatisticIndividual;
