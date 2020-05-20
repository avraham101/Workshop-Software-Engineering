import React, {Component} from "react";
import BackGroud from "../../../Component/BackGrond";
import Menu from "../../../Component/Menu";
import Title from "../../../Component/Title";
import Button from "../../../Component/Button";
import {send} from '../../../Handler/ConnectionHandler';
import {pass} from "../../../Utils/Utils";
import DivBetter from "../../../Component/DivBetter";


class WatchRevenu extends Component {

    constructor() {
      super();
      this.pathname = "/admin/watchRevenu";
      this.state = {
        day_income:100,
        i_day:undefined,
        i_month:undefined,
        i_year:undefined,
        s_day:undefined,
        s_month:undefined,
        s_year:undefined,
        selected_day_income:undefined,
      };
      this.get_Date  = this.get_Date.bind(this);
      this.pprint_Date = this.pprint_Date.bind(this);
      this.render_SelectedDate = this.render_SelectedDate.bind(this);
      this.onChange_day = this.onChange_day.bind(this);
      this.onChange_month = this.onChange_month.bind(this);
      this.onChange_year = this.onChange_year.bind(this);
      this.render_SelectedIncome = this.render_SelectedIncome.bind(this);
    }

    get_Date() {
      if(this.state.day===undefined) {
        let date = new Date();
        let day = date.getDate();
        let month = date.getMonth() + 1;
        let year = date.getFullYear();
        this.setState({day:day, month:month, year:year, i_day:day, i_month:month, i_year:year});
      }
    }

    pprint_Date() {
      return this.state.day +'.'+this.state.month+'.'+this.state.year;
    }

    onChange_day(e) {
      if(e.target.value>31) {
        alert('Cant Enter a day gretter then 31');
      }
      else if(e.target.value<1) {
        alert('Cant Enter a day lower then 1');
      }
      else {
        this.setState({i_day:e.target.value});
      }
    }

    onChange_month(e) {
      if(e.target.value>12) {
        alert('Cant Enter a month gretter then 12');
      }
      else if(e.target.value<1) {
        alert('Cant Enter a month lower then 1');
      }
      else {
        this.setState({i_month:e.target.value});
      }
    }

    onChange_year(e) {
      if(e.target.value>9999) {
        alert('Cant Enter a year gretter then 9999');
      }
      else if(e.target.value<1) {
        alert('Cant Enter a year lower then 1');
      }
      else {
        this.setState({i_year:e.target.value});
      }
    }

    render_SelectedIncome() {
      if(this.state.selected_day_income===undefined)
        return;
      return ( 
              <div style={{float:'left', width:'30%', marginLeft:'35%'}}>
                <DivBetter>
                  <h2 style={{marginTop:0, border:'1px solid black', backgroundColor:'#63FFA7'}}> 
                      Day Income </h2>
                  <h3>  {this.state.s_day +'.'+this.state.s_month+'.'+this.state.s_year} </h3> 
                  <h3> {this.state.selected_day_income} $</h3>
                </DivBetter>
              </div>)
    }

    render_SelectedDate() {
      let onClick = (e) => {
        if(this.state.year < this.state.i_year) {
          alert('Cant Select A Year greater then the current Year');
        }
        else if(this.state.month < this.state.i_month) {
          alert('Cant Select A Month greater then the current Month');
        }
        else if(this.state.day < this.state.i_day) {
          alert('Cant Select A Day greater then the current Day');
        }
        else {
          this.setState({ s_year:this.state.i_year, s_month:this.state.i_month, s_day:this.state.i_day,
                          selected_day_income:100, //TODO
                          i_year:this.state.year, i_month:this.state.month, i_day:this.state.day})
        }
      } 
      return (
        <div style={{float:'left',width:'100%', paddingTop:'15px'}}>
            <div style={{float:'left',width:'60%', paddingLeft:'20%'}}>
              <div style={{float:'left', width:'32%', border:'2px solid black', textAlign:'center'}}>
                <p style={{marginTop:0, marginBottom:10, fontSize:21, width:'99.7%', backgroundColor:'#FFF9A8'}}> Day </p>
                <input style={{textAlign:'center',marginBottom:10, width:'50%'}} type={'number'} min={1} max={31} value={this.state.i_day} 
                    onChange={this.onChange_day} />
              </div>
              <div style={{float:'left', width:'32%', border:'2px solid black',textAlign:'center'}}>
                <p style={{marginTop:0, marginBottom:10, fontSize:21, width:'99.7%', backgroundColor:'#FFF9A8'}}> Month </p>
                <input style={{textAlign:'center' ,marginBottom:10, width:'50%'}} type={'number'} min={1} max={12} value={this.state.i_month} 
                    onChange={this.onChange_month} />
              </div>
              <div style={{float:'left', width:'32%', border:'2px solid black', textAlign:'center'}}>
                <p style={{marginTop:0,  marginBottom:10, fontSize:21, width:'99.7%', backgroundColor:'#FFF9A8'}}> Year </p>
                <input style={{textAlign:'center', marginBottom:10, width:'50%'}} type={'number'} min={1} max={9999} value={this.state.i_year} 
                    onChange={this.onChange_year} />
              </div>
            </div>
            <div style={{float:'left', width:'100%'}}>
              <Button text="View Date Income" onClick ={onClick}/>
              {this.render_SelectedIncome()}
            </div>
        </div>
      )
    }

    render() {
        this.get_Date();
        return (
            <BackGroud>
                <Menu state={this.props.location.state}/>
                <Title title="System Revenu"/>
                <body>
                  <div style={{float:'left', width:'30%', textAlign:'center', 
                                border:'1px solid black', marginLeft:'35%'}}>
                    <DivBetter>
                      <h2 style={{marginTop:0, border:'1px solid black', backgroundColor:'#D4E6FF'}}> 
                          Day Income </h2>
                      <h3> {this.pprint_Date()} </h3>
                      <h3> {this.state.day_income} $</h3>
                    </DivBetter>
                  </div>
                  {this.render_SelectedDate()}
                </body>
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