import React, {Component} from 'react';
import history from '../Screen/history'
import {pass} from '../Utils/Utils'
import MenuSubscribe from './MenuSubscribe';

const WHITE_BLUE= '#B3D1F0'
class Menu extends Component {
  
  constructor(props){
    super(props)
    this.fromPath = this.props.fromPath
    this.state = { }
  }
  
  renderGuest() {
    let onHover = (event) => {
      event.currentTarget.style.backgroundColor = WHITE_BLUE;
    }
    let onLeave = (event) => {
        event.currentTarget.style.backgroundColor = '';
    }
    return (
        <header style={{backgroundColor: '#FFC242', borderBottom:'2px solid #B38118'}}>
          <title> Trading System </title>
          {/*<p>{(this.props.state!=undefined)?this.props.state.id:''}</p>*/}
          <table style={style_sheet}>
          <tr style={{width:'100%'}}>
            <th style={{margin:0, borderRight:'1px solid white'}}
                onClick={() => pass(history,"/",this.fromPath,this.props.state)}
                onMouseOver={onHover} onMouseLeave={onLeave}> Menu </th>
            <th style={{margin:0, borderRight:'1px solid white'}}
                onClick={() => pass(history,"/viewStoresAndProducts",this.fromPath,this.props.state)}
                onMouseOver={onHover} onMouseLeave={onLeave}> Watch Store and Products </th>
            <th style={{margin:0, borderRight:'1px solid white'}}
                onClick={()=> pass(history,"/searchAndFilter",this.fromPath,this.props.state)}
                onMouseOver={onHover} onMouseLeave={onLeave}> Search and Filter Products </th>
            <th style={{margin:0, borderRight:'1px solid white'}}
                onClick={()=>pass(history,"/register",this.fromPath,this.props.state)} 
                onMouseOver={onHover} onMouseLeave={onLeave}> Register </th>
            <th style={{margin:0, borderRight:'1px solid white'}}
                onClick={()=>pass(history,"/login",this.fromPath,this.props.state)}
                onMouseOver={onHover} onMouseLeave={onLeave}> Login </th>
            <th style={{margin:0, borderRight:'1px solid white'}}
                onClick={()=>pass(history,"/viewMyCart",this.fromPath,this.props.state)}
                onMouseOver={onHover} onMouseLeave={onLeave}> Watch My Cart </th>
          </tr>
        </table>
        </header>
    );
  }

  render() {
    return (this.props.state===undefined)? this.renderGuest():
           (this.props.state.logged===undefined)? this.renderGuest():
            <MenuSubscribe state ={this.props.state}/>
           
  }
}

export default Menu;

const style_sheet = {
  color: "black",
  backgroundColor: '#FFC242',
  fontFamily: "Arial",
  width:"100%",
  height:'50px',
  padding:0,
  margin:0,
}
