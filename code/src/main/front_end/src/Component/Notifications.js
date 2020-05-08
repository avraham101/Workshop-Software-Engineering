import React, {Component} from 'react';
import {connect, sendMessage} from '../Handler/WebSocketHandler'
import {send} from '../Handler/ConnectionHandler';
import Button from './Button';
var flag_connected = false;
var products=[];
var products_notification = [];
var replays=[];
var replays_notification = [];
var managment=[];
var managment_notification = [];
var size_0= 0;
var size_1= 0;
var size_2= 0;
var show = undefined;// PRODUCTS, REPLAYS , MANAGMENT
export function turnOf(){
  flag_connected = false;
};

export class Notifications extends Component {
  
  constructor(props){
    super(props)
    this.state = {
      color_0: '#72CC87',
      color_1: '#72CC87',
      color_2: '#72CC87',
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
    if(opcode==='Buy_Product') {
      let list = element.value;
      list.forEach(element=>{
        products.push(element); //proudct data
      });
      products_notification.push(element.id); //id of notification
    }
    else if(opcode==='Replay_Request') {
      replays.push(element.value); //Request
      replays_notification.push(element.id); //id of notification
    }
    else if(opcode==='Removed_From_Managment') {
      managment.push(element.value); //String store name
      managment_notification.push(element.id); //id of notification
    }
  }

  parseList(list) {
    list.forEach(element=>this.parseNotification(element))
  }

  handleNotification(listNotification) {
    this.parseList(listNotification);
    // let msg = 'resived notifications:\n';
    // msg+= 'products bougt: '+products.length+'\n';
    // msg+= 'replays recived: '+replays.length+'\n';
    // msg+= 'removed from store managment: '+ managment.length;
    // alert(msg);
    size_0 = products.length;
    size_1 = replays.length;
    size_2 = managment.length;
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
    if(show==='PRODUCTS') {
      let msg = products_notification;
      products=[];
      products_notification=[];
      size_0= 0;
      send('/ack?id='+this.props.id,'POST',msg,()=>{});
    }
    else if(show==='REPLAYS') {
      let msg = replays_notification;
      replays=[];
      replays_notification=[];
      size_1= 0;
      send('/ack?id='+this.props.id,'POST',msg,()=>{});
    }
    else if(show==='MANAGMENT') {
      let msg = managment_notification;
      managment=[];
      managment_notification=[];
      size_2= 0;
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
          <p style={{textAlign:'center', padding: 4}}> Store Name: {element.storeName} </p>
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
          <div style={{border: "2px solid black", padding:2}}>
            <p style={{textAlign:'center', padding: 4}}> Store Name: {element.storeName } </p>
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
        <div style={{border: "2px solid black", padding:2}}>
          <p style={{textAlign:'center', padding: 4}}> Store Name: {element} </p>
        </div>
      )
    })
    return output;
  }

  renderPopUp() {
    if(show===undefined)
      return
    if(show==='PRODUCTS')
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
    if(show==='REPLAYS')
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
    if(show==='MANAGMENT')
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
    return
  }

  render() {
    this.connectHandler(this.props.id);
    return (
      <div style={{width:'100%',backgroundColor:'#72CC87',margin:0}}>
        <div style={{float:'left',width:'33%',backgroundColor:this.state.color_0,maring:0}}
         onMouseOver={()=>this.setState({color_0: '#B3D1F0'})}
         onMouseLeave={()=>this.setState({color_0: '#72CC87'})}
         onClick={()=>{show='PRODUCTS';this.setState({})}}>
          <h4 style={{textAlign:'center',margin:0}}> Products 
            <em style={{color:'#AD200B', fontSize:21}}> {size_0} </em>
          </h4>
        </div>
        <div style={{float:'left',width:'33%',backgroundColor:this.state.color_1,maring:0}}
            onMouseOver={()=>this.setState({color_1: '#B3D1F0'})}
            onMouseLeave={()=>this.setState({color_1: '#72CC87'})}
            onClick={()=>{show='REPLAYS';this.setState({})}}>
          <h4 style={{textAlign:'center',margin:0}}> Replays 
            <em style={{color:'#AD200B',fontSize:21}}> {size_1} </em>
          </h4>
        </div>
        <div style={{float:'left',width:'34%',backgroundColor:this.state.color_2,maring:0}}
            onMouseOver={()=>this.setState({color_2: '#B3D1F0'})}
            onMouseLeave={()=>this.setState({color_2: '#72CC87'})}
            onClick={()=>{show='MANAGMENT';this.setState({})}}>
          <h4 style={{textAlign:'center',margin:0}}> Removed Managment 
            <em style={{color:'#AD200B',fontSize:21}}> {size_2} </em>
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