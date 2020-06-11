import React, {Component} from "react";
import BackGroud from "../../../Component/BackGrond";
import Menu from "../../../Component/Menu";
import Title from "../../../Component/Title";
import Button from "../../../Component/Button";
import {send} from '../../../Handler/ConnectionHandler';
import {pass} from "../../../Utils/Utils";
import DivBetter from "../../../Component/DivBetter";


class WatchDailyUsage extends Component {

    constructor() {
      super();
      this.pathname = "/admin/watchDailyUsage";
      this.state = { };
    }

    findMax(data) {
      let max = data.guestNumber;
      if(data.subscribeNumber > max) 
        max = data.subscribeNumber;
      if(data.managerNumber > max) 
        max = data.managerNumber;
      if(data.ownerNumber > max)
        max = data.ownerNumber;
      if(data.adminNumber > max)
        max = data.adminNumber;
      return max;
    }

    evalPresentage(value, max) {
      let result = (value/max) * 100;
      return result  + '%';
    }

    evalDataPresentages(data, max) {
      let guestNumber = this.evalPresentage(data.guestNumber, max); 
      let subscribeNumber = this.evalPresentage(data.subscribeNumber, max);
      let managerNumber = this.evalPresentage(data.managerNumber, max);
      let ownerNumber = this.evalPresentage(data.ownerNumber, max);
      let adminNumber = this.evalPresentage(data.adminNumber, max);
      return {guestNumber: guestNumber, subscribeNumber: subscribeNumber, managerNumber: managerNumber, 
                ownerNumber: ownerNumber, adminNumber: adminNumber};
    }

    renderBar(name, value, presentage, color) {
      return (
          <div style={{float:'left', width:'100%', borderBottom:'black solid 1px', paddingBottom:'5px'}}>
            <div style={{float:'left', width:'15%'}}>
              <p style={{textAlign:'center', fontSize:'16px'}}> {name} </p>
            </div>
            <div style={{float:'left', width:'83%', marginTop:'5px'}}>
              <div style={{width:presentage,backgroundColor:color, border:'black solid 1px'}}>
                <p style={{textAlign:'right', color:'black', paddingRight:'5px', fontSize:'14px', fontWeight:'bold'}}> {value} </p>
              </div>
            </div>
          </div>);
    }

    renderGraph(data) {
      let max = this.findMax(data);
      let pre = this.evalDataPresentages(data, max);
      let output = [];
      output.push(this.renderBar('guests' ,data.guestNumber, pre.guestNumber, '#AFF78F'));
      output.push(this.renderBar('subscribes', data.subscribeNumber, pre.subscribeNumber, '#85F7CD'));
      output.push(this.renderBar('managers',data.managerNumber, pre.managerNumber, '#88F4F7'));
      output.push(this.renderBar('owners',data.ownerNumber, pre.ownerNumber, '#827EF7'));
      output.push(this.renderBar('admins',data.adminNumber, pre.adminNumber, '#D27CF7'));
      return (<div style={{float:'left', width:'100%'}}>
                <div style={{float:'left', width:'99%'}}>
                  <h3 style={{textAlign:'center'}}> {data.date} </h3>
                </div>
                <div style={{float:'left', width:'90%', marginLeft:'5%', border:'1px solid black'}}>
                {output}
                </div>
              </div>);
    }

    renderRangeGraph() {
      let lst = [test, test];
      let output = [];
      lst.forEach(element => {
        output.push(this.renderGraph(element));
      });
      return output;
    }

    renderSelectDate() {
      return (
        <div style={{float:'left', width:'98%', marginLeft:'1%', marginTop:'10px', marginBottom:'10px', backgroundColor:'#E6F0FA'}}>
            <div style={{float:'left', width:'100%', textAlign:'center'}}>
              <h2> Select Range of Dates </h2>
            </div>
            <div style={{float:'left', width:'10%',}}>
                <p style={{textAlign:'center', fontSize:'20px'}}> Start Date: </p>
            </div>
            <div style={{float:'left', width:'39%'}}>
              <Date/>
            </div>
            <div style={{float:'left', width:'10%',}}>
                <p style={{textAlign:'center', fontSize:'20px'}}> End Date: </p>
            </div>
            <div style={{float:'left', width:'39%'}}>
              <Date/>
            </div>
            <div style={{float:'left', width:'100%', textAlign:'center', marginTop:'2px'}}>
              <Button text='Show'/>
            </div>
            {this.renderRangeGraph()}
        </div>
      );
    }

    render() {
        return (
            <BackGroud>
                <Menu state={this.props.location.state}/>
                <Title title="Daily Usage"/>
                {this.renderGraph(test)}
                {this.renderSelectDate()}
            </BackGroud>
        );
    }

}

export default WatchDailyUsage;


class Date extends Component {

  render() {
    return (<div style = {{float:'left', width:'100%'}}>
              <div style={{float:'left', width:'32%', border:'1px solid black', textAlign:'center', backgroundColor:'#FBFFF7'}}>
                <p style={{marginTop:0, marginBottom:10, fontSize:21, width:'99.7%', backgroundColor:'#FFF9A8'}}> Day </p>
                <input style={{textAlign:'center',marginBottom:10, width:'50%'}} type={'number'} min={1} max={31} 
                    onChange={()=>{}} />
              </div>
              <div style={{float:'left', width:'32%', border:'1px solid black', textAlign:'center',backgroundColor:'#FBFFF7'}}>
                <p style={{marginTop:0, marginBottom:10, fontSize:21, width:'99.7%', backgroundColor:'#FFF9A8'}}> Month </p>
                <input style={{textAlign:'center',marginBottom:10, width:'50%'}} type={'number'} min={1} max={12} 
                    onChange={()=>{}} />
              </div>
              <div style={{float:'left', width:'32%', border:'1px solid black', textAlign:'center', backgroundColor:'#FBFFF7'}}>
                <p style={{marginTop:0, marginBottom:10, fontSize:21, width:'99.7%', backgroundColor:'#FFF9A8'}}> Year </p>
                <input style={{textAlign:'center',marginBottom:10, width:'50%'}} type={'number'} min={0} max={2020} 
                    onChange={()=>{}} />
              </div>
           </div>)
  }
}
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

const test = {
  date: '02/12/1994',
  guestNumber: 10,
  subscribeNumber: 20,
  managerNumber: 5,
  ownerNumber: 30,
  adminNumber: 2,
}