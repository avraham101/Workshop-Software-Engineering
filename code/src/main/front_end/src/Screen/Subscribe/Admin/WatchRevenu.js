import React, {Component} from "react";
import BackGroud from "../../../Component/BackGrond";
import Menu from "../../../Component/Menu";
import Title from "../../../Component/Title";
import Button from "../../../Component/Button";
import {send} from '../../../Handler/ConnectionHandler';
import {pass} from "../../../Utils/Utils";


class WatchRevenu extends Component {

    constructor() {
      super();
      this.state = {
        day_income:100,
        i_date:1,
        i_month:1,
        i_year:2020,
      };
      this.get_Date();
      this.pprint_Date = this.pprint_Date.bind(this);
    }

    get_Date() {
      let date = new Date();
      let day = date.getDay();
      let month = date.getMonth() + 1;
      let year = date.getFullYear();
      this.setState({day:day, month:month, year:year})
    }

    pprint_Date() {
      return this.state.day +'.'+this.state.month+'.'+this.state.year;
    }

    render() {
      this.create_list();
        return (
            <BackGroud>
                <Menu state={this.props.location.state}/>
                <Title title="System Revenu"/>
                <body>
                  <div>
                    <h2> Day Income </h2>
                    <h3> {this.pprint_Date()} </h3>
                    <p> {this.state.day_income} $</p>
                  </div>
                </body>
                {this.renderPurchase()} 
            </BackGroud>
        );
    }

}

export default WatchRevenu;

const style_sheet = {
    color: "black",
    padding: "1px",
    margin: "10px",
    lineHeight: 5,
    fontFamily: "Arial",
    width:"80%",
  }

const select_style={
  display: "flex",
  justifyContent: "center",
  alignItems: "center"
}