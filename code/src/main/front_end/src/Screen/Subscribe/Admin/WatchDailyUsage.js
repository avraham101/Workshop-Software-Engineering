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
      this.state = {
        dStart:undefined,
        mStart:undefined,
        yStart:undefined,
        dEnd:undefined,
        mEnd:undefined,
        yEnd:undefined,
        lst_enteries:[test],
      };
      this.changeSday = this.changeSday.bind(this);
      this.changeEday = this.changeEday.bind(this);
      this.changeSmonth = this.changeSmonth.bind(this);
      this.changeEmonth = this.changeEmonth.bind(this);
      this.changeSyear = this.changeSyear.bind(this);
      this.changeEyear = this.changeEyear.bind(this);
      this.submitDate = this.submitDate.bind(this);
      this.saveCVS = this.saveCVS.bind(this);
    }

    ipos() {
      let date = new Date();
      let day = date.getDate();
      let month = date.getMonth() + 1;
      let year = date.getFullYear();
      this.setState({dEnd:day, mEnd:month, yEnd:year, dStart:day, mStart:month, yStart:year});
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

    saveCVS() {
      let output = [];
      let rows = [['','date', 'guests', 'subscribes', 'managers', 'owners', 'admins']];
      let data = this.state.lst_enteries;
      data.forEach(element=> {
        rows.push([element.date,element.guestNumber, element.subscribeNumber, element.managerNumber, element.ownerNumber,
                      element.adminNumber]);
      });
      console.warn(rows);
      rows.forEach(element=>{
        output.push(element.join(','));
        output.push("\n");
      });
      console.warn(output);
      var a = document.createElement('a');
      var csvData = new Blob([output], { type: 'text/csv' }); 
      var csvUrl = URL.createObjectURL(csvData);
      a.href = csvUrl;      
      a.target = "_blank";
      a.download = "usageReport.csv";
      a.click();
    }

    renderRangeGraph() {
      if( this.state.lst_enteries === undefined)
        return;
      let lst = this.state.lst_enteries;
      let output = [];
      lst.forEach(element => {
        output.push(this.renderGraph(element));
      });
      return (  <div style={{float:'left', width:'90%', marginLeft:'5%', border:'1px solid black', paddingBottom:'10px'}}>
                  <div style={{float:'left', width:'10%', marginLeft:'89%'}}>
                    <Button text='Save to CVS' onClick={this.saveCVS}/>
                  </div>
                  {output}
                </div>);
    }

    changeSday(event) {
      let value = event.target.value;
      let t = parseInt(value);
      if( t > 31 || t < 0) {
        alert("Wrong value for Start day");
        let date = new Date();
        event.target.value = date.getDate();
      }
      this.setState({dStart:t});
    }

    changeEday(event) {
      let value = event.target.value;
      let t = parseInt(value);
      if( t > 31 || t < 1) {
        alert("Wrong value for End day");
        let date = new Date();
        event.target.value = date.getDate();
      }
      this.setState({dEnd:t});
    }

    changeSmonth(event) {
      let value = event.target.value;
      let t = parseInt(value);
      if( t > 12 || t < 1) {
        alert("Wrong value for Start month");
        let date = new Date();
        event.target.value = date.getMonth() + 1;
      }
      this.setState({mStart:t});
    }

    changeEmonth(event) {
      let value = event.target.value;
      let t = parseInt(value);
      if( t > 12 || t < 1) {
        alert("Wrong value for End month");
        let date = new Date();
        event.target.value = date.getMonth() + 1;
      }
      this.setState({mEnd:t});
    }
    
    changeSyear(event) {
      let value = event.target.value;
      let t = parseInt(value);
      let date = new Date();
      if( t > date.getFullYear() || t < 0) {
        alert("Wrong value for Start year");
        event.target.value = date.getFullYear();
      }
      this.setState({yStart:t});
    }

    changeEyear(event) {
      let value = event.target.value;
      let t = parseInt(value);
      let date = new Date();
      if( t > date.getFullYear() || t < 0) {
        alert("Wrong value for End year");
        event.target.value = date.getFullYear();
      }
      this.setState({yEnd:t});
    }

    submitDate() {
      if(this.state.dStart === undefined ||  this.state.mStart === undefined || this.state.yStart === undefined) {
        alert("Didnt Select Start Date correctly");
      }
      else if(this.state.dEnd === undefined ||  this.state.mEnd === undefined || this.state.yEnd === undefined) {
        alert("Didnt Select End Date correctly");
      }
      else if(this.state.yStart>this.state.yEnd) {
        alert("Cant Select Start year greater then End year");
      }
      else if(this.state.yStart==this.state.yEnd && this.state.mStart>this.state.mEnd) {
        alert("Cant Select Start month greater then End month");
      }
      else if(this.state.yStart==this.state.yEnd && this.state.mStart==this.state.mEnd && this.state.dStart>this.state.dEnd) {
        alert("Cant Select Start day greater then End day");
      }
      else {
        //TOOD: call send
      }

        
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
              <Datey changeDay = {this.changeSday} changeMon = {this.changeSmonth} changeYear = {this.changeSyear}/>
            </div>
            <div style={{float:'left', width:'10%',}}>
                <p style={{textAlign:'center', fontSize:'20px'}}> End Date: </p>
            </div>
            <div style={{float:'left', width:'39%'}}>
              <Datey changeDay = {this.changeEday} changeMon = {this.changeEmonth} changeYear = {this.changeEyear}/>
            </div>
            <div style={{float:'left', width:'100%', textAlign:'center', marginTop:'2px'}}>
              <Button text='Show' onClick={this.submitDate}/>
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
                <body>
                  {this.renderGraph(test)}
                  {this.renderSelectDate()}
                </body>
            </BackGroud>
        );
    }

}

export default WatchDailyUsage;


class Datey extends Component {

  render() {
    //this.props.
    return (<div style = {{float:'left', width:'100%'}}>
              <div style={{float:'left', width:'32%', border:'1px solid black', textAlign:'center', backgroundColor:'#FBFFF7'}}>
                <p style={{marginTop:0, marginBottom:10, fontSize:21, width:'99.7%', backgroundColor:'#FFF9A8'}}> Day </p>
                <input style={{textAlign:'center',marginBottom:10, width:'50%'}} type={'number'} min={1} max={31}
                    onChange={this.props.changeDay} />
              </div>
              <div style={{float:'left', width:'32%', border:'1px solid black', textAlign:'center',backgroundColor:'#FBFFF7'}}>
                <p style={{marginTop:0, marginBottom:10, fontSize:21, width:'99.7%', backgroundColor:'#FFF9A8'}}> Month </p>
                <input style={{textAlign:'center',marginBottom:10, width:'50%'}} type={'number'} min={1} max={12} 
                    onChange={this.props.changeMon} />
              </div>
              <div style={{float:'left', width:'32%', border:'1px solid black', textAlign:'center', backgroundColor:'#FBFFF7'}}>
                <p style={{marginTop:0, marginBottom:10, fontSize:21, width:'99.7%', backgroundColor:'#FFF9A8'}}> Year </p>
                <input style={{textAlign:'center',marginBottom:10, width:'50%'}} type={'number'} min={0} max={2020} 
                    onChange={this.props.changeYear} />
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