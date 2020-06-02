import React, {Component} from 'react';
import {connect, sendMessage} from '../Handler/WebSocketHandler'
import {send} from '../Handler/ConnectionHandler';
import Button from './Button';
var flag_connected = false;
var products = [];
var products_notification = [];
var replays = [];
var replays_notification = [];
var managment = [];
var managment_notification = [];
var waiting_approvels = [];
var waiting_approvels_notification = [];
var approvels = [];
var approvels_notification = [];
var size_0= 0;
var size_1= 0;
var size_2= 0;
var size_3= 0;
var size_4= 0;
var show = undefined;// PRODUCTS, REPLAYS , MANAGMENT

export function turnOf(){
  flag_connected = false;
};


var NOTIFICATION_PRODUCT = 'Buy_Product';
var NOTIFICATION_REPLAY = 'Reply_Request';
var NOTIFICATION_REMOVE_MANAGER = 'Removed_From_Management';
var NOTIFICATION_WAITING_APPROVED = 'Approve_Owner';
var NOTIFICATION_APPROVED = 'Add_Owner';
var SHOW_PRODUCTS = 'PRODUCTS';
var SHOW_REPLAYS = 'REPLAYS';
var SHOW_MANAGMENT = 'MANAGMENT';
var SHOW_WAITING_APPROVES = 'WAITING_APPROVES';
var SHOW_APPROVED = 'APPROVED';

export class Notifications extends Component {
  
  constructor(props){
    super(props)
    this.state = {
      color_0: '#92BAFF',
      color_1: '#92BAFF',
      color_2: '#92BAFF',
      color_3: '#92BAFF',
      color_4: '#92BAFF',
    }
    this.handleNotification = this.handleNotification.bind(this);
    this.parseNotification = this.parseNotification.bind(this);
    this.parseList = this.parseList.bind(this);
    this.renderPopUp = this.renderPopUp.bind(this);
    this.buttonX = this.buttonX.bind(this);
  }

  parseNotification(element) {
    console.log(element);
    let opcode = element.reason;
    if(opcode===NOTIFICATION_PRODUCT) {
      let list = element.value;
      list.forEach(element=>{
        products.push(element); //proudct data
      });
      products_notification.push(element.id); //id of notification
    }
    else if(opcode===NOTIFICATION_REPLAY) { 
      replays.push(element.value); //Request
      replays_notification.push(element.id); //id of notification
    }
    else if(opcode===NOTIFICATION_REMOVE_MANAGER) {
      managment.push(element.value); //String store name
      managment_notification.push(element.id); //id of notification
    }
    else if(opcode===NOTIFICATION_WAITING_APPROVED) {
      let list = element.value;
      let obj = {store: list[0], name:list[0]};
      waiting_approvels.push(obj);
      waiting_approvels_notification.push(element.id);
    }
    else if(opcode===NOTIFICATION_APPROVED) {
      let list = element.value;
      let obj = {store: list[0], name:list[0]};
      approvels.push(obj);
      approvels_notification.push(element.id);
    }
  }

  parseList(list) {
    list.forEach(element=>this.parseNotification(element))
  }

  handleNotification(listNotification) {
    this.parseList(listNotification);
    size_0 = products.length;
    size_1 = replays.length;
    size_2 = managment.length;
    size_3 = waiting_approvels.length;
    size_4 = approvels.length;
    this.setState({});
    this.forceUpdate();
  }

  connectHandler(id) {
    if(flag_connected===false) {
      products=[];
      products_notification=[];
      replays=[];
      replays_notification=[];
      managment=[];
      managment_notification=[];
      size_0= 0;
      size_1= 0;
      size_2= 0;
      connect(''+id,this.handleNotification);
      flag_connected=true; 
    }
  }

  buttonX() {
    if(show===SHOW_PRODUCTS) {
      let msg = products_notification;
      products=[];
      products_notification=[];
      size_0= 0;
      send('/ack?id='+this.props.id,'POST',msg,()=>{});
    }
    else if(show===SHOW_REPLAYS) {
      let msg = replays_notification;
      replays=[];
      replays_notification=[];
      size_1= 0;
      send('/ack?id='+this.props.id,'POST',msg,()=>{});
    }
    else if(show===SHOW_MANAGMENT) {
      let msg = managment_notification;
      managment=[];
      managment_notification=[];
      size_2= 0;
      send('/ack?id='+this.props.id,'POST',msg,()=>{});
    }
    else if(show===SHOW_WAITING_APPROVES) {
      let msg = waiting_approvels_notification;
      waiting_approvels=[];
      waiting_approvels_notification=[];
      size_3= 0;
      send('/ack?id='+this.props.id,'POST',msg,()=>{});
    }
    else if(show===SHOW_APPROVED) {
      let msg = approvels_notification;
      approvels=[];
      approvels_notification=[];
      size_4= 0;
      send('/ack?id='+this.props.id,'POST',msg,()=>{});
    }
    show=undefined;
    this.setState({});
  }

