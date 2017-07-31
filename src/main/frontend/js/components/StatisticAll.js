import React from 'react';

import BaseDetroitComponent from './BaseDetroitComponent';
import { Link } from 'react-router-dom'
import {Bar, Line, Pie} from 'react-chartjs-2';

class StatisticAll extends BaseDetroitComponent {

  constructor(props) {
    super(props);
    this.state = {
      allStatistic: []
    };
    this.parameterProps = [];
    this.categoryProps = [];
    this.getStatisticData()
  }

  getStatisticData() {
    let component = this;
    return this.auth.apiCall('/api/v1/statistic', {
      method: 'GET'
    }).then((response) => response.json())
      .then((json) => {
        component.setState({
          allStatistic: json.content
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
    let component = this
    if(this.state.allStatistic.length != 0) {
      let xAxis = component.state.allStatistic['dateInISOFormat'];
      let parameterDatasets = [];
      component.state.allStatistic['parameter'].forEach((item,index)=> {
        let categoryDatasets = [];
        parameterDatasets.push({
          label: item["name"],
          data: item['scores'],
          backgroundColor: [
            component.generateRandomColor(item['name'])
          ]
        })

        item['category'].forEach((item, index)=> {
          categoryDatasets.push({
            label: item['name'],
            data: item['scores'],
            backgroundColor: [
              component.generateRandomColor(item['name'])
              ]
          })
        })

        component.categoryProps.push(
          <TotalStatistic key={item['name']} title={item['name']} labels={xAxis} datasets={categoryDatasets} />
        )

      })

      component.parameterProps.push(
        <TotalStatistic key={"All"} title={"All"} labels={xAxis} datasets={parameterDatasets} />
      )
    }

    return (
      <div className="right_col" role="main">
        <div className="">
          <div className="page-title">
            <div className="title_left">
              <h3>Test Statistic All</h3>
            </div>
          </div>

          <div className="clearfix"></div>

          <div className="row tile_count">
            <div className="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
              <span className="count_top"><i className="fa fa-user"></i> Total Customer Service</span>
              <div className="count green">2502</div>
              <span className="count_bottom"><i className="green"><i className="fa fa-sort-asc"></i> +5 </i>From last Month</span>
            </div>
            <div className="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
              <span className="count_top"><i className="fa fa-clock-o"></i> Total Value Avg</span>
              <div className="count green">92</div>
              <span className="count_bottom"><i className="green"><i className="fa fa-sort-asc"></i>5% </i> From last Month</span>
            </div>
            <div className="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
              <span className="count_top"><i className="fa fa-user"></i> Technical Review Avg</span>
              <div className="count red">68</div>
              <span className="count_bottom"><i className="red"><i className="fa fa-sort-desc"></i>8% </i> From last Month</span>
            </div>
            <div className="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
              <span className="count_top"><i className="fa fa-user"></i> Services Review Avg</span>
              <div className="count">85</div>
              <span className="count_bottom">Same with last Month</span>
            </div>
            <div className="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
              <span className="count_top"><i className="fa fa-user"></i> Other Review Avg</span>
              <div className="count green">78</div>
              <span className="count_bottom"><i className="green"><i className="fa fa-sort-asc"></i>2% </i> From last Month</span>
            </div>
            <div className="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
              <span className="count_top"><i className="fa fa-user"></i> ... Review Avg</span>
              <div className="count">-</div>
              <span className="count_bottom">-</span>
            </div>
          </div>

          { this.parameterProps }

          { this.categoryProps }
        </div>
      </div>
    );
  }
}

class TotalStatistic extends React.Component{
  constructor(props) {
    super(props);
    this.state = {
      chartData:{
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
              <h2>{ this.props.title } Review Parameter Summary <small>Annual progress</small></h2>
              <div className="filter">
                <div id="reportrange" className="pull-right">
                  <i className="glyphicon glyphicon-calendar fa fa-calendar"></i>
                  <span>December 30, 2014 - January 28, 2015</span> <b className="caret"></b>
                </div>
              </div>
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

export default StatisticAll;
