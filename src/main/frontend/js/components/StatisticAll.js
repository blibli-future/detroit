import React from 'react';

import BaseDetroitComponent from './BaseDetroitComponent';
import { Link } from 'react-router-dom'
import {Bar, Line, Pie} from 'react-chartjs-2';

class StatisticAll extends BaseDetroitComponent {

  constructor(props) {
    super(props);
    this.state = {
      allStatistic: [],
      allInfo: {
        totalAgent: 0,
        finalScore: 0,
        diffFinalScore: 0,
        parameters: []
      }
    };
    this.getStatisticInfo();

    this.totalProps = [];
    this.positionChannelProps = [];
    this.getStatisticData();
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

  getStatisticInfo() {
    let component = this;
    return this.auth.apiCall('/api/v1/statistic/info', {
      method: 'GET'
    }).then((response) => response.json())
      .then((json) => {
        component.setState({
          allInfo: json.content
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
      let totalDatasets = [{
        label: "Total Review Score",
        data: component.state.allStatistic['scores'],
        backgroundColor: [
          component.generateRandomColor("Total Review Score")
        ]
      }]
      let xAxis = component.state.allStatistic['dateInISOFormat'];

      component.totalProps.push(
        <TotalStatistic key="Total" title="Total" labels={ xAxis } datasets={ totalDatasets } />
      )

      component.state.allStatistic['positions'].forEach((item,index)=> {
        let positionChannelDatasets = [];
        item['parameters'].forEach((item,index)=> {
          positionChannelDatasets.push({
            label: item['name'],
            data: item['scores'],
            backgroundColor: [
              component.generateRandomColor(item['name'])
            ]
          })
        })

        component.positionChannelProps.push(
          <TotalStatistic key={ item['name'] } title={ item['name'] } labels={ xAxis } datasets={ positionChannelDatasets } />
        )

      })
    }

    let parameterInfo = [];
    if(this.state.allInfo.length != 0) {
      this.state.allInfo['parameters'].forEach((item, index)=> {
        parameterInfo.push(
          <StatisticInfo title={ item['name'] } score={ item['score'] } diff={ item['diffScore'] }  />
        )
      })
    }


    return (
      <div className="right_col" role="main">
        <div className="">
          <div className="page-title">
            <div className="title_left">
              <h3>All Statistic</h3>
            </div>
          </div>

          <div className="clearfix"></div>

          <div className="row tile_count">
            <StatisticInfo title="Total Agent" score={ this.state.allInfo['totalAgent'] } />
            <StatisticInfo title="Total Review Score" score={ this.state.allInfo['finalScore'] } diff={ this.state.allInfo['diffFinalScore'] }  />
            { parameterInfo }
          </div>

          { this.totalProps }

          { this.positionChannelProps }
        </div>
      </div>
    );
  }
}

class TotalStatistic extends React.Component {
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


class StatisticInfo extends React.Component {
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



export default StatisticAll;