  renderProudcts(){
    let output = [];
    if(products.length===0)
      return <p style={{textAlign:'center'}}> No Product Notifications Yet </p>;
    products.forEach(element=>{
      output.push(
        <div style={{border: "2px solid black", padding:2, backgroundColor:'white'}}>
          <p style={{textAlign:'center', padding: 4}}> Proudct Name: {element.productName } </p>
          <p style={{textAlign:'center', padding: 4}}> Store Name: {element.store} </p>
          <p style={{textAlign:'center', padding: 4}}> Amount Bought: {element.amount} </p>
        </div>
      );
    });
    return output;
  }

  renderReplays(){
    let output = [];
    if(replays.length===0)
      return <p style={{textAlign:'center'}}> No Replays Notification Yet </p>;
    replays.forEach(element=>{
        output.push(
          <div style={{border: "2px solid black", padding:2, backgroundColor:'white'}}>
            <p style={{textAlign:'center', padding: 4}}> Store Name: {element.store } </p>
            <p style={{textAlign:'center', padding: 4}}> Content: {element.content} </p>
            <p style={{textAlign:'center', padding: 4}}> Comment: {element.comment} </p>
            <p style={{textAlign:'center', padding: 4}}> Id: {element.id} </p>
          </div>
        )
    })
    return output;
  }

  renderManagment() {
    let output = [];
    if(managment.length===0)
      return <p style={{textAlign:'center'}}> No Removes From Management Yet </p>;
    managment.forEach(element=>{
      output.push(
        <div style={{border: "2px solid black", padding:2, backgroundColor:'white'}}>
          <p style={{textAlign:'center', padding: 4,}}> Store Name: {element} </p>
        </div>
      )
    })
    return output;
  }

  renderWaitingApprovals() {
    let output = [];
    if(waiting_approvels.length ===0)
      return <p style={{textAlign:'center'}}> No New Waiting Approvels For Owners Yet </p>;
    waiting_approvels.forEach(element=> {
      output.push(
        <div style={{border: "2px solid black", padding:2, backgroundColor:'white'}}>
          <p style={{textAlign:'center', padding: 4,}}> Store Name: {element.store} </p>
          <p style={{textAlign:'center', padding: 4,}}> Owner Name: {element.owner} </p>
        </div>
      )
    })
    return output;
  }

  renderApprovals() {
    let output = [];
    if(approvels.length ===0)
      return <p style={{textAlign:'center'}}> No New Approvels For Being Owners </p>;
    approvels.forEach(element=> {
      output.push(
        <div style={{border: "2px solid black", padding:2, backgroundColor:'white'}}>
          <p style={{textAlign:'center', padding: 4,}}> Store Name: {element.store} </p>
        </div>
      )
    })
    return output;
  }

  renderPopUp() {
    if(show===undefined)
      return
    if(show===SHOW_PRODUCTS) {
      return (
        <div style={popup}>
          <div style={popup_inner}>
            <h2 style={{textAlign:'center', backgroundColor:'#150A80', width:'100%', marginTop:0, color:'white'}}>
                 Products</h2>
            <div>
              {this.renderProudcts()}
              <Button text="X" onClick={this.buttonX}/>
            </div>
          </div>
        </div>
      )
    }
    if(show===SHOW_REPLAYS) {
      return (
        <div style={popup}>
        <div style={popup_inner}>
          <h2 style={{textAlign:'center', backgroundColor:'#150A80', width:'100%', marginTop:0, color:'white'}}>
               Replays</h2>
          <div>
            {this.renderReplays()}
            <Button text="X" onClick={this.buttonX}/>
          </div>
        </div>
      </div>
      )
    }
    if(show===SHOW_MANAGMENT){
      return (
        <div style={popup}>
          <div style={popup_inner}>
            <h2 style={{textAlign:'center', backgroundColor:'#150A80', width:'100%', marginTop:0, color:'white'}}>
                Remove From Manager</h2>
            <div>
              {this.renderManagment()}
              <Button text="X" onClick={this.buttonX}/>
            </div>
          </div>
        </div>
      )
    }
    if(show===SHOW_WAITING_APPROVES) {
      return (
        <div style={popup}>
        <div style={popup_inner}>
          <h2 style={{textAlign:'center', backgroundColor:'#150A80', width:'100%', marginTop:0, color:'white'}}>
              New Waiting Approvals</h2>
          <div>
            {this.renderWaitingApprovals()}
            <Button text="X" onClick={this.buttonX}/>
          </div>
        </div>
      </div> 
      )
    }
    if(show==SHOW_APPROVED){
      return (
        <div style={popup}>
        <div style={popup_inner}>
          <h2 style={{textAlign:'center', backgroundColor:'#150A80', width:'100%', marginTop:0, color:'white'}}>
              New Approvals </h2>
          <div>
            {this.renderApprovals()}
            <Button text="X" onClick={this.buttonX}/>
          </div>
        </div>
      </div> 
      )
    }
        
    return
  }

