import React, {Component} from 'react';
import history from '../Screen/history'
import {pass} from '../Utils/Utils'
import {send} from '../Handler/ConnectionHandler';
import {closeSocket} from '../Handler/WebSocketHandler'
import {Notifications, turnOf} from './Notifications';

const WHITE_BLUE= '#B3D1F0'
class MenuSubscribe extends Component {
  
  constructor(props){
    super(props)
    this.fromPath = this.props.fromPath;
    this.prevState = this.props.state;
    this.refresh = this.refresh.bind(this);
    this.checkIfAdmin = this.checkIfAdmin.bind(this);
    this.getAdminWatchPurchaseHistoryHtml = this.getAdminWatchPurchaseHistoryHtml.bind(this);
    this.checkIfManager = this.checkIfManager.bind(this);
    this.getManageStoresHtml = this.getManageStoresHtml.bind(this);
    this.admin = false;
    this.manager = false;
    this.state = { }
    this.checkIfAdmin();
    this.checkIfManager();
  }

  checkIfAdmin(){
    let id = this.props.state.id;
    send('/admin/allusers?id='+id,'GET','',(received)=>{
      console.log(received)
      if(received){
        let opt = ''+received.reason;
        if(opt === "Success")
          this.setState({admin: true})
        else
          this.setState({admin: false})
      }
    });
  }

  getAdminWatchPurchaseHistoryHtml(){
    let onHover = (event) => {
      event.currentTarget.style.backgroundColor = WHITE_BLUE;
    }
    let onLeave = (event) => {
        event.currentTarget.style.backgroundColor = '';
    }
    return(
        <th style={{margin:0, borderRight:'1px solid white'}}
            onClick={()=> pass(history,"/admin/storehistory",this.fromPath,this.props.state)}
            onMouseOver={onHover} onMouseLeave={onLeave}> Admin Watch Purchases </th>,
        <th style={{margin:0, borderRight:'1px solid white'}}
            onClick={()=> pass(history,"/admin/watchRevenu",this.fromPath,this.props.state)}
            onMouseOver={onHover} onMouseLeave={onLeave}> Admin Watch Revenu </th>);
  }

  checkIfManager(){
    let id = this.props.state.id;
    send('/managers/mystores?id='+id,'GET','',(received)=>{
      console.log(received)
      if(received){
        let opt = ''+received.reason;
        if(opt === "Success")
          this.setState({manager: true})
        else
          this.setState({manager: false})
      }
    });
  }

  getManageStoresHtml(){
    let onHover = (event) => {
      event.currentTarget.style.backgroundColor = WHITE_BLUE;
    }
    let onLeave = (event) => {
        event.currentTarget.style.backgroundColor = '';
    }
    return(
        <th style={{margin:0, borderRight:'1px solid white'}}
            onClick={()=> pass(history,"/storeManagement",this.fromPath,this.props.state)}
            onMouseOver={onHover} onMouseLeave={onLeave}> Manage Stores </th>)
  }

  logout() {
    let id = this.props.state.id;
    turnOf();
    send('/home/logout?id='+id,"POST",'',()=>{});
    closeSocket();
    pass(history,"/",this.fromPath,{id:id});
  }

  refresh() {
    this.setState({});
  }

  render() {
    let onHover = (event) => {
      event.currentTarget.style.backgroundColor = WHITE_BLUE;
    }
    let onLeave = (event) => {
        event.currentTarget.style.backgroundColor = '';
    }
    let onLeaveMenu = (event) => {
      event.currentTarget.style.backgroundColor = '#FFEB57';
    }
    return (
        <header style={{backgroundColor: '#FFC242', borderBottom:'2px solid #B38118',marginBottom:0}}>
          <table style={style_sheet}>
          <tr style={{width:'100%'}}>
            <th style={{margin:0, borderRight:'1px solid white', backgroundColor:'#FFEB57'}}
                onClick={() => pass(history,"/subscribe",this.fromPath,this.props.state)}
                onMouseOver={onHover} onMouseLeave={onLeaveMenu}> Menu </th>
            <th style={{margin:0, borderRight:'1px solid white'}}
                onClick={() => pass(history,"/viewStoresAndProducts",this.fromPath,this.props.state)}
                onMouseOver={onHover} onMouseLeave={onLeave}> Watch Store and Products </th>
            <th style={{margin:0, borderRight:'1px solid white'}}
                onClick={()=> pass(history,"/searchAndFilter",this.fromPath,this.props.state)}
                onMouseOver={onHover} onMouseLeave={onLeave}> Search and Filter Products </th>
            <th style={{margin:0, borderRight:'1px solid white'}}
                onClick={()=> pass(history,"/sendRequest",this.fromPath,this.props.state)}
                onMouseOver={onHover} onMouseLeave={onLeave}> Send Request </th>
            
            <th style={{margin:0, borderRight:'1px solid white'}}
                onClick={()=> pass(history,"/openStore",this.fromPath,this.props.state)}
                onMouseOver={onHover} onMouseLeave={onLeave}> Open Store </th>
            { this.state.manager ? this.getManageStoresHtml() : ""}
            { this.state.admin ? this.getAdminWatchPurchaseHistoryHtml() : ""}
            <th style={{margin:0, borderRight:'1px solid white'}}
                onClick={()=> pass(history,"/userWatchPurchasesHistory",this.fromPath,this.props.state)}
                onMouseOver={onHover} onMouseLeave={onLeave}> Purchases </th>
            <th style={{margin:0, borderRight:'1px solid white'}}
                onClick={()=>pass(history,"/viewMyCart",this.fromPath,this.props.state)}
                onMouseOver={onHover} onMouseLeave={onLeave}> Watch My Cart </th>
            <th onClick={()=>this.logout()}
                onMouseOver={onHover} onMouseLeave={onLeave}> Logout </th>
          </tr>
        </table>
        <Notifications id={this.props.state.id}/>
        </header>
    );
  }
}

export default MenuSubscribe;

const style_sheet = {
  color: "black",
  backgroundColor: '#FFC242',
  fontFamily: "Arial",
  fontSize: "14px",
  width:"100%",
  height:'50px',
  padding:0,
}