  render() {
    this.connectHandler(this.props.id);
    return (
      <div style={{width:'100%',backgroundColor:'#92BAFF',margin:0}}>
        <div style={{float:'left',width:'19.9%',backgroundColor:this.state.color_0,maring:0,
                      borderRight:'1px solid white'}}
         onMouseOver={()=>this.setState({color_0: '#BEDEFF'})}
         onMouseLeave={()=>this.setState({color_0: '#92BAFF'})}
         onClick={()=>{show='PRODUCTS';this.setState({})}}>
          <h4 style={{textAlign:'center',margin:0}}> Upcoming Phurchases 
            <em style={{color:'black', fontSize:23}}> {size_0} </em>
          </h4>
        </div>
        <div style={{float:'left',width:'19.9%',backgroundColor:this.state.color_1,maring:0,
                      borderRight:'1px solid white'}}
            onMouseOver={()=>this.setState({color_1: '#BEDEFF'})}
            onMouseLeave={()=>this.setState({color_1: '#92BAFF'})}
            onClick={()=>{show=SHOW_REPLAYS;this.setState({})}}>
          <h4 style={{textAlign:'center',margin:0}}> Replays 
            <em style={{color:'black',fontSize:23}}> {size_1} </em>
          </h4>
        </div>
        <div style={{float:'left',width:'19.9%',backgroundColor:this.state.color_2,maring:0,
                      borderRight:'1px solid white'}}
            onMouseOver={()=>this.setState({color_2: '#BEDEFF'})}
            onMouseLeave={()=>this.setState({color_2: '#92BAFF'})}
            onClick={()=>{show=SHOW_MANAGMENT;this.setState({})}}>
          <h4 style={{textAlign:'center',margin:0}}> Dismissal From Managment 
            <em style={{color:'black',fontSize:23}}> {size_2} </em>
          </h4>
        </div>
        <div style={{float:'left',width:'20%',backgroundColor:this.state.color_3,maring:0,
                      borderRight:'1px solid white'}}
            onMouseOver={()=>this.setState({color_3: '#BEDEFF'})}
            onMouseLeave={()=>this.setState({color_3: '#92BAFF'})}
            onClick={()=>{show=SHOW_WAITING_APPROVES;this.setState({})}}>
          <h4 style={{textAlign:'center',margin:0}}> Waiting Agreements 
            <em style={{color:'black',fontSize:23}}> {size_3} </em>
          </h4>
        </div>
        <div style={{float:'left',width:'20%',backgroundColor:this.state.color_4,maring:0}}
            onMouseOver={()=>this.setState({color_4: '#BEDEFF'})}
            onMouseLeave={()=>this.setState({color_4: '#92BAFF'})}
            onClick={()=>{show=SHOW_APPROVED;this.setState({})}}>
          <h4 style={{textAlign:'center',margin:0}}> Approvels Agreements 
            <em style={{color:'black',fontSize:23}}> {size_4} </em>
          </h4>
        </div>
        {this.renderPopUp()}
      </div>
    );
  }
}

const popup= { 
  position: 'fixed',
  width: '100%',  
  height: '100%',  
  top: '0',  
  left: '0',  
  right: '0',  
  bottom: '0',  
  margin: 'auto',  
  backgroundColor:'#7A919E',  
  opacity:'0.95',
}
const popup_inner= {  
  position: 'absolute',  
  left: '15%',  
  right: '15%',  
  top: '15%',  
  bottom: '15%',  
  margin: 'auto',  
  backgroundColor: '#9DBBCC',  
  overflowY: 'scroll'
}